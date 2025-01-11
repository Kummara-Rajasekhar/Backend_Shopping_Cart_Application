package com.rajasekhar.dreamshops.repository;

import com.rajasekhar.dreamshops.model.Category;
import com.rajasekhar.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
