package com.formacionbdispringboot.app.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.formacionbdispringboot.app.commons.models.entity.usuarios.Usuario;
import com.formacionbdispringboot.app.oauth.services.IUsuarioService;

@Component
public class InfoAditionalToken implements TokenEnhancer {
	
	@Autowired
	private IUsuarioService uservice;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String,Object> info = new HashMap<String,Object>();
		Usuario u = uservice.findByUsername(authentication.getName());
		info.put("nombre", u.getNombre());
		info.put("correo", u.getEmail());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
	

}
