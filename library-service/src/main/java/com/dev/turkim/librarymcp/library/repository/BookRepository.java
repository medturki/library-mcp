package com.dev.turkim.librarymcp.library.repository;

import com.dev.turkim.librarymcp.library.model.Book;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * In-memory catalog. Replace with a database-backed repository in a real application.
 */
@Repository
public class BookRepository {

    private static final List<Book> BOOKS = List.of(
            new Book("BK-001", "Dune", "Frank Herbert", "Science Fiction", 1965, 3, 1),
            new Book("BK-002", "Foundation", "Isaac Asimov", "Science Fiction", 1951, 2, 0),
            new Book("BK-003", "Neuromancer", "William Gibson", "Science Fiction", 1984, 2, 2),
            new Book("BK-004", "The Left Hand of Darkness", "Ursula K. Le Guin", "Science Fiction", 1969, 1, 1),
            new Book("BK-005", "Nineteen Eighty-Four", "George Orwell", "Dystopian", 1949, 4, 2),
            new Book("BK-006", "Brave New World", "Aldous Huxley", "Dystopian", 1932, 2, 0),
            new Book("BK-007", "The Hobbit", "J.R.R. Tolkien", "Fantasy", 1937, 3, 3),
            new Book("BK-008", "Pride and Prejudice", "Jane Austen", "Classic", 1813, 2, 1),
            new Book("BK-009", "The Pragmatic Programmer", "Andrew Hunt, David Thomas", "Software", 1999, 2, 1),
            new Book("BK-010", "Clean Code", "Robert C. Martin", "Software", 2008, 3, 0));

    public List<Book> findAll() {
        return BOOKS;
    }

    public Optional<Book> findById(String id) {
        return BOOKS.stream()
                .filter(book -> book.id().equalsIgnoreCase(id))
                .findFirst();
    }

    /** Case-insensitive match on title, author or genre. */
    public List<Book> search(String query) {
        String q = query.toLowerCase(Locale.ROOT);
        return BOOKS.stream()
                .filter(book -> book.title().toLowerCase(Locale.ROOT).contains(q)
                        || book.author().toLowerCase(Locale.ROOT).contains(q)
                        || book.genre().toLowerCase(Locale.ROOT).contains(q))
                .toList();
    }

    /** Books with at least one free copy, optionally limited to one genre. */
    public List<Book> findAvailable(String genre) {
        return BOOKS.stream()
                .filter(Book::isAvailable)
                .filter(book -> genre == null || genre.isBlank() || book.genre().equalsIgnoreCase(genre))
                .toList();
    }
}
