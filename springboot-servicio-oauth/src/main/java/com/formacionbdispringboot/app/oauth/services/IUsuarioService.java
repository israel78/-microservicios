package com.formacionbdispringboot.app.oauth.services;


import com.formacionbdispringboot.app.commons.models.entity.usuarios.Usuario;

public interface IUsuarioService {
	
	public Usuario findByUsername(String username);
	
	public Usuario update(Usuario usuario,Long id ) ;


}
