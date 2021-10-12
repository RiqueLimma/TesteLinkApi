package com.linkapi.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.linkapi.model.Usuario;
import com.linkapi.model.UsuarioLogin;
import com.linkapi.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	
	@Autowired
	private UsuarioRepository repository;
	
	
	public Usuario cadastrarUsuario(Usuario usuario) {
		
		// Verifica se o usuário (email) existe
		if(repository.findByUsuario(usuario.getUsuario()).isPresent())
			throw new ResponseStatusException(
			          	HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);

		return repository.save(usuario); //alterado tirando o optinal
	}

	
	public Optional<UsuarioLogin> Logar(Optional<UsuarioLogin> user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());

		if (usuario.isPresent()) {
			if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encoderAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encoderAuth);
				
				user.get().setId(usuario.get().getId());
				user.get().setNome(usuario.get().getNome());
				user.get().setToken(authHeader);
				
				return user;
			}
		}
		return null;
}
	public Optional<Usuario> atualizarUsuario(Usuario usuario){

        if(repository.findById(usuario.getId()).isPresent()) {

           
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String senhaEncoder = encoder.encode(usuario.getSenha());
            usuario.setSenha(senhaEncoder);

            return Optional.of(repository.save(usuario));

        }else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);

        }
       
    }
}
		