package restfull.demo.config.security;

import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import restfull.demo.model.User;

public class CurrentUserDetail extends org.springframework.security.core.userdetails.User{

    @Getter
    private User user;

    public CurrentUserDetail(User user) {
        super(user.getEmail(),user.getPassword(),AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }
}