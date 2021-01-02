package com.formacionbdispringboot.app.item.controller;

import java.util.HashMap ;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.formacionbdispringboot.app.item.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.formacionbdispringboot.app.commons.models.entity.productos.Producto;
import com.formacionbdispringboot.app.item.models.Item; 

@RefreshScope //para refrescar configuraciones en tiempo real en calietne
@RestController
public class ItemController {
	
	private static Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ItemService itenService;
	
	@Value("${configuracion.texto}")
	private String texto;
	

	
	@Value("${server.port}")
	private String puerto;
	
	@GetMapping("/listar")
	public List<Item> listar(){
		return itenService.findAll();
	}
	@HystrixCommand(fallbackMethod = "metodoAlternativo") //Si hay un fallo llamo a otro metodo alternativo
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id,@PathVariable Integer cantidad ) {
		return itenService.findById(id, cantidad);
		
	}
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return itenService.save(producto);
	}
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto,@PathVariable Long id) {
		return itenService.update(producto,id);
	}
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		 itenService.delete(id);
	}
	public Item metodoAlternativo(Long id,Integer cantidad) {
		Item item = new Item();
		item.setCantidad(cantidad);
		Producto producto = new Producto();
		producto.setId(id);
		producto.setNombre("Soni");
		producto.setPrecio(500.0);
		item.setProducto(producto);
		return item;
	}
	//Metodo para obtener los datos de configuración del fichero creado con el config-server, en este caso, este proyecto es cliente del servidor y mandamos 
	//las propiedades del fichero del repositorio git
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerconfig(){
		log.info(texto);
		Map<String,String>json = new HashMap<>();
		json.put("texto", texto);
		json.put("puerto", puerto);
		
		if(env.getActiveProfiles().length>0&&env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
		}
		
		return new ResponseEntity<Map<String,String>>(json,HttpStatus.OK);
	}
}
