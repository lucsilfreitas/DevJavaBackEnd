package curso.api.rest.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import curso.api.rest.model.Usuarios;

@Repository
public interface UsuariosRepository extends CrudRepository<Usuarios, Long> {

	@Query("select u from Usuarios u where  u.login = ?1")
	Usuarios findUserByLogin(String login);
	
	
	
}
