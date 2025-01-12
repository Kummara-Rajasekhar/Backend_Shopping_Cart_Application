package com.rajasekhar.dreamshops.Controller;


import com.rajasekhar.dreamshops.dto.ProductDto;
import com.rajasekhar.dreamshops.exceptions.ResourceNotFoundException;
import com.rajasekhar.dreamshops.model.Product;
import com.rajasekhar.dreamshops.request.AddProductRequest;
import com.rajasekhar.dreamshops.request.ProductUpdateRequest;
import com.rajasekhar.dreamshops.response.ApiResponse;
import com.rajasekhar.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllPeoducts(){
        List<Product> products=productService.getAllProducts();
        List<ProductDto> convertedProducts=productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success!",convertedProducts));
    }

    @GetMapping("/product/{productid}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long prductId){
            try{
                Product product=productService.getProductById(prductId);
                ProductDto productDto=productService.convertToDto(product);
                return ResponseEntity.ok(new ApiResponse("success!",productDto));
            }catch (ResourceNotFoundException e){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

            }
    }

   @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
            try{

                Product theProduct=productService.addProduct(product);
                return ResponseEntity.ok(new ApiResponse("Add Product success!",theProduct));
            }catch (Exception e){
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
            }
    }


    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product,@PathVariable Long productId){
        try{
            Product updatedProduct=productService.updateProduct(product,productId);
            return ResponseEntity.ok(new ApiResponse("Update Product success!",updatedProduct));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public  ResponseEntity<ApiResponse> deleteProduct(@RequestBody ProductUpdateRequest product,@PathVariable Long productId){
        try{
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete Product success!",null));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductbyBrandAndName(@RequestParam String brandname, @RequestParam String productname){
        try{
            List<Product> products= productService.getProductsByBrandAndName(brandname,productname);
            List<ProductDto> convertedProducts=productService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found!",null));
            }
            return ResponseEntity.ok(new ApiResponse("success!",convertedProducts));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductbyCategoryAndBrand(@PathVariable String category, @PathVariable String brandname){
        try{
            List<Product> products= productService.getProductsByCategoryAndBrand(category,brandname);
            List<ProductDto> convertedProducts=productService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found!",null));
            }
            return ResponseEntity.ok(new ApiResponse("success!",convertedProducts));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProsuctsByName(@PathVariable String name){
        try{
            List<Product> products= productService.getProductByName(name);
            List<ProductDto> convertedProducts=productService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found!",null));
            }
            return ResponseEntity.ok(new ApiResponse("success!",convertedProducts));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@PathVariable String brandname){
        try{
            List<Product> products= productService.getProductsByBrand(brandname);
            List<ProductDto> convertedProducts=productService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found!",null));
            }
            return ResponseEntity.ok(new ApiResponse("Product found!",convertedProducts));
        }
        catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/products/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductsByCategory(@PathVariable String category){
        try{
            List<Product> products= productService.getProductsByCategory(category);
            List<ProductDto> convertedProducts=productService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found!",null));
            }
            return ResponseEntity.ok(new ApiResponse("success!",convertedProducts));
        }
        catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brandname, @RequestParam String productname){
        try{
            var productCount= productService.countProductsByBrandAndName(brandname,productname);
            return ResponseEntity.ok(new ApiResponse("Product found!",productCount));
        }
        catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }




}
