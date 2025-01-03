package com.example.Library.Controllers;

import com.example.Library.Entities.Author;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
 public class AuthorController {

    @Autowired
    private AuthorService authorService;

    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

     @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors() {
         logger.info("Getting authors " );
         List<Author> authors = authorService.getAllAuthor();
          return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId())
                .toUri();
        logger.info("Created a new author with an id of " + author.getId());
        authorService.addAuthor(author);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        logger.info("Finding an author with an id of " + id);
        return new ResponseEntity<>(authorService.getAnAuthorById(id),HttpStatus.OK);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author author) throws ResourceNotFoundException {
        logger.info("Updating a author with an id of " + author.getId());
        return  authorService.changeAuthor(author, id);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable Long id) {
         authorService.deleteAuthor(id);
         logger.info("Deleted a author with an id of " + id);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/authors/search")
//    public ResponseEntity<List<Author>> searchAuthorsByBookTitle(@RequestParam String query) {
//        List<Author> authors = (List<Author>) authorService.getAllAuthorsByBookTitle(query);
//        return new ResponseEntity<>(authors, HttpStatus.OK);
//    }


}