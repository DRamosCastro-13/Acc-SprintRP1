package com.example.webfluxdemo.controllers;

import com.example.webfluxdemo.dto.ItemDTO;
import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.repositories.ItemRepository;
import com.example.webfluxdemo.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/items")
@Tag(name = "Item API", description = "API for managing items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/{id}")
    @Operation(summary = "Get an item by ID", description = "Retrieves an item based on its unique identifier.")
    @ApiResponse(responseCode = "200", description = "Item found successfully")
    @ApiResponse(responseCode = "404", description = "Item not found")
    public Mono<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @GetMapping
    @Operation(summary = "Get all items", description = "Retrieves a list of all items.")
    @ApiResponse(responseCode = "200", description = "Items retrieved successfully")
    public Flux<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    @Operation(summary = "Create a new item", description = "Creates a new item based on the provided data.")
    @ApiResponse(responseCode = "201", description = "Item created successfully")
    public Mono<Item> createItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing item", description = "Updates an existing item with the provided data.")
    @ApiResponse(responseCode = "200", description = "Item updated successfully")
    public Mono<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        return itemService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an item", description = "Deletes an existing item based on its unique identifier.")
    @ApiResponse(responseCode = "204", description = "Item deleted successfully")
    @ApiResponse(responseCode = "404", description = "Item not found")
    public Mono<ResponseEntity<Object>> deleteItem(@PathVariable Long id) {
        return itemService.deleteItem(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .defaultIfEmpty(Mono.just(ResponseEntity.notFound().build()).block());
    }
}


