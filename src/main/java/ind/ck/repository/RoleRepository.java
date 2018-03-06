package ind.ck.repository;

import java.io.Serializable;

import ind.ck.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Serializable>
{

}
