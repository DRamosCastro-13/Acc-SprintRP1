package com.example.webfluxdemo.services.impl;

import com.example.webfluxdemo.controllers.ItemNotFoundException;
import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.repositories.ItemRepository;
import com.example.webfluxdemo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Mono<Item> getItemById(Long id) {
        return itemRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(id)));
    }

    @Override
    public Flux<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Mono<Item> saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Mono<Item> updateItem(Long id, Item item) {
        return itemRepository.findById(id)
                .flatMap(existingItem -> {
                    existingItem.setName(item.getName()); // Update other fields as needed
                    return itemRepository.save(existingItem);
                })
                .switchIfEmpty(Mono.error(new ItemNotFoundException(id)));
    }

    @Override
    public Mono<Void> deleteItem(Long id) {
        return itemRepository.findById(id)
                .flatMap(item -> itemRepository.delete(item)
                        .then(Mono.empty())); // Return Mono<Void> on success
    }
}
