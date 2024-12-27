package com.example.Library.Repos;

import com.example.Library.Entities.Library;
import org.springframework.data.repository.CrudRepository;

public interface LibraryRepo extends CrudRepository<Library,Long> {
}
