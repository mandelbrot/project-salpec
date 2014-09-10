package com.otv.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {

	private int id;
	private String name;
	private String pass;
	private String email;
	private String language;

	@Id
	@Column(name="ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public User() {
	}
	
	public User(String name, String pass, int id, String email) {
		this.name = name;
		this.pass = pass;
		this.id = id;
		this.email = email;
	}

	@Column(name="name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="pass", unique = true, nullable = false)
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}	

	@Column(name="email", unique = true, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	@Column(name="language", unique = true, nullable = false)
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}	
	
	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("id : ").append(getId());
		strBuff.append(", username : ").append(getName());
		strBuff.append(", pass : ").append(getPass());
		strBuff.append(", email : ").append(getEmail());
		strBuff.append(", language : ").append(getLanguage());
		return strBuff.toString();
	}
}
