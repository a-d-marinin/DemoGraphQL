package ru.innoseti.demo.dto;

import java.util.Set;

public record BookInput(String title, Set<String> authors) {
}
