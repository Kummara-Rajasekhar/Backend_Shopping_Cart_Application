package com.rajasekhar.dreamshops.service.product;

import com.rajasekhar.dreamshops.exceptions.ProductNotFoundException;
import com.rajasekhar.dreamshops.model.Category;
import com.rajasekhar.dreamshops.repository.CategoryRepository;
import com.rajasekhar.dreamshops.repository.ProductRepository;
import com.rajasekhar.dreamshops.model.Product;
import com.rajasekhar.dreamshops.request.AddProductRequest;
import com.rajasekhar.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductsService implements IProductService{


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;



    @Override
    public Product addProduct(AddProductRequest request) {
        Category category =  Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }

    private Product createProduct(AddProductRequest request,Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category



        );
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
         productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                 ()->{throw new ProductNotFoundException("Product not found!");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long id) {
        return productRepository.findById(id)
                .map(existingProduct-> updateExistingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));

    }


    private  Product updateExistingProduct(Product exixtingProduct, ProductUpdateRequest request) {
            exixtingProduct.setName(request.getName());
            exixtingProduct.setBrand(request.getBrand());
            exixtingProduct.setPrice(request.getPrice());
            exixtingProduct.setInventory(request.getInventory());
            exixtingProduct.setDescription(request.getDescription());
            Category category = categoryRepository.findByName(request.getCategory().getName());
            exixtingProduct.setCategory(category);
            return exixtingProduct;



    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
