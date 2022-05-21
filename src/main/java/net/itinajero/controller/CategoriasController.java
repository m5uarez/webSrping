package net.itinajero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.model.Categoria;
import net.itinajero.model.Vacante;
import net.itinajero.service.ICategoriasService;


@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {
	
	//Inyecta clase de servicios en controlador
		@Autowired
		private ICategoriasService serviceCategorias;
	
	// @GetMapping("/index")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model) {
		
		List<Categoria> lista = serviceCategorias.buscarTodas();
		model.addAttribute("categorias", lista);
		
		return "categorias/listCategorias";
	}
	
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
			Page<Categoria> lista = serviceCategorias.buscarTodas(page);
			model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}

	
	
	// @GetMapping("/create")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear() {
		return "categorias/formCategoria";
	}
	
	
	// @PostMapping("/save")
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria, BindingResult result,  RedirectAttributes attributes) {
		
		/*Si hay errores da verdadero Result. regreso al formulario para que complete de nuevo*/
		if(result.hasErrors()) {
			
			/*Con el FOR puedo mostra los erores en la consola. SOn clases propias de Spring*/
			
			for (ObjectError error: result.getAllErrors()){
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
				}
			
			return "vacantes/formVacante";
		}
		
		serviceCategorias.guardar(categoria);
		System.out.println("Categoria: " + categoria);
		
		attributes.addFlashAttribute("msg", "Registro Guardado");//es temporal, se almacena antes del redicet, se muestra en la pagina del reidecrt y automaticamente se borra. Dura una vez
		return "redirect:/categorias/index";
		
		/*
		System.out.println("Categoria: " + nombre);
		System.out.println("Descripcion: " + descripcion);
		return "categorias/listCategorias";
		*/
	}
	
	/*  ELIMINAAR CATEGORIA PERO COMO TIENE ASOCIADA UNA VACANTE LE PONGO EL TRY--COMO EN EL METODO DE ABAJO
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) //Coloco @PathVariable ya que el ID es dinamico
	{
		
		System.out.println("Borrado categoria con id: " + idVacante);
		serviceCategorias.eliminar(idVacante);
		attributes.addFlashAttribute("msg", "La categoria fue eliminada!");
		
		return "redirect:/categorias/index";
		
	}
	
	 */
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes) {
	 
		try {	
		
			// Eliminamos la categoria.
			serviceCategorias.eliminar(idCategoria);		
			attributes.addFlashAttribute("msg", "La categoría fue eliminada!.");
	 
		}catch(Exception ex) {
			attributes.addFlashAttribute("msg", "No es posible eliminar la Categoría seleccionada!.");
		}
	 
		return "redirect:/categorias/index";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model) {
		
		Categoria categoria = serviceCategorias.buscarPorId(idCategoria);
		model.addAttribute("categoria", categoria);
		
		
		return "categorias/formCategoria" ;
	}

}
