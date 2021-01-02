package com.formacionbdispringboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.formacionbdispringboot.app.commons.models.entity.usuarios.Usuario;
import com.formacionbdispringboot.app.oauth.services.IUsuarioService;

import feign.FeignException;

@Component
public class AutenticationSuccessErrorHandler implements AuthenticationEventPublisher {
	
	private Logger log = LoggerFactory.getLogger(AutenticationSuccessErrorHandler.class);
	
	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		//Al pasar por aqui con el nombre de la aplicacion.. no tiene ningun permiso y no entra, para que no falle
		if(authentication.getAuthorities().size()>0){
			UserDetails user = (UserDetails) authentication.getPrincipal();
			log.info("Success Login: "+user.getUsername());
			Usuario u = usuarioService.findByUsername(authentication.getName());
			if(u.getIntentos() != null && u.getIntentos()>0) {
				u.setIntentos(0);
				usuarioService.update(u, u.getId());
			}
		
		}
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
	
		try {
			Usuario u = usuarioService.findByUsername(authentication.getName());
			if(u.getIntentos() == null) {
				u.setIntentos(0);
			}
			log.info("El numero de intentos se incrementa un intento");
			u.setIntentos(u.getIntentos()+1);
			if(u.getIntentos()>=3) {
				u.setEnabled(false);
				log.error(String.format("El usuario %s desabilitado por maximo intentos", authentication.getName()));
			}
			usuarioService.update(u, u.getId());
			
		}catch(FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
		
		
		log.error("Error Login: "+exception.getMessage());
	}

}
