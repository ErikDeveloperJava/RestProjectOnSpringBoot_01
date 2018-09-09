package restfull.demo.endpoint;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restfull.demo.model.Category;
import restfull.demo.repository.CategoryRepository;

@RestController
@RequestMapping("/category")
public class CategoryEndpoint {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    @ApiOperation(value = "add",response = Category.class)
    public ResponseEntity add(@RequestBody Category category){
        return ResponseEntity
                .ok(categoryRepository.save(category));
    }

    @GetMapping
    @ApiOperation(value = "categories",response = Category.class,responseContainer = "list")
    public ResponseEntity categories(){
        return ResponseEntity
                .ok(categoryRepository.findAll());
    }
}