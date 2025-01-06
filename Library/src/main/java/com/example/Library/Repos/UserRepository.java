package com.example.Library.Repos;

import com.example.Library.Entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
        @Query("SELECT u FROM User u WHERE u.id = :id")
        User findUserById(@Param("id") Long id);
    }
