package com.example.webfluxdemo.controllers;

import com.example.webfluxdemo.dto.ItemDTO;
import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.repositories.ItemRepository;
import com.example.webfluxdemo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/{id}")
    public Mono<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @GetMapping
    public Flux<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public Mono<Item> createItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @PutMapping("/{id}")
    public Mono<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        return itemService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteItem(@PathVariable Long id) {
        return itemService.deleteItem(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .defaultIfEmpty(Mono.just(ResponseEntity.notFound().build()).block());
    }
}


