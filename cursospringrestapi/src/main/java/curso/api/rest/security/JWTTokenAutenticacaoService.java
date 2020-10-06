package curso.api.rest.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import curso.api.rest.ApplicationContextLoad;
import curso.api.rest.model.Usuarios;
import curso.api.rest.repository.UsuariosRepository;
import io.jsonwebtoken.Jwts;

@Service
@Component
public class JWTTokenAutenticacaoService {

	// TEMPO DE EXPIRAÇÃO DO 2 DIAS TOKEN
	private static final long EXPIRATION_TIME = 172800000;

	// senha unica para compor a autenticacao
	private static final String SECRET = "SenhaExtremamenteSecreta";

	// prefixo padrao de token
	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	// Gernado Token de autenticação a adicionando cabeçalho e resposta
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {
		String JWT = Jwts.builder().setSubject(username) // adiciona o usuario
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))// expiracao
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, SECRET).compact(); // compactacao de algoritimo

		// Junta o token com o prefixo

		String token = TOKEN_PREFIX + "" + JWT;

		// Adiciona no cabeçalho http
		response.addHeader(HEADER_STRING, token);

		// Escreve o token com resposta no corpo do HTTP
		response.getWriter().write("{\"Authorization\":\"" + token + "\"}");
	}

	public Authentication getAuthentication(HttpServletRequest request) {

		// Pega o token enviado no cabeçalho http
		String token = request.getHeader(HEADER_STRING);

		if (token != null) {

			// faz a validação do token do user na requisicao
			String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
					.getSubject();

			if (user != null) {

				Usuarios usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuariosRepository.class)
						.findUserByLogin(user);
				// retornar o usuario logado

				if (usuario != null) {

					return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(),
							usuario.getAuthorities());
				}

			}

		}

		return null;// não autorizado

	}

}