package com.rajasekhar.dreamshops.repository;

import com.rajasekhar.dreamshops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long > {

    Optional<Object> existByEmail(String email);
}
