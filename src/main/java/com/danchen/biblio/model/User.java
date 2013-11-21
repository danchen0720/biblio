package com.danchen.biblio.model;

// Generated Nov 21, 2013 4:45:51 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

	private String id;
	private String password;
	private Set articles = new HashSet(0);

	public User() {
	}

	public User(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public User(String id, String password, Set articles) {
		this.id = id;
		this.password = password;
		this.articles = articles;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set getArticles() {
		return this.articles;
	}

	public void setArticles(Set articles) {
		this.articles = articles;
	}

}