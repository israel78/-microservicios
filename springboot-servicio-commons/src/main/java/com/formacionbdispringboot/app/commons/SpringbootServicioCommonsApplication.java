package com.formacionbdispringboot.app.commons;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//Se excluye de la configuracion la clase entidad productos y sus anotaciones referentes a base de datos con exclude
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootServicioCommonsApplication {

//Se quita el metodo main por que es un proyecto de librerias no ejecutable
//	public static void main(String[] args) {
//		SpringApplication.run(SpringbootServicioCommonsApplication.class, args);
//	}

}
