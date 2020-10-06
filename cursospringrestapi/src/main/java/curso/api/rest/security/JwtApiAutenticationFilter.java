package curso.api.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


//Filtro onde todas as requisiçoes serao capturadas para uatenticar
public class JwtApiAutenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//estabelece autanticação para a requisicao
		Authentication authentication = new JWTTokenAutenticacaoService()
				.getAuthentication((HttpServletRequest) request);
		
		//Coloca o processo de autanticaçao no spring security
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//contunula o processamento
		chain.doFilter(request, response);
		
		
		
		
		
	}

}
