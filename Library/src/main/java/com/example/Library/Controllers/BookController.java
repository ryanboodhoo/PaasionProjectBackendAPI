package com.example.Library.Controllers;

import com.example.Library.Entities.Book;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
         List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/{libraryId}/{authorId}")
    public ResponseEntity<Book> createBook(@RequestBody Book book, @PathVariable Long authorId ,@PathVariable Long libraryId) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(book.getId())
                .toUri();
        logger.info("Created a new Book with an id of " +  book.getId());
        Book createdBook = bookService.addBook(book ,authorId,libraryId);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @GetMapping("books/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Book with an id of " +  id);
        return  bookService.getABookById(id);
    }

    @PutMapping("books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book book) throws ResourceNotFoundException {
        logger.info("Updating Book with an id of " +  book.getId());

        return bookService.changeBook(book, id);
    }

    @DeleteMapping("books/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        logger.info("Deleted a Book with an id of " + id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Book>> searchBooksByTitle(@RequestParam String title) {
        logger.info("Looking for a book with the title of" + title);
        Iterable<Book> books = bookService.searchBooksByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

//    @GetMapping("/findByTitle")
//    public ResponseEntity<List<Book>> findByTitle(@RequestParam String title) {
//        List<Book> books = bookService.findByTitle(title);
//        return new ResponseEntity<>(books, HttpStatus.OK);
//    }

    @PutMapping("/book/{bookId}/library/{libraryId}")
    public void bookAssignedToLibrary (@PathVariable Long bookId , @PathVariable Long libraryId){
    bookService.addBooktoLibrary(bookId,libraryId) ;
    }
}
