package com.example.webfluxdemo;

import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.repositories.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebfluxdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxdemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ItemRepository itemRepository){
		return args -> {
			Item item = new Item("Pencil");
			itemRepository.save(item).subscribe(System.out::println);
			Item item1 = new Item("Keyboard");
			itemRepository.save(item1).subscribe(System.out::println);
			Item item2 = new Item("Paper");
			itemRepository.save(item2).subscribe(System.out::println);
		};
	}
}
