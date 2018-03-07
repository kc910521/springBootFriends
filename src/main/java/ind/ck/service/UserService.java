/**
 * 
 */
package ind.ck.service;

import java.util.List;

import ind.ck.entity.User;
import ind.ck.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author Siva
 *
 */
@Service
@Transactional
public class UserService 
{
	//private UserDao userDao;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MutableAclService aclService;
	
	/*
	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	*/

	public void aclTry(){
		// Prepare the information we'd like in our access control entry (ACE)
		ObjectIdentity oi = new ObjectIdentityImpl(User.class, new Long(1));
		Sid sid = new PrincipalSid("Samantha");
		Permission p = BasePermission.ADMINISTRATION;

		// Create or update the relevant ACL
		MutableAcl acl = null;
		try {
			acl = (MutableAcl) aclService.readAclById(oi);
		} catch (NotFoundException nfe) {
			acl = aclService.createAcl(oi);
		}

		// Now grant some permissions via an access control entry (ACE)
		acl.insertAce(acl.getEntries().size(), p, sid, true);
		aclService.updateAcl(acl);
	}

	public List<User> findAll() {
		//return userDao.findAll();
		return userRepository.findAll();
	}

	public User create(User user) {
		//return userDao.create(user);
		return userRepository.save(user);
	}

//	public User findUserById(int id) {
//		//return userDao.findUserById(id);
//		return userRepository.findOne(id);
//	}

	public User login(String email, String password) {
		//return userDao.login(email,password);
		//return userRepository.login(email,password);
		return userRepository.findByEmailAndPassword(email,password);
	}

	public User update(User user) {
		return userRepository.save(user);
	}

//	public void deleteUser(int id) {
//		userRepository.delete(id);
//	}

	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	
	
}

