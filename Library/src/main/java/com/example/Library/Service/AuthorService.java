package com.example.Library.Service;

import com.example.Library.Entities.Author;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Repos.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//
@Service
public class AuthorService {
    @Autowired
        AuthorRepo authorRepo;

        public List<Author> getAllAuthor(){
            List<Author> authorsList = new ArrayList<>();
            for(Author author : authorRepo.findAll()){
                authorsList.add(author);
            }
            return authorsList;
        }

        //create an author
        public void addAuthor(Author author){
            authorRepo.save(author);
        }
        //DELETE author
        public void deleteAuthor(Long id){
            authorRepo.deleteById(id);
        }

        public Author getAnAuthorById(Long authorId) {

            Author author = authorRepo.findById(authorId).orElse(null);

            if (author == null) {
                throw new ResourceNotFoundException("Author with id of " + authorId + " not found");
            }
            return author;
        }

        //Update an Author
        public ResponseEntity<?> changeAuthor(Author author, Long id) {

            if (authorRepo.findById(id).isPresent()) {
                Author newAuthor = authorRepo.findById(id).get();
                newAuthor.setName(author.getName());
                return new ResponseEntity<>(authorRepo.save(newAuthor), HttpStatus.ACCEPTED);
            }
            throw new ResourceNotFoundException("Author with id of " + id + " not found");
        }

        public Iterable<Author>getAllAuthorsByBookTitle(String query){return authorRepo.findByAuthor(query);}

    }
