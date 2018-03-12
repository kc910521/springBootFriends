/**
 * 
 */
package ind.ck.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author katsi02
 *
 */
@Entity
@Table(name="USERS")
public class User 
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String name;
	@Column(nullable=false, unique=true)
	private String email;
	@Column(nullable=false)
	private String password;
	@Column(nullable=true)
	private Integer sexual;

	private Date dob;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private Set<Role> roles = new HashSet<>();
	@Column(columnDefinition = "text")
	private String extra;
	
	public User() {
	}

	public User(int id, String name, String email, String password, Date dob,Integer sexual) {
	this.id = id;
	this.name = name;
	this.email = email;
	this.password = password;
	this.dob = dob;
	this.sexual = sexual;
}

	public Integer getSexual() {
		return sexual;
	}

	public void setSexual(Integer sexual) {
		this.sexual = sexual;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email
				+ ", dob=" + dob + ", sexual=" + sexual + "]";
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
