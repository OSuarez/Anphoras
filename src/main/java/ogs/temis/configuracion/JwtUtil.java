package ogs.temis.configuracion;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static java.util.Collections.emptyList;

public class JwtUtil {

	//Metodo para crear el JWT y enviarlo al cliente en el header de la respuesta
	static void addAuthentication(HttpServletResponse res, String username) {
		
		String token = Jwts.builder()
				.setSubject(username)
				
				// Vamos a asignar un tiempo de expiracion de 1 minuto
				// solo con fines demostrativos en el video que hay alfinal
				
				// Hash con el que firmaremos la clave
				.signWith(SignatureAlgorithm.HS512, "P@tit0")
				.compact();
		
		res.addHeader("Authorization", "Bearer "+token);
	}
	
	static Authentication getAuthentication(HttpServletRequest request) {
		
		//Obtenemos el token que viene en el encabezado de la peticion
		String token = request.getHeader("Authorization");
		
		//Si hay un token presente, entonces lo validamos
		if(token != null) {
			String user = Jwts.parser()
					.setSigningKey("P@tit0")
					.parseClaimsJws(token.replace("Bearer", "")) //ese metodo es el que valida
					.getBody()
					.getSubject();
			//Recordemos que para las demas peticiones que no sean /login
			//no requerimos una autenticacion por username/password
			//por este motivo podemos devolver un UsernamePasswordAuthenticationToken si password
			
			return user != null ?
					new UsernamePasswordAuthenticationToken(user, null, emptyList()):
					null;
		}
		return null;
	}
}
