package net.itinajero.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Categorias")//INDICO A QUE TABLA DE LA BASE HACE REFERENCIA, TIENE QUE SER EL NOMBRE TAL CUAL FIGURA EN LA BASE
public class Categoria {
	
	@Id //PARA REALIZAR EL MAPEO CON LA LLAVE PRIMARIA
	@GeneratedValue(strategy=GenerationType.IDENTITY)  //INDICO QUE SE VA AUTOGENERAR EL VALOR Y ES AUTOINCREMENTAL
	private Integer id;
	private String nombre;
	private String descripcion;
	
	public Categoria() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
	
	

}
