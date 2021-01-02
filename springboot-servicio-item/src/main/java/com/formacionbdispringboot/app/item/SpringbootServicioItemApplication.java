package com.formacionbdispringboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
//Se excluye de la configuracion la clase entidad productos y sus anotaciones referentes a base de datos con exclude
//@RibbonClient(name="servicio-productos")
@EnableCircuitBreaker
@EnableEurekaClient
@EnableFeignClients //Cliente especifico para llamar a otro microservicio rest de otra forma con un cliente especifico añadido al pom
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class SpringbootServicioItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioItemApplication.class, args);
	}
	
	

}
