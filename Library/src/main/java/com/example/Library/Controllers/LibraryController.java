package com.example.Library.Controllers;

import com.example.Library.Entities.Book;
import com.example.Library.Entities.Library;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/library")
    public ResponseEntity<Library> createLibrary(@RequestBody Library library) {
        Library createdLibrary = libraryService.createLibrary(library);
        return new ResponseEntity<>(createdLibrary, HttpStatus.CREATED);
    }

    @GetMapping("/{libraryid}")
    public ResponseEntity<Library> getLibraryById(@PathVariable Long libraryid) throws ResourceNotFoundException {
        return new ResponseEntity<>(libraryService.getLibraryById(libraryid),HttpStatus.OK);
    }

    @GetMapping("/library")
    public ResponseEntity<List<Library>> getAllLibraries() {
         return new ResponseEntity<>(libraryService.getAllLibraries(), HttpStatus.OK);
    }

    @DeleteMapping("/{libraryid}")
    public ResponseEntity<HttpStatus> deleteLibrary(@PathVariable Long libraryid) {
        libraryService.deleteLibrary(libraryid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PutMapping("/library/{libraryId}/book/{bookId}")
//    public ResponseEntity<Library> addBookToLibrary(@PathVariable Long libraryId, @PathVariable Long bookId) {
//     return new ResponseEntity<>(libraryService.addBookToLibrary(libraryId,bookId),HttpStatus.OK);
//    }
}