package ogs.temis.servicios;

import java.util.ArrayList;
import java.util.List;
import ogs.L;
import ogs.temis.entidades.Acceso;
import ogs.temis.repositorios.AccesoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("accesoServicio")
public class AccesoServicio implements UserDetailsService {

    @Autowired
    @Qualifier("accesoRepositorio")
    private AccesoRepositorio accesoRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Acceso user = accesoRepositorio.findByUsuario(username);
        return new User(user.getUsuario(),
                user.getContrasena(),
                user.isActivo(),
                user.isActivo(),
                user.isActivo(),
                user.isActivo(),
                buildgranted(user.getRol()));
    }

    public List<GrantedAuthority> buildgranted(byte rol) {
        String[] roles = {"LECTOR", "USUARIO", "ADMINISTRADOR"};
        List<GrantedAuthority> auths = new ArrayList<>();

        for (int i = 0; i < rol; i++) {
            auths.add(new SimpleGrantedAuthority(roles[i]));
        }
        return auths;
    }

    public boolean crear(Acceso acceso) {
        try {
            acceso.setContrasena(
                    (new BCryptPasswordEncoder())
                    .encode(acceso.getContrasena()
                    )
            );
            accesoRepositorio.save(acceso);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean actualizar(Acceso acceso) {
        L.L("servicio:Acceso:actualizar");
        L.L("id:" + acceso.getId() + ":usuario:" + acceso.getUsuario());
        if (acceso.getId() == 0) {
            L.L("servicio:Acceso:actualizar:Id nula");
            return false;
        }
        if (accesoRepositorio.findByUsuario(acceso.getUsuario()) == null) {
            L.L("servicio:Acceso:actualizar:No existe usuario");
            return false;
        }
        L.L("servicio:Acceso:actualizar:contrasena:"+acceso.getContrasena()+"");
        if(acceso.getContrasena()!= null)
            acceso.setContrasena(
                    (new BCryptPasswordEncoder())
                    .encode(acceso.getContrasena()
                    )
            );
        accesoRepositorio.save(acceso); //antes verificar que id no sea 0 o nulo
        return true;
    }

    public boolean borrar(String usuario, long id) {
        try {
            Acceso acceso = accesoRepositorio.findByUsuarioAndId(usuario, id);
            accesoRepositorio.delete(acceso);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Acceso> obtener() {
        return accesoRepositorio.findAll();
    }

    public Acceso obtenerUsuario(String usuario) {
        return accesoRepositorio.findByUsuario(usuario);
    }

}
