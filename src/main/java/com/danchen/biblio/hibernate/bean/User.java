package com.danchen.biblio.hibernate.bean;

// Generated Nov 22, 2013 6:45:27 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

	private Integer id;
	private String password;
	private String username;
	private Set articles = new TreeSet();

	public User() {
	}

	public User(Integer id) {
		this.id = id;
	}

	public User(String password, String username) {
		this.password = password;
		this.username = username;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set getArticles() {
		return this.articles;
	}

	public void setArticles(Set articles) {
		this.articles = articles;
	}

}
