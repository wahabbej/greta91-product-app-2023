package fr.greta91.productapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.greta91.productapp.dto.LoginDTO;
import fr.greta91.productapp.dto.UserDTO;
import fr.greta91.productapp.service.AuthService;
import fr.greta91.productapp.service.JwtService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@Autowired
	JwtService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO){
		try {
			UserDTO userDTO = authService.login(loginDTO.getUsername(), loginDTO.getPassword());
			System.err.println(userDTO);
			String res = jwtService.getJWT(userDTO.getUsername(), userDTO);
			System.err.println(res);
			userDTO.setAccessToken(res);
			return ResponseEntity.ok(userDTO);
		}
		catch(Exception ex) {
			System.err.println(ex.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
}
