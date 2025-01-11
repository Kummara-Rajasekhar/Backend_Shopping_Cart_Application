package com.rajasekhar.dreamshops.service.product;

import com.rajasekhar.dreamshops.model.Product;
import com.rajasekhar.dreamshops.request.AddProductRequest;
import com.rajasekhar.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);


    Product getProductById(Long id);


    void deleteProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

}
