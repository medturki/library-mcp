package com.dev.turkim.librarymcp.library.model;

public record Book(
        String id,
        String title,
        String author,
        String genre,
        int publishedYear,
        int totalCopies,
        int availableCopies) {

    public boolean isAvailable() {
        return availableCopies > 0;
    }
}
