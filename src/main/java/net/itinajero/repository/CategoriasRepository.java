package net.itinajero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import net.itinajero.model.Categoria;

/*EXTIENDO A OTRA INTERFAZ
 * PRIMERO ENVIO EL TIPO DE MODELO QUE VAMOS A MAPEAR (CATEGORIA)
 * SEGUNDO EL DOMINIO DE LA LLAVE PRIMARIA (INTEGER)*/

//public interface CategoriasRepository extends CrudRepository<Categoria, Integer> {
public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {	

}
