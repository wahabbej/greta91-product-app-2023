package fr.greta91.productapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.greta91.productapp.entity.Product;

@RestController
@RequestMapping("/products")
public class ProductController {
	@RequestMapping("")
	public List<Product> getAll(){
		List<Product> products = new ArrayList<Product>();
		products.add(new Product(1001, "prod1", 10.15));
		products.add(new Product(1002, "prod2", 20.15));
		products.add(new Product(1003, "prod3", 10.15));
		return products;
	}
}
