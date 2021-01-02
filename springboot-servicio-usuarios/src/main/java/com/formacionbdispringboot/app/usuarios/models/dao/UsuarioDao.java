package com.formacionbdispringboot.app.usuarios.models.dao;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.formacionbdispringboot.app.commons.models.entity.usuarios.Usuario;

//Si se extiende de pagingand.. ofrece los metodos crud mas los metodos de ordenacion y paginacion
@RepositoryRestResource(path="usuarios") //esta dependencia es para hacer que el propio dao sea accesible directamente desde el exterior
public interface UsuarioDao extends PagingAndSortingRepository<Usuario,Long>{
	//A traves del nombre del metodo data sabe como hacer la consulta con palabras clave.. el where seria en este caso el username
	//Si quisieramos agregar mas parametros seria añadir al final un and mas el nombre del parametro por el que filtrar por ejemplo findByUsernameAndEmailOrId...
	//ver https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories
	@RestResource(path="buscar-username") //si se quere cambiar nombre de la query para ser llamada desde fuera, añadimos parametro @Param
	public Usuario findByUsername(@Param("username") String username);
	
	//Se puede hacer la consulta con el parametro query tambien. Esta consulta es por objetos java pero se puede hacer por los mombres de 
	//tabla/campos nativos de sql añadiendo  @Query(value="select u from Usuario u where u.username = ?1", nativeQuery = true)
	//Nota: los parametros van con ? seguido del num de parametro en el caso de la query de abajo ?1
	@Query(value="select u from Usuario u where u.username = ?1")
	public Usuario obtenerPorUsername(String username); 

}
