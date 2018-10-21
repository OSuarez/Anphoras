package ogs.temis.repositorios;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ogs.temis.entidades.Acceso;

@Repository("accesoRepositorio")
public interface AccesoRepositorio extends JpaRepository<Acceso, Serializable>{

	public abstract Acceso findByUsuario(String usuario);
        
	public abstract Acceso findByUsuarioAndId(String usuario, long id);
        
}
/*
	public abstract Nota findByNombre(String nombre);
	
	public abstract List<Nota> findByTitulo(String titulo);
	
	public abstract Nota findByNombreAndTitulo(String nombre, String titulo);
	
	public abstract Nota findByNombreAndId(String nombre, long id);
	
	public abstract Page<Nota> findAll(Pageable pageable);

*/