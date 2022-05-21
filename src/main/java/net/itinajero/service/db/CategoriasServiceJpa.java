package net.itinajero.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.itinajero.model.Categoria;

import net.itinajero.repository.CategoriasRepository;
import net.itinajero.service.ICategoriasService;


//COMO ES CALSE DE SERVICIO AGREGO @Service
@Service
@Primary //AGREGO ESTA ANOTACION PARA DECIR QUE ESTA ES LA IMPLEMENTACION QUE DEBE USAR. TENGO DOS, ESTA Y categoriasServiceImpl. AL NO SABER CUAL USAR EL CONTROLADOR,CUAL INYECTAR, DA ERROR
public class CategoriasServiceJpa implements ICategoriasService {

	@Autowired
	private CategoriasRepository categoriasRepo;
	
	@Override
	public void guardar(Categoria categoria) {
		categoriasRepo.save(categoria);

	}

	@Override
	public List<Categoria> buscarTodas() {
		
		return categoriasRepo.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> optional = categoriasRepo.findById(idCategoria);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}
	
	

	@Override
	public void eliminar(Integer idCategoria) {
		categoriasRepo.deleteById(idCategoria);
		
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		
		return categoriasRepo.findAll(page);
	}


}
