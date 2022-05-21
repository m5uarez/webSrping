/**
 * Esta clase representa una entidad (un registro) en la tabla de Usuarios de la base de datos
 */
package net.itinajero.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment MySQL
	private Integer id;
	private String username;
	private String nombre;
	private String email;
	private String password;
	private Integer estatus;	
	private Date fechaRegistro;
	
	//Permite que un usuario tenga muchos perfiles asigandos
	@ManyToMany(fetch=FetchType.EAGER)// fetch=FetchType.EAGER sirve para que cuando haga una colsuta se recupere todos los perfiles que tiene asigando el usuario
	
	//"UsuarioPerfil" ES EL NOMBRE QUE COINCIDE CON EL QUE ESTA EN LA TABLA DE LA DB
	/*ES MUY IMPORTANTE RESPETAR EL ORDEN!!!
	 * EN joinColumns = @JoinColumn(name="idUsuario") DEBE IR EL NOMBRE DE LA TABLA EN QUE ESTAMOS
	 * inverseJoinColumns = @JoinColumn(name="idPerfil") EL NOMBRE DE LA OTRA LLAVE FORANEA
	 * */
	@JoinTable(name="UsuarioPerfil",
			   joinColumns = @JoinColumn(name="idUsuario"),
			   inverseJoinColumns = @JoinColumn(name="idPerfil")			
			   )//AQUI SE CONFIGURA LA TABLA INTERMEDIA QUE MODELA EL MUCHOS A MUCHOS ENTRE USUARIOS Y PERFILES
	
	private List<Perfil> perfiles;
	
	/*METODO QUE
	 * PERMITE AGREGAR VARIOS PERFILES AL USUARIO
	 * */
	public void agregar(Perfil tempPerfil) {
		if (perfiles == null) {
			perfiles = new LinkedList<Perfil>();
		}
		perfiles.add(tempPerfil);
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
		

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", nombre=" + nombre + ", email=" + email
				+ ", password=" + password + ", estatus=" + estatus + ", fechaRegistro=" + fechaRegistro + "]";
	}
	
}
