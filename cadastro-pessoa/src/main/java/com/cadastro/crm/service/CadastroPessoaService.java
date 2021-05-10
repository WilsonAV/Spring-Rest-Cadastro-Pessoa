package com.cadastro.crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadastro.crm.exception.EntidadeException;
import com.cadastro.crm.model.Pessoa;
import com.cadastro.crm.repository.PessoaRepository;

@Service
public class CadastroPessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	}
	
	public List<Pessoa> listarFiltrando(String nome){
		return pessoaRepository.findByNomeContaining(nome);
	}
	
	public Pessoa buscarPorID(Long id) {
		return  verificarPessoa(id);
	}
	
	public Pessoa salvar(Pessoa pessoa) {
		pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
		return pessoaRepository.save(pessoa);
	}
	
	public Pessoa atualizaressoa (Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = verificarPessoa(id);
		
		pessoaSalva.getContatos().clear();
		
		pessoaSalva.getContatos().addAll(pessoa.getContatos());
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id", "contatos");
		
		return pessoaRepository.save(pessoaSalva);
	}
	
	public void deletarPessoa(Long id) {
		Pessoa pessoa = verificarPessoa(id);
		pessoaRepository.delete(pessoa);
	}
	
	private Pessoa verificarPessoa(Long id) {
		return pessoaRepository.findById(id).orElseThrow(() -> new EntidadeException("Pessoa não Localizada"));
	}
	

}
