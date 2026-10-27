package com.dev.turkim.librarymcp.assistant.config;

import io.modelcontextprotocol.client.McpAsyncClient;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.AsyncMcpToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Builds the ChatClient: Claude plus every tool discovered on the configured MCP servers.
 * Tools are listed once, at startup: restart this service after adding a tool to library-service.
 */
@Configuration
public class ChatClientConfig {

    private static final Logger log = LoggerFactory.getLogger(ChatClientConfig.class);

    private static final String SYSTEM_PROMPT = """
            You are a helpful library assistant.
            Use the library tools for any question about books, authors, genres or availability;
            never invent books or copy counts. Mention the book id when you list books.
            """;

    @Bean
    ChatClient chatClient(ChatClient.Builder builder, List<McpAsyncClient> mcpClients) {
        AsyncMcpToolCallbackProvider mcpTools = AsyncMcpToolCallbackProvider.builder()
                .mcpClients(mcpClients)
                .build();
        // tools/list on every server now, so a missing server fails fast at startup
        log.info("Discovered {} MCP tools from {} server(s)", mcpTools.getToolCallbacks().length, mcpClients.size());

        return builder
                .defaultSystem(SYSTEM_PROMPT)
                .defaultTools(mcpTools)
                .build();
    }
}
