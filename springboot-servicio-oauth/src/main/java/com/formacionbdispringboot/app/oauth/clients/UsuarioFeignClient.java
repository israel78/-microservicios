package com.formacionbdispringboot.app.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;   
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.formacionbdispringboot.app.commons.models.entity.usuarios.Usuario;

@FeignClient(name="servicio-usuarios")
public interface UsuarioFeignClient {

	@GetMapping("/usuarios/search/obtenerPorUsername")
	public Usuario obtenerPorUsername(@RequestParam() String username);
	
	@PutMapping("/usuarios/{id}")
	public Usuario update(@RequestBody Usuario usuario, @PathVariable Long id ) ;
	

}
