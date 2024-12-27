package com.example.Library.Repos;

import com.example.Library.Entities.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends CrudRepository<Author, Long>{

    @Query(value = "SELECT * FROM Author WHERE name LIKE CONCAT('%', :title, '%')", nativeQuery = true)



    Iterable<Author> findByAuthor(String title);

}