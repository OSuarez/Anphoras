package ogs.temis.configuracion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends AbstractAuthenticationProcessingFilter{
	
	private static final Log logger= LogFactory.getLog(LoginFilter.class);
	
	public LoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
		InputStream body=req.getInputStream();
		
		Acceso user = new ObjectMapper().readValue(body, Acceso.class);
		logger.info("usuario:"+user.getUsuario()+":pw:"+user.getContrasena());
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsuario(),
						user.getContrasena(), 
						Collections.emptyList()
				)
		);
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest req, 
			HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		// Si la autenticacion fue exitosa, agregamos el token a la respuesta
		JwtUtil.addAuthentication(res,auth.getName());
	}

	
}
