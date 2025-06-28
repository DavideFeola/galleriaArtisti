package it.uniroma3.siw.authentication;
import java.io.IOException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
//public  class WebSecurityConfig {
public class AuthConfiguration {
	 @Autowired
	    private DataSource dataSource;
	    
	    /*specifica come il sistema deve recuperare username, password e ruoli del DB, quindi le Credentials*/

	 @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.jdbcAuthentication()
	                .dataSource(dataSource)
	                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credenziali WHERE username=?")
	                .authoritiesByUsernameQuery("SELECT username, ruolo FROM credenziali WHERE username=?");
	    }
	    
	    
	    /*metodo per verificare l'uguaglianza della password criptata*/
	    
	    @Bean
	    public PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	        
	    }
	    
	    

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
	        return authenticationConfiguration.getAuthenticationManager();
	    }

	    
	    /*definisce il filtro HTTP che gestisce le policies di autenticazione e autorizzazione attraverso una lunga serie di inocazioni concatenate all'oggetto httpSecurity: i metodi restituiscono lo stesso oggetto http aggiornato..*/
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	        httpSecurity
	                .csrf().disable()
	                .authorizeRequests()
	                .requestMatchers(HttpMethod.GET, "/", "/index", "/index.html", "/register", "/css/**", "/images/**", "/favicon.ico", "/aggiorna", "/classifica").permitAll()
	                .requestMatchers(HttpMethod.POST, "/register", "/index", "/index.html").permitAll()
	                .requestMatchers("/artista/**").hasAuthority("ARTISTA")
	                .anyRequest().authenticated()
	                .and()
	                .formLogin()
	                .loginPage("/login")
	                .permitAll()
	                .defaultSuccessUrl("/", true)
	                .failureUrl("/login?erroreLogin")
	                //.failureHandler(authenticationFailureHandler())
	                .and()
	                .logout()
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/")
	                .invalidateHttpSession(true)
	                .deleteCookies("JSESSIONID")
	                .permitAll()
	                .and()
	                .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
	                .sessionFixation().migrateSession();  // Mantieni la sessione dopo il login

	        return httpSecurity.build();
	    }

	    @Bean
	    public AuthenticationFailureHandler authenticationFailureHandler() {
	        return new SimpleUrlAuthenticationFailureHandler() {
	            @Override
	            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	                                                AuthenticationException exception) throws IOException {
	                getRedirectStrategy().sendRedirect(request, response, "/login?error=TRUE");
	            }
	        };
	    }
}