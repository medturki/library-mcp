package com.dev.turkim.librarymcp.library.config;

import com.dev.turkim.librarymcp.library.tools.BookTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the {@code @Tool} methods of {@link BookTools} with the MCP server.
 */
@Configuration
public class McpToolsConfig {

    @Bean
    ToolCallbackProvider bookToolCallbacks(BookTools bookTools) {
        return MethodToolCallbackProvider.builder().toolObjects(bookTools).build();
    }
}
