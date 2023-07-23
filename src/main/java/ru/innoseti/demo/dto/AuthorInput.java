package ru.innoseti.demo.dto;

import java.util.Set;

public record AuthorInput(String name, Set<String> books) {
}
