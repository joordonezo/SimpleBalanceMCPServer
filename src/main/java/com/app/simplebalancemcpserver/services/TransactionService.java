package com.app.simplebalancemcpserver.services;

import com.app.simplebalancemcpserver.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final WebClient webClient;

    @Tool(name = "find-all", description = "Find all Transactions")
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

    @Tool(name = "find-all-by-query", description = "Find all Transactions by queryString")
    public List<Transaction> findAllTransactionByQueryString(@ToolParam(description = "queryString") String query){
        return webClient.get()
                .uri("/list/"+query)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToFlux(Transaction.class)
                .collectList()
                .block();

    }

    @Tool(name = "find-transaction-by-id", description = "Find a single transaction by id")
    public Transaction findTransactionById(@ToolParam(description = "id") String id){
        return webClient.get()
                .uri("/transaction/"+id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToMono(Transaction.class)
                .block();

    }

    @Tool(name = "save-transaction", description = "Save a single transaction")
    public Transaction saveTransaction(@ToolParam(description = "item") String item, @ToolParam(description = "value") Double value){
        return webClient.post()
                .uri("/transaction")
                .bodyValue(new Transaction(null, item, value))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToMono(Transaction.class)
                .block();

    }

    @Tool(name = "edit-transaction", description = "Edit a single transaction")
    public Transaction editTransaction(@ToolParam(description = "id") String id, @ToolParam(description = "item") String item, @ToolParam(description = "value") Double value){
        return webClient.put()
                .uri("/transaction")
                .bodyValue(new Transaction(id, item, value))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToMono(Transaction.class)
                .block();

    }

    @Tool(name = "delete-transaction", description = "Delete a single transaction")
    public Transaction deleteTransaction(@ToolParam(description = "id") String id, @ToolParam(description = "item") String item, @ToolParam(description = "value") Double value){
        return webClient.delete()
                .uri("/transaction")
                //.bodyValue(transaction)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToMono(Transaction.class)
                .block();

    }

    @Tool(name = "delete-transaction-by-id", description = "delete a single transaction by id")
    public Transaction deleteTransactionById(@ToolParam(description = "id") String id){
        return webClient.delete()
                .uri("/transaction/"+id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error 4xx")))
                .bodyToMono(Transaction.class)
                .block();

    }
}
