package fr.greta91.productapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.greta91.productapp.entity.Role;
import fr.greta91.productapp.entity.User;
import fr.greta91.productapp.repository.RoleRepository;
import fr.greta91.productapp.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepo;
	
	@GetMapping("/admin/user/add")
	public ModelAndView add(ModelAndView mv) {
		List<Role> roles = roleRepo.findAll();
		mv.addObject("user", new User());
		mv.addObject("roles", roles);
		mv.addObject("titrePage", "Ajouter un utilisateur");
		mv.setViewName("user/add");
		return mv;
	}
	
	@PostMapping("/admin/user/add")
	public ModelAndView traiterForm(@ModelAttribute("user") @Valid User user, 
			BindingResult errors,
			ModelAndView mv) {
		
		if (errors.hasErrors()) {
			List<Role> roles = roleRepo.findAll();
			mv.addObject("roles", roles);
			mv.addObject("user", user);
			mv.addObject("titrePage", "Ajouter un utilisateur");
			mv.setViewName("user/add");
		} else {
			try {
				userService.save(user);
				mv.setViewName("redirect:/user/list");//réponse 302 ()
			}
			catch(Exception ex) {
				List<Role> roles = roleRepo.findAll();
				mv.addObject("roles", roles);
				mv.addObject("user", user);
				mv.addObject("titrePage", "Ajouter un utilisateur");
				mv.addObject("error", "username n'est plus disponible");
				mv.setViewName("user/add");
			}
		}
		return mv;
	}
	
	@GetMapping("/user/list")
	public ModelAndView list(ModelAndView mv) {
		mv.addObject("titrePage", "Liste des utilisateurs");
		mv.setViewName("user/list");
		return mv;
	}
}
