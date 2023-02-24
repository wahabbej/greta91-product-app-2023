package fr.greta91.productapp.service;

import java.util.Optional;

import fr.greta91.productapp.entity.User;

public interface UserService {

	Optional<User> findByUsername(String user);

	void save(User u);
}
