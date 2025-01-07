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
 import org.springframework.web.bind.annotation.PathVariable;

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

        // Fetch user and book
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = booksRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Check if the book is available
        if (!"available".equals(book.getStatus())) {
            throw new RuntimeException("Book is already checked out");
        }

        // Check user's borrowing limit
        if (user.getBorrowedBooks().size() >= 5) {
            throw new RuntimeException("Borrowing limit reached");
        }

        // Update book status
        book.setStatus("checked_out");
        book.setBorrowedBy(user.getId()); // Use userId instead of name
        book.setDueDate(LocalDate.now().plusDays(14)); // 2 weeks from now
        booksRepo.save(book);

        // Add the book to user's borrowed books list
        user.getBorrowedBooks().add(String.valueOf(bookId));
        userRepository.save(user);

        return "Book checked out successfully!";
    }



    public ResponseEntity<String> returnBook(Long userId, Long bookId) {
        try {
            // Fetch user and book
            User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
            Book book = booksRepo.findById(bookId).orElseThrow(() -> new Exception("Book not found"));

            // Check if this user borrowed the book
            String borrowedBy = String.valueOf(book.getBorrowedBy());
            if (borrowedBy == null || !borrowedBy.equals(String.valueOf(userId))) {
                return ResponseEntity.badRequest().body("Book is not currently borrowed by you.");
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

            return ResponseEntity.ok("Book returned successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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


