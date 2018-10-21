package ogs.temis.configuracion;

import ogs.temis.servicios.AccesoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class TemisSeguridad extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("accesoServicio")
	private AccesoServicio userDetailsService;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService);
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/login","/").permitAll() //permitimos el acceso a /login a cualquiera
		.anyRequest().authenticated() //otra peticion requiere autenticacion
		.and()
		// Las peticiones /login pasaran previamente por este filtro
		.addFilterBefore(new LoginFilter("/login",authenticationManager()),
				UsernamePasswordAuthenticationFilter.class)
		//Las demas peticiones pasaran por este filtro para validar el token
		.addFilterBefore(new JwtFilter(),
				UsernamePasswordAuthenticationFilter.class);
		
				
	}
}