package com.example.Library.Controllers;

import com.example.Library.Entities.Author;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
 public class AuthorController {

    @Autowired
    private AuthorService authorService;

     @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthor();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        authorService.addAuthor(author);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        return new ResponseEntity<>(authorService.getAnAuthorById(id),HttpStatus.OK);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author author) throws ResourceNotFoundException {
        return  authorService.changeAuthor(author, id);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/authors/search")
//    public ResponseEntity<List<Author>> searchAuthorsByBookTitle(@RequestParam String query) {
//        List<Author> authors = (List<Author>) authorService.getAllAuthorsByBookTitle(query);
//        return new ResponseEntity<>(authors, HttpStatus.OK);
//    }


}