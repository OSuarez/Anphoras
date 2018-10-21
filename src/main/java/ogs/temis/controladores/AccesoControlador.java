package ogs.temis.controladores;

import java.util.List;
import javax.validation.Valid;
import ogs.L;
import ogs.temis.entidades.Acceso;
import ogs.temis.servicios.AccesoServicio;
import ogs.temis.servicios.ServicioExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/0")
public class AccesoControlador {

    	@Autowired
	@Qualifier("accesoServicio")
	AccesoServicio servicio;

	@PutMapping("/acceso")
	public boolean agregar(@RequestBody @Valid Acceso modelo) {
		return servicio.crear(modelo);
	}
	
	@PostMapping("/acceso")
	public boolean actualizar(@RequestBody @Valid Acceso modelo) {
            L.L("controlador:Acceso:actualizar");
            return servicio.actualizar(modelo);
	}
	
	@DeleteMapping("/acceso/{id}/{usuario}")
	public boolean borrar(@PathVariable("id") long id, @PathVariable("usuario") String usuario) {
		return servicio.borrar(usuario, id);
	}
	
	@GetMapping("/accesos")
	public List<Acceso> obtener(){
		return servicio.obtener();
	}

        @GetMapping("/acceso/{usuario}")
	public Acceso obtenerUsuario(@PathVariable("usuario") String usuario){
		return servicio.obtenerUsuario(usuario);
	}
}
