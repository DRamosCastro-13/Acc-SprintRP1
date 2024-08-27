package com.example.webfluxdemo.dto;

import com.example.webfluxdemo.models.Item;

public class ItemDTO {

    private Long id;

    private String name;

    public ItemDTO(Item item){
        this.id = item.getId();
        this.name = item.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
