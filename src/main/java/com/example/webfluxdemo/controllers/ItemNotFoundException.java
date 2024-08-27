package com.example.webfluxdemo.controllers;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super("Item with ID " + id + " not found");
    }
}
