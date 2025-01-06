package com.example.Library.Service;
 import com.example.Library.Entities.Book;
import com.example.Library.Entities.Library;
import com.example.Library.Entities.User;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Repos.BooksRepo;
import com.example.Library.Repos.LibraryRepo;
import com.example.Library.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class LibraryService {

    @Autowired
    private LibraryRepo libraryRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BooksRepo booksRepo;

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
            newLibrary.setName(library.getName());
            return new ResponseEntity<>(libraryRepo.save(newLibrary), HttpStatus.ACCEPTED);
        }
        throw new ResourceNotFoundException("Author with id of " + id + " not found");
    }

    public String checkoutBook(Long userId, Long bookId) throws Exception {
        // Fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

        // Fetch book
        Book book = booksRepo.findById(bookId).orElseThrow(() -> new Exception("Book not found"));

        // Check if book is available
        if (!"available".equals(book.getStatus())) {
            throw new Exception("Book is already checked out");
        }

        // Check user borrowing limit
        if (user.getBorrowedBooks().size() >= 5) {
            throw new Exception("Borrowing limit reached");
        }

        // Update book status
        book.setStatus("checked_out");
        book.setBorrowedBy(userId);
        book.setDueDate(LocalDate.now().plusDays(14)); // 2 weeks from now
        booksRepo.save(book);

        // Update user
        user.getBorrowedBooks().add(bookId);
        userRepository.save(user);

        return "Book checked out successfully";
    }



    public String returnBook(Long userId, Long bookId) throws Exception {
        // Fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

        // Fetch a book
        Book book = booksRepo.findById(bookId).orElseThrow(() -> new Exception("Book not found"));

        // Check if this user currently borrows the book
        if (!book.getBorrowedBy().equals(userId)) {
            throw new Exception("Book is not borrowed by this user");
        }

        // Remove a book from user's borrowed list
        user.getBorrowedBooks().remove(bookId);

        // Update book's status
        book.setStatus("available");
        book.setBorrowedBy(null);
        book.setDueDate(null);

        // Save changes using CrudRepository
        userRepository.save(user);
        booksRepo.save(book);

        return "Book returned successfully";
    }
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


