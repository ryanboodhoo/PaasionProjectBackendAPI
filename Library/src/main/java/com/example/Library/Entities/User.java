package com.example.Library.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    private List<Long> borrowedBooks; // List of book IDs

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Long> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
