package com.app.simplebalancemcpserver;

import com.app.simplebalancemcpserver.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionTools {

    private final WebClient webClient;

    @McpTool(name = "find-all", description = "Find all Transactions")
    public List<Transaction> findAllTransactions(){
        return webClient.get()
                .uri("/list")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToFlux(Transaction.class)
                .collectList()
                .block();

    }

    @McpTool(name = "find-all-by-query", description = "Find all Transactions by queryString")
    public List<Transaction> findAllTransactionByQueryString(@McpToolParam(description = "queryString") String queryString){
        return webClient.get()
                .uri("/list/"+queryString)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToFlux(Transaction.class)
                .collectList()
                .block();

    }

    @McpTool(name = "find-transaction-by-id", description = "Find a single transaction by id")
    public Transaction findTransactionById(@McpToolParam(description = "id") String id){
        return webClient.get()
                .uri("/transaction/"+id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToMono(Transaction.class)
                .block();

    }

    @McpTool(name = "save-transaction", description = "Save a single transaction")
    public Transaction saveTransaction(@McpToolParam(description = "item") String item, @McpToolParam(description = "value") Double value){
        return webClient.post()
                .uri("/transaction")
                .bodyValue(new Transaction(null, item, value))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToMono(Transaction.class)
                .block();

    }

    @McpTool(name = "edit-transaction", description = "Edit a single transaction")
    public Transaction editTransaction(@McpToolParam(description = "id") String id, @McpToolParam(description = "item") String item, @McpToolParam(description = "value") Double value){
        return webClient.put()
                .uri("/transaction")
                .bodyValue(new Transaction(id, item, value))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToMono(Transaction.class)
                .block();

    }

    @McpTool(name = "delete-transaction", description = "Delete a single transaction")
    public Transaction deleteTransaction(@McpToolParam(description = "id") String id, @McpToolParam(description = "item") String item, @McpToolParam(description = "value") Double value){
        return webClient.delete()
                .uri("/transaction")
                //.bodyValue(transaction)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToMono(Transaction.class)
                .block();

    }

    @McpTool(name = "delete-transaction-by-id", description = "delete a single transaction by id")
    public Transaction deleteTransactionById(@McpToolParam(description = "id") String id){
        return webClient.delete()
                .uri("/transaction/"+id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToMono(Transaction.class)
                .block();

    }
}
