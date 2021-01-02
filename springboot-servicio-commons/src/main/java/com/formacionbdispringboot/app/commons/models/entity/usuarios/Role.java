package com.formacionbdispringboot.app.commons.models.entity.usuarios;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true,length = 30)
	private String nombre;
	
	//Asi se mapearia si se estableciera una relacion muchos a muchos.. con mappedBy. No es el caso de esta app con lo cual se quitaria
//	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
//	private List<Role> Usuarios;
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	private static final long serialVersionUID = 4533494369883762369L;

}
