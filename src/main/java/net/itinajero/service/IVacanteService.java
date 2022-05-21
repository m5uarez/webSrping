package net.itinajero.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.itinajero.model.Vacante;

public interface IVacanteService {
	
	public List<Vacante> buscarTodas();
	public Vacante buscarPorId(Integer idVacante);
	public void guardar(Vacante vacante);
	public List<Vacante> buscarDestacadas();
	public void eliminar(Integer idVacante);
	public List<Vacante> buscarByExample(Example<Vacante> example);//permite hacer una consulta dinamica(que varia) en la parte del where, pone los atributos no nulos del objeto en el where para consultar
	public Page<Vacante> buscarTodas(Pageable page);
	
}
