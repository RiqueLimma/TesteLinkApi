package com.linkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.linkapi.model.Postagem;
import com.linkapi.repository.PostagemRepository;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository postagemRepository;
	
	private Postagem buscarPostagemPeloId(Long id) {
		
		Postagem postagemSalva = postagemRepository.findById(id).orElse(null);
		
		
		if (postagemSalva == null) {
			
			throw new EmptyResultDataAccessException(1);
		}
		
		
		return postagemSalva;
	}

	
	

	
}
