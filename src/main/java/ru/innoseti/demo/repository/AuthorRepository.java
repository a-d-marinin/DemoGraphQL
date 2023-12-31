package ru.innoseti.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innoseti.demo.model.Author;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @EntityGraph(attributePaths = "books")
    Optional<Author> findByName(String name);

    @EntityGraph(attributePaths = "books")
    Set<Author> findByNameIn(Set<String> name);
}