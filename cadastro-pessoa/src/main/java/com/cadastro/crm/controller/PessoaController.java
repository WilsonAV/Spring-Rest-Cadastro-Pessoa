package com.cadastro.crm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.crm.model.Pessoa;
import com.cadastro.crm.service.CadastroPessoaService;

@RestController
@RequestMapping("/api")
public class PessoaController {
	
	@Autowired
	private CadastroPessoaService pessoaService;
	
	
	@GetMapping("/pessoas")
	public ResponseEntity <List<Pessoa>> listar(@RequestParam(required=false) String nome){
		try {
			List<Pessoa> pessoas = new ArrayList<Pessoa>();
			if(nome == null) 
				pessoas =pessoaService.listar();
			else
				pessoas = pessoaService.listarFiltrando(nome);
			if(pessoas.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(pessoas, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/pessoas/{pessoaId}")
	public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long pessoaId) {
		
		return ResponseEntity.ok(pessoaService.buscarPorID(pessoaId));
	}
		
	@PostMapping
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa) {
		Pessoa p = pessoaService.salvar(pessoa);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(p);
	}
	
	@PutMapping("pessoas/{pessoaId}")
	public ResponseEntity<Pessoa> atualizar(@Valid @PathVariable Long pessoaId,
			@RequestBody Pessoa pessoa){
		return ResponseEntity.ok(pessoaService.atualizaressoa(pessoaId, pessoa));
	}
	
	@DeleteMapping("pessoas/{pessoaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remover(@PathVariable Long pessoaId){
		
		pessoaService.deletarPessoa(pessoaId);
		return ResponseEntity.noContent().build();
	}
	

}
