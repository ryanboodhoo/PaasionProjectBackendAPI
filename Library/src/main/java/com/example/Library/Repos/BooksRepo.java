package com.example.Library.Repos;

import com.example.Library.Entities.Author;
import com.example.Library.Entities.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepo extends CrudRepository<Book, Long>{
    @Query(value = "SELECT * FROM Book WHERE title LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    Iterable<Book> searchBooks(String query);

    @Query(value = "SELECT * FROM Author WHERE name LIKE CONCAT('%', :query, '%')", nativeQuery = true)

    Iterable<Author> getAllAuthorsByBookTitle(String query);


    public List<Book> findByTitle(@Param("title")String title);

    @Query("SELECT b FROM Book b WHERE b.id = :id")
    Book findBookById(@Param("id") Long id);

}