package com.example.Library.Service;

import com.example.Library.Entities.Author;
import com.example.Library.Entities.Book;
import com.example.Library.Entities.Library;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Repos.AuthorRepo;
import com.example.Library.Repos.BooksRepo;
import com.example.Library.Repos.LibraryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    LibraryService libraryService;

    @Autowired
    BooksRepo booksRepo;

    @Autowired
    AuthorRepo authorRepo;

    @Autowired
    AuthorService authorService;

    public List<Book> getAllBooks(){
        List<Book> booksList = new ArrayList<>();
        for(Book book : booksRepo.findAll()){
            booksList.add(book);
        }
        return booksList;
    }

    //create a Book
    public Book addBook(Book book,Long authorId,Long libraryId){
        Library library = libraryService.getLibraryById(libraryId);
        Author author = authorService.getAnAuthorById(authorId);

        book.setAuthor(author);

        book.setLibrary(library);

       return  booksRepo.save(book);
    }
    //DELETE Book
    public void deleteBook(Long id){
        booksRepo.deleteById(id);
    }

    public ResponseEntity<?> getABookById(Long bookId)throws ResourceNotFoundException {
        Book books = booksRepo.findById(bookId).orElse(null);

        if (books == null) {
            throw new ResourceNotFoundException("Book with id of " + bookId + " not found");
        }
        return new ResponseEntity<> (books, HttpStatus.OK);
    }

    //Update a Book
    public ResponseEntity<?> changeBook(Book book, Long id){

        if (booksRepo.existsById(id)){
            Book newBook = booksRepo.findById(id).get();
            newBook.setTitle(book.getTitle());
             return new ResponseEntity<>(booksRepo.save(newBook), HttpStatus.ACCEPTED);
        }
        return null;
    }

    public Iterable<Book>getAllAuthorsByBookTitle(String query){return booksRepo.searchBooks(query);}


    public List<Book>findByTitle(String title){
        return booksRepo.findByTitle(title);
    }

    public Iterable<Book> searchBooksByTitle(String query) {
        return booksRepo.searchBooks(query);
    }

    public void addBooktoLibrary(Long bookId,Long libraryId ){
        for (Book book : getAllBooks()){
            if(book.getId() == bookId){
               book.setLibrary(libraryService.getLibraryById(libraryId));
            }

        }
    }
}