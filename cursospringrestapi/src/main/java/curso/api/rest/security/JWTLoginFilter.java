package curso.api.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import curso.api.rest.model.Usuarios;

//Estabelece o nosso gerenciador de token
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	//configura o gerenciado de autenticacao
	protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		
		//obriga autnticar url
		super(new AntPathRequestMatcher(url));
		
		//gerenciador de autenticação
		setAuthenticationManager(authenticationManager);
		
		
	}

	// retorna o usuario que está sendo processado
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		//Pegando o token para validar
		Usuarios user = new ObjectMapper()
				.readValue(request.getInputStream(), Usuarios.class);
		
		//retorna o usuario login e senha e acessos
		return getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

			new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
		
	}
}
