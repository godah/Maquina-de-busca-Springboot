package com.maquinadebusca.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maquinadebusca.app.model.service.UserService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@RequestMapping("/login") // URL: http://localhost:8080/host
public class LoginController {

	@Autowired
	UserService us;

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity login() {
		return new ResponseEntity(us.encontrarUsuarioLogado(), HttpStatus.OK);
	}

}
