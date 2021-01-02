package com.formacionbdispringboot.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//Ahora la entidad es comun en un proyecto aparte 
//Esta dentro un paquete con nombre diferente al nombre base de este pro
//yecto y hay que indicar que tenga en cuenta esta paqueteria
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.formacionbdispringboot.app.commons.models.entity.productos"}) 
public class SpringbootServicioProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioProductosApplication.class, args);
	}

}
