package com.example.Library.Controllers;

import com.example.Library.Entities.Library;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Service.LibraryService;
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

public class LibraryController {

    private final Logger logger = LoggerFactory.getLogger(Library.class);

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/library")
    public ResponseEntity<Library> createLibrary(@RequestBody Library library) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(library.getId())
                .toUri();
        logger.info("Created a new Library with an id of " + library.getId());
        libraryService.createLibrary(library);
        return new ResponseEntity<>(library, HttpStatus.CREATED);
    }

    @GetMapping("/library/{libraryid}")
    public ResponseEntity<Library> getLibraryById(@PathVariable Long libraryid) throws ResourceNotFoundException {
        logger.info("Getting a Library with an id of " + libraryid);
        return new ResponseEntity<>(libraryService.getLibraryById(libraryid), HttpStatus.OK);
    }

    @GetMapping("/library")
    public ResponseEntity<List<Library>> getAllLibraries() {
        logger.info("Getting all Libraries ");

        return new ResponseEntity<>(libraryService.getAllLibraries(), HttpStatus.OK);
    }

    @DeleteMapping("/library/{libraryid}")
    public ResponseEntity<HttpStatus> deleteLibrary(@PathVariable Long libraryid) {
        logger.info("Delete a Library with an id of " + libraryid);
        libraryService.deleteLibrary(libraryid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/library/{id}")
    public ResponseEntity<?> updateLibrary(@PathVariable Long id, @RequestBody Library library) throws ResourceNotFoundException {
        logger.info("Delete a Library with an id of " + library.getId());
        return libraryService.changeLibrary(library, id);
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkoutBook(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            String message = libraryService.checkoutBook(userId, bookId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            String message = libraryService.returnBook(userId, bookId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
//    @PutMapping("/library/{libraryId}/book/{bookId}")
//    public ResponseEntity<Library> addBookToLibrary(@PathVariable Long libraryId, @PathVariable Long bookId) {
//     return new ResponseEntity<>(libraryService.addBookToLibrary(libraryId,bookId),HttpStatus.OK);
//    }
