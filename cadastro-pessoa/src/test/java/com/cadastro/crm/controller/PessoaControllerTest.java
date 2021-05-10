package com.cadastro.crm.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.cadastro.crm.model.Pessoa;
import com.cadastro.crm.service.CadastroPessoaService;

import io.restassured.http.ContentType;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@WebMvcTest
public class PessoaControllerTest {
	
	@Autowired
	private PessoaController pessoaController;
	
	@MockBean
	private CadastroPessoaService CadastroPessoaService;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.pessoaController);
	}
	
	@Test
	public void deveRetornarSucesso_QuandoBuscarPessoa() {
		
		when(this.CadastroPessoaService.buscarPorID(1L)).thenReturn(new Pessoa());
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/pessoas/{pessoaId}", 1L)
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarNaoEncontrado_QuandoBuscaFilme() {
		
		when(this.CadastroPessoaService.buscarPorID(100L)).thenReturn(null);
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/pessoas/{pessoaId}",100L)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

}
