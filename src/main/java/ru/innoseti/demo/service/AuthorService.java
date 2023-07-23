package ru.innoseti.demo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innoseti.demo.dto.AuthorInput;
import ru.innoseti.demo.mapper.AuthorMapper;
import ru.innoseti.demo.model.Author;
import ru.innoseti.demo.model.Book;
import ru.innoseti.demo.repository.AuthorRepository;
import ru.innoseti.demo.repository.BookRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorMapper mapper;

    @Transactional
    public Author saveAuthor(AuthorInput authorInput) {
        Optional<Author> optionalAuthor = authorRepository.findByName(authorInput.name());
        Author author = optionalAuthor.orElseGet(() -> mapper.INSTANCE.toEntity(authorInput));

        Set<Book> booksToSave = authorInput.books().stream()
                .map(title -> {
                    Optional<Book> optionalBook = bookRepository.findByTitle(title);
                    Book book = optionalBook.orElseGet(() -> {
                        Book newBook = new Book();
                        newBook.setTitle(title);
                        newBook.setAuthors(new HashSet<>());
                        return newBook;
                    });
                    book.getAuthors().add(author);
                    return book;
                })
                .collect(Collectors.toSet());

        author.setBooks(booksToSave);
        return authorRepository.save(author);
    }

    public Optional<Author> getAuthor(String name) {
        return authorRepository.findByName(name);
    }
}