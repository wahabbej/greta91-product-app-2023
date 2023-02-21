package fr.greta91.productapp.service;

import java.util.List;

import fr.greta91.productapp.entity.Product;

public interface ProductService {
	List<Product> getAll();

	void remove(int idProduct);

	Product save(Product product);
}
