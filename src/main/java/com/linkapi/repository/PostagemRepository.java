package com.linkapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkapi.model.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	
	public Postagem findById(long id);
	public List<Postagem> findByGanhosContainingIgnoreCase(String ganhos);
	public Postagem deleteById(long id);
}
