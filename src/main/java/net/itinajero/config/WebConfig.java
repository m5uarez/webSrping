package net.itinajero.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Clase para que las imagenes se busquen en mi disco C

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Value("${empleosapp.ruta.imagenes}") // el valor de la propiedad empleosapp.ruta.imagenes se inyecta en rutaImagenes
	private String rutaImagenes;
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) { 
		//registry.addResourceHandler("/logos/**").addResourceLocations("file:/empleos/img-vacantes/"); // Linux 
		//registry.addResourceHandler("/logos/**").addResourceLocations("file:c:/empleos/img-vacantes/"); // Windows
		registry.addResourceHandler("/logos/**").addResourceLocations("file:" + rutaImagenes); //La ventaja de poner tutaImagenes es q si cambio de propiertis impacta aqui tambien
	}


}
