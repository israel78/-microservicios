package com.formacionbdispringboot.app.item.service;

import java.util.List;

import com.formacionbdispringboot.app.commons.models.entity.productos.Producto;
import com.formacionbdispringboot.app.item.models.Item; 
public interface ItemService {
	
	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad );
	public Producto save(Producto producto);
	public Producto update(Producto producto, Long id);
	public void delete(Long id);
	

}
