package fr.greta91.productapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.greta91.productapp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
