package com.example.Library.Service;
import com.example.Library.Entities.Book;
import com.example.Library.Entities.Library;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Repos.LibraryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepo libraryRepo;
//
//    @Autowired
//    private BookService bookService;

    public Library createLibrary(Library library) {
        return libraryRepo.save(library);
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
