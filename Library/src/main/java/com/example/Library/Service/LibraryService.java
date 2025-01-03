package com.example.Library.Service;
import com.example.Library.Entities.Author;
import com.example.Library.Entities.Library;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Repos.LibraryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepo libraryRepo;
//
//    @Autowired
//    private BookService bookService;

    public void createLibrary(Library library) {

        libraryRepo.save(library);
    }

    public Library getLibraryById(Long id) {
         for (Library x : getAllLibraries()) {
            if (x.getId() == id) {
                return x;
            }
        }
        throw new ResourceNotFoundException("Library id not found" + id);
    }
    public List<Library> getAllLibraries() {
        return (List<Library>) libraryRepo.findAll();
    }

    public void deleteLibrary(Long id) {
        libraryRepo.deleteById(id);
    }

    public ResponseEntity<?> changeLibrary(Library library, Long id) {

        if (libraryRepo.findById(id).isPresent()) {
            Library newLibrary = libraryRepo.findById(id).get();
            newLibrary.setName( library.getName());
            return new ResponseEntity<>(libraryRepo.save(newLibrary), HttpStatus.ACCEPTED);
        }
        throw new ResourceNotFoundException("Author with id of " + id + " not found");
    }

//    public Library addBookToLibrary(Long libraryid, Long bookid){
//        Library library = getLibraryById(libraryid);
//                 for(Book x : bookService.getAllBooks()){
//                     if (x.getId() == bookid){
//                                 library.addBook(x);
//                         return libraryRepo.save(library);
//                     }
////                 }
//throw new ResourceNotFoundException("Book with ID not found" + bookid);
//    }

}
