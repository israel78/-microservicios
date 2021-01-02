package com.formacionbdispringboot.app.usuarios;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.formacionbdispringboot.app.commons.models.entity.usuarios.Role;
import com.formacionbdispringboot.app.commons.models.entity.usuarios.Usuario;

//import com.formacionbdispringboot.app.usuarios.models.entity.Role;
//import com.formacionbdispringboot.app.usuarios.models.entity.Usuario;



@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer{

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Usuario.class, Role.class);//para que se vea el id en las consultas para clases usuario y role
	}
}
