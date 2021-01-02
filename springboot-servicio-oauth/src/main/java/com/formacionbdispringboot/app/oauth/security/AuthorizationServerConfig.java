package com.formacionbdispringboot.app.oauth.security;

import java.util.Arrays; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	
	@Autowired
	private Environment env;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfoAditionalToken infoAditionalToken;
	
	@Override//permiso que van a obtenr el servicio de autorizacion para dar seguridad a los empoints
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()"); //para generar el token el cliente tiene que estar autenticado;
	}
	@Override //hay que registrar a los clientes que se conectan a la aplicacion
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//Se generan el token con la aplicacion y con las credenciales del usuario
		clients.inMemory().withClient(env.getProperty("config.security.oauth.client.id"))
		.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))
		.scopes("read","write") //alcance del token 
		.authorizedGrantTypes("password","refresh_token") //permite obtener un nuevo token cuando caduque el actual
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600);
		//si a continuacion ponemos and(). se pueden configurar mas clientes distintos por ejemplo androidapp...
	}
	@Override //el endpoint se encarga de generar el token tambien se autentica aplicaciones clientes
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();//Se encadena informacion adicional de la clase info AditionalToken
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAditionalToken,accessTokenConverter()));
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain);
	}
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(env.getProperty("config.security.oauth.jwt.key"));
		return tokenConverter;
		
	}

}
