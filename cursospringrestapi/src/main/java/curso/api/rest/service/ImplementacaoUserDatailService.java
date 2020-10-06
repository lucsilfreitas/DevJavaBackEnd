package curso.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import curso.api.rest.model.Usuarios;
import curso.api.rest.repository.UsuariosRepository;

@Service
public class ImplementacaoUserDatailService implements UserDetailsService {

	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Consulta no banco
		
		Usuarios usuario = usuariosRepository.findUserByLogin(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
		
		
		return new User(usuario.getLogin(), 
				usuario.getPassword(), 
				usuario.getAuthorities());
	}

}
