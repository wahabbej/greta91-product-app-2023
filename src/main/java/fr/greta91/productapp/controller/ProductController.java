package fr.greta91.productapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.greta91.productapp.entity.Product;
import fr.greta91.productapp.repository.ProductRepository;
import fr.greta91.productapp.service.ProductService;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	ProductService service;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Product> getAll(){
		List<Product> products = service.getAll();
//		List<Product> products = new ArrayList<Product>();
//		products.add(new Product(1001, "prod1", 10.15));
//		products.add(new Product(1002, "prod2", 20.15));
//		products.add(new Product(1003, "prod3", 10.15));
		return products;
	}
	
	//supprimer un produit
//	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)// /products/1
	@DeleteMapping("/{id}")
	public Map<String, Boolean> remove(@PathVariable(name = "id") int idProduct) {
		Map<String, Boolean> res=new HashMap<String, Boolean>();
		try {
			service.remove(idProduct);
			res.put("ok", true);
		}
		catch(Exception ex) {
			res.put("ok", false);
		}
		return res;
	}
	
	/**
	 * persiste le produit dans la BDD
	 * @param product à persister
	 * @return produit créé
	 */
	@RequestMapping(value = "", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> add(@RequestBody Product product) {//JSON -> java
		product = service.save(product);
		
		return ResponseEntity.created(null).body(product);
	}
}
