package restfull.demo.endpoint;

import io.jsonwebtoken.impl.DefaultClaims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import restfull.demo.dto.LoginRequestDto;
import restfull.demo.dto.LoginResponseDto;
import restfull.demo.dto.StringDto;
import restfull.demo.dto.UserDto;
import restfull.demo.exception.Response401;
import restfull.demo.exception.Response404;
import restfull.demo.model.User;
import restfull.demo.model.UserRole;
import restfull.demo.repository.UserRepository;
import restfull.demo.service.MailingService;
import restfull.demo.util.JwtUtil;

import java.util.Optional;
import java.util.Random;

@RestController
public class LoginRegisterEndpoint {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MailingService mailingService;

    @PostMapping("/register")
    @ApiOperation(value = "register",response = StringDto.class)
    public ResponseEntity register(@RequestBody User user) {
        String token = new Random().nextInt(2000000000) + user.getEmail();
        user.setRole(UserRole.USER);
        user.setToken(token);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String subject = "please confirm your email address";
        String text = "http://localhost:8080/confirm-email/" + user.getEmail() + "/"  +token;
        mailingService.add(user.getEmail(),subject,text);
        return ResponseEntity
                .ok(StringDto
                        .builder()
                        .message("please confirm your email address")
                        .build());
    }

    @PostMapping("/login")
    @ApiOperation(value = "register",response = LoginResponseDto.class)
    @ApiResponses({
            @ApiResponse(code = 401,message = "if entered wrong (username or password) or email does not confirmed"),
    })
    public ResponseEntity login(@RequestBody LoginRequestDto loginDto) {
        Optional<User> optUser = userRepository.findByEmail(loginDto.getEmail());
        User user;
        if (!optUser.isPresent() || !passwordEncoder.matches(loginDto.getPassword(), (user = optUser.get()).getPassword())) {
            throw new Response401("you entered wrong username or password");
        } else if(!user.isConfirmEmail()){
            throw new Response401("you did not confirm your email");
        }else  {
            String token = jwtUtil.generateToken(loginDto.getEmail(), new DefaultClaims());
            return ResponseEntity
                    .ok(LoginResponseDto
                            .builder()
                            .token(token)
                            .userDto(UserDto.
                                    builder()
                                    .name(user.getName())
                                    .surname(user.getSurname())
                                    .build())
                            .build());
        }
    }

    @GetMapping("/confirm-email/{email}/{token}")
    @ApiOperation(value = "confirmEmail",response = StringDto.class)
    @ApiResponses({
            @ApiResponse(code = 404,message = "if url token invalid")
    })
    public ResponseEntity confirmEmail(@PathVariable("email")String email, @PathVariable("token")String token){
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent() || !user.get().getToken().equals(token)){
            throw new Response404("invalid user email token");
        }else {
            user.get().setConfirmEmail(true);
            userRepository.save(user.get());
            return ResponseEntity
                    .ok(new StringDto("Your email address has been verified"));
        }
    }
}