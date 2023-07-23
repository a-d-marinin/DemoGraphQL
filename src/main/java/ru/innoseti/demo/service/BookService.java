package ru.innoseti.demo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innoseti.demo.dto.BookInput;
import ru.innoseti.demo.mapper.BookMapper;
import ru.innoseti.demo.model.Author;
import ru.innoseti.demo.model.Book;
import ru.innoseti.demo.repository.AuthorRepository;
import ru.innoseti.demo.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper mapper;

    @Transactional
    public Book saveBook(BookInput bookInput) {
        Optional<Book> optionalBook = bookRepository.findByTitle(bookInput.title());

        Book book = optionalBook.orElseGet(() -> mapper.INSTANCE.toEntity(bookInput));

        Set<Author> authorsToSave = bookInput.authors().stream()
                .map(authorName -> authorRepository.findByName(authorName)
                        .orElseGet(() -> {
                            Author author = new Author();
                            author.setName(authorName);
                            return authorRepository.save(author);
                        }))
                .collect(Collectors.toSet());

        book.setAuthors(authorsToSave);
        return bookRepository.save(book);
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorsName(author);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}