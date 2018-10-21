package ogs.temis.bootstrap;

import ogs.L;
import ogs.temis.entidades.Acceso;
import ogs.temis.repositorios.AccesoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CargaUsuario implements ApplicationListener<ContextRefreshedEvent> {

    private AccesoRepositorio accesoRepositorio;
    @Autowired
    public void setAccesoRepositorio(AccesoRepositorio accesoRepositorio){
        this.accesoRepositorio=accesoRepositorio;
    }
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        L.L("CargaUsuario Inicial:Usuarios:"+accesoRepositorio.count());
        if(accesoRepositorio.count()!= 0) return;
        {
            Acceso u=new Acceso();
            u.setUsuario("user");
            u.setContrasena((new BCryptPasswordEncoder()).encode("12345"));
            u.setRol((byte)1);
            u.setActivo(false);
            accesoRepositorio.save(u);
        }
        {
            Acceso u=new Acceso();
            u.setUsuario("admin");
            u.setContrasena((new BCryptPasswordEncoder()).encode("12345"));
            u.setRol((byte)2);
            u.setActivo(true);
            accesoRepositorio.save(u);
        }
        
    }   

    
}
