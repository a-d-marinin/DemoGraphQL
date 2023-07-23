package ru.innoseti.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.innoseti.demo.dto.AuthorInput;
import ru.innoseti.demo.model.Author;
import ru.innoseti.demo.service.AuthorService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @QueryMapping
    public Optional<Author> getAuthor(@Argument String name) {
        return authorService.getAuthor(name);
    }

    @MutationMapping
    public Author saveAuthor(@Argument AuthorInput author) {
        return authorService.saveAuthor(author);
    }

}