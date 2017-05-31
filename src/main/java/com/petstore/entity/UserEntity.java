package com.petstore.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="T_USER")
public class UserEntity extends AbstractEntity{
	
	@Id
	@Column(name = "IDT_USER")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
    @NotNull
    @NotEmpty
	@Column(name = "USERNAME",length = 50, nullable = false)
	private String username;
    
    @NotNull
    @NotEmpty
	@Column(name = "PASSWORD",length = 50, nullable = false)
	private String password;
	
    @Column(name = "LAST_NAME")
	private String lastName;
    
    @Column(name = "FIRST_NAME")
	private String firstName;
    
    @Column(name = "EMAIL")
	private String email;
    
    @Column(name = "PHONE")
	private String phone;
    
    @Column(name = "STATUS")
    private int status;
    
	@ManyToMany(fetch=FetchType.EAGER) 
	@JoinTable(name = "T_USER_ROLE", joinColumns = { @JoinColumn(name = "IDT_USER") }, inverseJoinColumns = {
			@JoinColumn(name = "IDT_ROLE") })
	@Fetch(value = FetchMode.SELECT)
	private List<RoleEntity> roles = new ArrayList<RoleEntity>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}
 
}
