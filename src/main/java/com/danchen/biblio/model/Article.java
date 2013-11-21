package com.danchen.biblio.model;

// Generated Nov 21, 2013 4:45:51 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Article generated by hbm2java
 */
public class Article implements java.io.Serializable {

	private Integer id;
	private User user;
	private Date time;
	private Integer parent;
	private int state;
	private Clob content;
	private String title;
	private Set tags = new HashSet(0);

	public Article() {
	}

	public Article(User user, Date time, int state) {
		this.user = user;
		this.time = time;
		this.state = state;
	}

	public Article(User user, Date time, Integer parent, int state,
			Clob content, String title, Set tags) {
		this.user = user;
		this.time = time;
		this.parent = parent;
		this.state = state;
		this.content = content;
		this.title = title;
		this.tags = tags;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getParent() {
		return this.parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Clob getContent() {
		return this.content;
	}

	public void setContent(Clob content) {
		this.content = content;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set getTags() {
		return this.tags;
	}

	public void setTags(Set tags) {
		this.tags = tags;
	}

}
