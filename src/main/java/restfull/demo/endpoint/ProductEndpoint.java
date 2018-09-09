package restfull.demo.endpoint;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restfull.demo.dto.ProductRequestDto;
import restfull.demo.exception.Response404;
import restfull.demo.model.Category;
import restfull.demo.model.Product;
import restfull.demo.repository.CategoryRepository;
import restfull.demo.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductEndpoint {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    @ApiOperation(value = "add",response = Product.class)
    public ResponseEntity add(@RequestBody ProductRequestDto productRequestDto){
        List<Category> categories = categoryRepository.findAllById(productRequestDto.getCategories());
        Product product = Product.builder()
                .title(productRequestDto.getTitle())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .categories(categories)
                .build();
        productRepository.save(product);
        return ResponseEntity
                .ok(product);
    }


    @PutMapping
    @ApiOperation(value = "update",response = Product.class)
    @ApiResponses({
            @ApiResponse(code = 404,message = "if product does not exists in database")
    })
    public ResponseEntity update(@RequestBody ProductRequestDto productRequestDto){
        if(productRequestDto.getId() <= 0){
            throw new Response404("product with id '" + productRequestDto.getId() + "' not found");
        }
        List<Category> categories = categoryRepository.findAllById(productRequestDto.getCategories());
        Product product = Product.builder()
                .id(productRequestDto.getId())
                .title(productRequestDto.getTitle())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .categories(categories)
                .build();
        productRepository.save(product);
        return ResponseEntity
                .ok(product);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "deleteById",response = Product.class)
    @ApiResponses({
            @ApiResponse(code = 404,message = "if product with id does not exists in database")
    })
    public ResponseEntity deleteById(@PathVariable("id")int id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent()){
            throw new Response404("product with id '" + id + "' not found");
        }
        Product product = optionalProduct.get();
        productRepository.delete(product);
        return ResponseEntity
                .ok(product);
    }

    @GetMapping
    @ApiOperation(value = "products",response = Product.class,responseContainer = "list")
    public ResponseEntity products(){
        return ResponseEntity
                .ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "getById",response = Product.class)
    @ApiResponses({
            @ApiResponse(code = 404,message = "if product with id does not exists in database")
    })
    public ResponseEntity getById(@PathVariable("id")int id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent()){
            throw new Response404("product with id '" + id + "' not found");
        }
        return ResponseEntity
                .ok(optionalProduct.get());
    }

    @GetMapping("/category/{id}")
    @ApiOperation(value = "getByCategory",response = Product.class,responseContainer = "list")
    @ApiResponses({
            @ApiResponse(code = 404,message = "if category with id not found")
    })
    public ResponseEntity getByCategory(@PathVariable("id")int id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(!optionalCategory.isPresent()){
            throw new Response404("category with id '" + id + "' not found");
        }else {
            return ResponseEntity
                    .ok(productRepository.findAllByCategories_Id(id));
        }
    }
}