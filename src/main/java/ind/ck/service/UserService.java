/**
 * 
 */
package ind.ck.service;

import java.io.Serializable;
import java.util.List;

import ind.ck.entity.User;
import ind.ck.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	public void aclSave(String uStr){
		// Prepare the information we'd like in our access control entry (ACE)
		ObjectIdentity oi = new ObjectIdentityImpl(User.class, Long.valueOf(uStr));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		org.springframework.security.core.userdetails.User usr = null;
//		if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User){
//			usr = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
//		}

		Sid sid = new PrincipalSid(authentication);
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

	private MutableAcl findACL(Serializable identifier){
		ObjectIdentity oi = new ObjectIdentityImpl(User.class, identifier);

		//获取ObjectIdentity对应的Acl
		try {
			return  (MutableAcl) aclService.readAclById(oi);
		}catch (NotFoundException e){
			e.printStackTrace();
			return null;
		}

	}

	public List<User> findAll() {
//		MutableAcl c1 = this.findACL(1);
//		MutableAcl c2 = this.findACL(2);
//		MutableAcl c3 = this.findACL(98);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getPrincipal().toString());
		return userRepository.findAll();
	}

	public User create(User user) {
		//return userDao.create(user);
		return userRepository.save(user);
	}
	@PreAuthorize("hasPermission(#n, 'ind.ck.entity.User', 2)")
	public User findUserById(@Param("n") int id) {
		//return userDao.findUserById(id);
		return userRepository.findById(id).get();
	}

	// or hasPermission(filterObject, 'admin')
	@PreAuthorize("hasRole('USER')")
	@PostFilter("hasPermission(filterObject, 2)")
	public List<User> findAllInLaw() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getPrincipal().toString());
		return userRepository.findAll();
	}
	//if there is more than one argument which is a collection type then you have to select one by name using the filterTarget property of this annotation.
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

