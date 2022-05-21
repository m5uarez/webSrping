package net.itinajero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.itinajero.model.Vacante;

public interface VacantesRepository extends JpaRepository<Vacante, Integer> {

	//METODOS QUERY
	
	public List<Vacante> findByEstatus(String estatus);
	
	//Busca por destacado y estatus, ademas ordena de forma descendente a paprtir del ID
	public List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado,String estatus);
	
	//Busca vacantes usando un rango de salarios
	
	public List<Vacante> findBySalarioBetweenOrderBySalarioDesc(double s1,double s2);
	
	//Busca vacantes por varios estatus
	public List<Vacante> findByEstatusIn(String [] estatus);
}
