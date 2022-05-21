package net.itinajero.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.model.Vacante;
import net.itinajero.service.ICategoriasService;
import net.itinajero.service.IVacanteService;
import net.itinajero.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	//INYECTA EN LA VARIABLE RUTA EL VALOR DE LA PROPIEDAD QUE ESTA EN PROPERTIES
	@Value("${empleosapp.ruta.imagenes}")
	private String ruta;
	
	//Inyecta clase de servicios en controlador
	@Autowired
	private IVacanteService serviceVacantes;
	
	@Autowired
	private ICategoriasService serviceCategoria;
	
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes", lista);
		
		return "vacantes/listVacantes";
	}
	
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
			Page<Vacante> lista = serviceVacantes.buscarTodas(page);
			model.addAttribute("vacantes", lista);
			return "vacantes/listVacantes";
	}

	
	
	@GetMapping("/create") /*Pasando el objeto vacante puedo vincular mi objeto con el formularrio asi despliego los errores*/
	public String crear(Vacante vacante, Model model) {
		
		return "vacantes/formVacante";
	}
	
	
	/*USA  Data Binding SI EL OBJETO COINCIDE CON LOS DATOS DEL FORMULARIO AUTOMATICAMENTE LE ASIGNA A LOS CAMPOS
	 * EL OBJETO DEBE TENER LOS MISMOS DATOS QUE CARGA EL FORMULARIO Y "NAME" EN EL FORM DEBE SER EL MISMO QUE EN EL OBJETO DECLARADO*/
	
	/*BINDING result, lo uso para capturar errores en la conversion, debe ir inmediatamente despues del objero, 
	 * en esta caso vacante, en vacante estan declarados los datos que tiene cada campo..sino no funciona
	 * */

	/* RedirectAttributes los uso con atributos flash
	 * */
	
	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result,  RedirectAttributes attributes,  @RequestParam("archivoImagen") MultipartFile multiPart) {
		
		/*Si hay errores da verdadero Result. regreso al formulario para que complete de nuevo*/
		if(result.hasErrors()) {
			
			/*Con el FOR puedo mostra los erores en la consola. SOn clases propias de Spring*/
			
			for (ObjectError error: result.getAllErrors()){
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
				}
			
			return "vacantes/formVacante";
		}
		
		if (!multiPart.isEmpty()) {
				//String ruta = "/empleos/img-vacantes/"; // Linux/MAC
				//String ruta = "c:/empleos/img-vacantes/"; // Windows
				String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
				if (nombreImagen != null){ // La imagen si se subio
					// Procesamos la variable nombreImagen
					vacante.setImagen(nombreImagen); 
					}
			}
		
		serviceVacantes.guardar(vacante);
		System.out.println("Vacante: " + vacante);
		
		attributes.addFlashAttribute("msg", "Registro Guardado");//es temporal, se almacena antes del redicet, se muestra en la pagina del reidecrt y automaticamente se borra. Dura una vez
		return "redirect:/vacantes/index"; //CON REDIRECT, NO MANDA A UNA VISTA SINO HAGO UNA PETICION HTTP A INDEX ASI ME RECARGA Y TRAE TODO, REDIRECCCIONA
	}
	
	/*
	@PostMapping("/save")
	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion, @RequestParam("estatus") String estatus, @RequestParam("fecha") String fecha,
			@RequestParam("destacado") int destacado, @RequestParam("salario") double salario, @RequestParam("detalles") String detalles) {
		
		System.out.println("Nombre: " + nombre);
		System.out.println("Descripcion: " + descripcion);
		System.out.println("Estatus: " + estatus);
		System.out.println("Fecha de publicacion: " + fecha);
		System.out.println("Destacado: " + destacado);
		System.out.println("Salario: " + salario);
		System.out.println("Detalles: " + detalles);
		
		return "vacantes/listVacantes";
	}
	*/
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) //Coloco @PathVariable ya que el ID es dinamico
	{
		
		System.out.println("Borrado vacante con id: " + idVacante);
		serviceVacantes.eliminar(idVacante);
		attributes.addFlashAttribute("msg", "La vacante fue eliminada!");
		
		return "redirect:/vacantes/index";
		
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model) {
		
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		
		
		return "vacantes/formVacante" ;
	}
	
		

	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {
		
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		
		System.out.println("Vacante: "+ vacante);
		model.addAttribute("vacante", vacante);
		
		//BUSCAR LOS DETALLES DE LA VACANTE EN LA DB
		
		return "detalle";
	}
	
	/*@InitBinder 
	 * CUANDO RECIBO FECHAS DE UN FORMULARIO SIEMPRE DEBO TENER EL METODO DE ABAJO
	 * PARA DARLE EL FORMATO DE FECHA QUE USA EL SERVIDOR DONDE SE ESTA EJECUTANDO*/
	
	@InitBinder
	public void initBinder (WebDataBinder webDataBinder) {
		
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
		
	}
	
	//AGREGA DATOS AL MODELO QUE SON COMUNES PARA TODO EL CONTROLADOR
		@ModelAttribute
		public void setGenericos(Model model) {
			model.addAttribute("categorias", serviceCategoria.buscarTodas());
		}
}
