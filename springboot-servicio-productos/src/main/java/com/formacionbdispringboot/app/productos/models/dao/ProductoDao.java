package com.formacionbdispringboot.app.productos.models.dao;
import org.springframework.data.repository.CrudRepository;

import com.formacionbdispringboot.app.commons.models.entity.productos.Producto;

public interface ProductoDao extends CrudRepository<Producto, Long>{
	
	

}
