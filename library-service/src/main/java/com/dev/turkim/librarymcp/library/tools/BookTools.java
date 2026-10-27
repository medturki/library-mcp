package com.dev.turkim.librarymcp.library.tools;

import com.dev.turkim.librarymcp.library.repository.BookRepository;
import dev.toonformat.jtoon.JToon;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

/**
 * Library operations published as MCP tools. Results are TOON-encoded to save LLM tokens.
 */
@Service
public class BookTools {

    private static final Logger log = LoggerFactory.getLogger(BookTools.class);

    private final BookRepository repository;

    public BookTools(BookRepository repository) {
        this.repository = repository;
    }

    @Tool(name = "search_books",
            description = "Search the library catalog by title, author or genre. "
                    + "Use when the user looks for books about a subject, by a writer, or with a title.")
    public String searchBooks(@ToolParam(description = "Words to match in title, author or genre") String query) {
        log.info("Tool call :: search_books query='{}'", query);
        return encode(repository.search(query));
    }

    @Tool(name = "get_book",
            description = "Get full details of one book by its id, for example BK-001.")
    public String getBook(@ToolParam(description = "Book id, e.g. BK-001") String bookId) {
        log.info("Tool call :: get_book id={}", bookId);
        return repository.findById(bookId)
                .map(JToon::encode)
                .orElse("No book found with id " + bookId);
    }

    @Tool(name = "list_available_books",
            description = "List books that have at least one copy available to borrow, optionally for one genre. "
                    + "Genres: Science Fiction, Dystopian, Fantasy, Classic, Software.")
    public String listAvailableBooks(
            @ToolParam(description = "Genre to filter on; leave empty for all genres", required = false) String genre) {
        log.info("Tool call :: list_available_books genre='{}'", genre);
        return encode(repository.findAvailable(genre));
    }

    private static String encode(List<?> results) {
        return results.isEmpty() ? "No matching books" : JToon.encode(results);
    }
}
