package com.formacionbdispringboot.app.productos.controllers;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdispringboot.app.commons.models.entity.productos.Producto;
import com.formacionbdispringboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private Environment enviroment; 
	
	@Value("${server.port}")//otra forma de obtener propiedades del properties
	private Integer port;
	
	//Se añade un puerto en el que se esta ejecutando el microservicio configurado en el properties
	@GetMapping("/listar")
	public List<Producto>listar(){
		return productoService.findAll().stream().map(producto ->{
			//producto.setPort(Integer.parseInt(enviroment.getProperty("local.server.port")));
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) {
	Producto producto = productoService.findById(id);
		producto.setPort(Integer.parseInt(enviroment.getProperty("local.server.port")));
// Excepcion controlada para probar el control de errores con hystrix
//		boolean ok = false;
//		if(ok == false) {
//			throw new Exception("No se pudo cargar el producto");
//		}
//		try {
//			Thread.sleep(2000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
		return producto;
	}
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return productoService.save(producto);	
	}
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)//importante cada vez que grabamos en la base de datos
	public Producto editar(@RequestBody Producto producto,@PathVariable Long id) {
		Producto p = productoService.findById(id);
		p.setNombre(producto.getNombre());
		p.setPrecio(producto.getPrecio());
		return productoService.save(p);	
	}
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		productoService.deleteById(id);
	}
}
