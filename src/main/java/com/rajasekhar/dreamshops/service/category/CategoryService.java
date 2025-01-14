package com.rajasekhar.dreamshops.service.category;

import com.rajasekhar.dreamshops.exceptions.AlreadyExistException;
import com.rajasekhar.dreamshops.exceptions.ResourceNotFoundException;
import com.rajasekhar.dreamshops.model.Cart;
import com.rajasekhar.dreamshops.model.Category;
import com.rajasekhar.dreamshops.repository.CartItemRepository;
import com.rajasekhar.dreamshops.repository.CartRepository;
import com.rajasekhar.dreamshops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CartRepository CartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);



    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c->!categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save).orElseThrow(()->new AlreadyExistException("Category not found!"));
    }

    @Override
    public Category updateCategory(Category category ,Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory->{
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()->new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, ()->{
            throw new ResourceNotFoundException("Category not found!");
        });
    }

    public Long generateCartId(){
        Cart newCart=new Cart();
        Long cartId=cartIdGenerator.incrementAndGet();
        newCart.setId(cartId);
        return cartId;
    }
}
