package net.itinajero.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.itinajero.model.Categoria;
import net.itinajero.model.Vacante;

public interface ICategoriasService {
	
	
	public void guardar(Categoria categoria);
	public List<Categoria> buscarTodas();
	public Categoria buscarPorId(Integer idCategoria);	
	public void eliminar(Integer idCategoria);
	public Page<Categoria> buscarTodas(Pageable page);
}

/**
 *  TODO: 1. Crear la clase CategoriasServiceImpl que implemente esta Interfaz (Guardar las categorías en una lista (LinkedList))
 *  
 *  TODO: 2. Inyectar la clase de servicio en CategoriasController.
 *  
 *  TODO: 3. Completar los siguientes métodos en CategoriasController:
 *  
 *  	mostrarIndex -> Renderizar el listado de Categorias (listCategorias.html)
 *  					Configurar la URL del botón para crear una Categoría
 *		
 *		guardar -> Guardar el objeto Categoria a traves de la clase de servicio
 *				   Validar errores de Data Binding y mostrarlos al usuario en caso de haber
 *				   Mostrar al usuario mensaje de confirmacion de registro guardado
 *	
 *	TODO: 4. Agregar un nuevo menu llamado Categorias y configurar la URL al listado de Categorias
 *		  	
 */
