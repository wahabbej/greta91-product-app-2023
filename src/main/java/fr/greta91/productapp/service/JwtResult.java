package fr.greta91.productapp.service;

import fr.greta91.productapp.dto.UserDTO;

public class JwtResult {
	private String login;
	private UserDTO userDTO;
	private boolean ok;
	
	private JwtResult(String login, UserDTO userDTO, boolean ok) {
		this.login = login;
		this.userDTO = userDTO;
		this.ok = ok;
	}

	public String getLogin() {
		return login;
	}
	
	public UserDTO getUserDTO() {
		return userDTO;
	}

	public boolean isOk() {
		return ok;
	}
	
	public boolean isClient() {
		return userDTO.getRoles().contains("ROLE_CLIENT");
	}
	
	public boolean isAdmin() {
		return userDTO.getRoles().contains("ROLE_ADMIN");
	}
	
	public static JwtResult buildFail() {
		return new JwtResult(null, null, false);
	}
	
	public static JwtResult buildInfo(String login, UserDTO userDTO) {
		return new JwtResult(login, userDTO, true);
	}
}
