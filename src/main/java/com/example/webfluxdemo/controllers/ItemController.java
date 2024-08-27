package com.example.webfluxdemo.controllers;

import com.example.webfluxdemo.dto.ItemDTO;
import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/{id}")
    public Mono<Item> getItemById(@PathVariable Long id) {
        return itemRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(id)));
    }

    @GetMapping
    public Flux<Item> getAllItems() {
        Flux<Item> allItems =  itemRepository.findAll();
        allItems.subscribe(System.out::println);
        return allItems;
    }

    @PostMapping
    public Mono<Item> createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public Mono<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        return itemRepository.findById(id)
                .flatMap(existingItem -> {
                    existingItem.setName(item.getName());
                    return itemRepository.save(existingItem);
                })
                .switchIfEmpty(Mono.error(new ItemNotFoundException(id)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteItem(@PathVariable Long id) {
        return itemRepository.findById(id)
                .flatMap(item -> itemRepository.delete(item)
                        .then(Mono.just(ResponseEntity.noContent().build())))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}

