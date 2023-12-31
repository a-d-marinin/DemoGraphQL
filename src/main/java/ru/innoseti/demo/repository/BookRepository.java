package ru.innoseti.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innoseti.demo.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorsName(String authorName);

    @EntityGraph(attributePaths = "authors")
    Optional<Book> findByTitle(String title);

    @EntityGraph(attributePaths = "authors")
    Set<Book> findByTitleIn(Set<String> bookTitles);
}