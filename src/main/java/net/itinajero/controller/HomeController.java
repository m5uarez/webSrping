package net.itinajero.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import net.itinajero.model.Perfil;
import net.itinajero.model.Usuario;
import net.itinajero.model.Vacante;
import net.itinajero.service.ICategoriasService;
import net.itinajero.service.IUsuariosService;
import net.itinajero.service.IVacanteService;


@Controller /*para que la clase funcione como controlador*/
public class HomeController {
	
	@Autowired
	private IVacanteService serviceVacantes;
	
	@Autowired
   	private IUsuariosService serviceUsuarios;
	
	@Autowired
   	private ICategoriasService serviceCategorias;
	
	@GetMapping("/") //EL metodo responde a una peticion get a la url /
	public String mostrarHome(Model model) {
		/*
		model.addAttribute("mensaje", "Bienvenidos a empleos App");
		model.addAttribute("fecha", new Date());
		*/
		/*ESTO YA NO HACE FALTA, LO AGREGO EN EL MODELO SETGENERICOS
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes", lista);
		*/
		return "home"; //represetna el nombre del archivo que es renderizado cuando se hace la peticion a /
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		usuario.setEstatus(1); // Activado por defecto
		usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual del servidor
		
		// Creamos el Perfil que le asignaremos al usuario nuevo
		Perfil perfil = new Perfil();
		perfil.setId(3); // Perfil USUARIO
		usuario.agregar(perfil);
		
		/**
		 * Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
		 */
		serviceUsuarios.guardar(usuario);
				
		attributes.addFlashAttribute("msg", "El registro fue guardado correctamente!");
		
		return "redirect:/usuarios/index";
	}
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes", lista);
		
		return "tabla";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		Vacante vacante = new Vacante();
		vacante.setNombre("Ingeniero de comunicaciones");
		vacante.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		model.addAttribute("vacante", vacante);
		return "detalle";
	}
	
	@GetMapping("/listado")
	public String mostrarListado(Model model) {
		List<String> lista = new LinkedList<String>();
		lista.add("Ingeniero  de Sistemas");
		lista.add("Auxiliar de Contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		
		model.addAttribute("empleos", lista);
		
		return "listado";
	}
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) //Con search voy hacer el data biding, es el nombre del atributo de modelo
	{
		System.out.println("Buscando por " + vacante);
		
		/*La siguiente linea permite modificar la consulta SQL
		 * cambia el igual, descripcion = a tal cosa 
		 * por like
		 * where descripcion like %?%
		 * Lo hago para fuscar por frases en la descripcion
		 * */
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		
		Example<Vacante> example= Example.of(vacante, matcher);
		List<Vacante> lista = serviceVacantes.buscarByExample(example);
		model.addAttribute("vacantes", lista);
		
		
		return "home";
	}
	
	/*
	 * InitBinder para String si los detecta vacios en el data baiding los settea a null
	 * @param Binder
	 * Me sirve en la busqueda, si completo un campo de los dos al otro le pone null y ya no busca por ese en el metodo buscarByExample
	 * */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	//Metodo para agregar datos al modelo
	
		@ModelAttribute//Si a aplicamos a nivel de metodo, podemos agregamos al modelo todos los atributos y van a estar disponibles para todos los metodos del controlador
		public void setGenericos(Model model) {
			Vacante vacanteSearch=new Vacante();//Para usar data binding en la busqueda
			vacanteSearch.reset();
			model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
			model.addAttribute("categorias", serviceCategorias.buscarTodas());
			model.addAttribute("search", vacanteSearch);
		}
	
}
