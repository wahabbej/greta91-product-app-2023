package fr.greta91.productapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.greta91.productapp.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
