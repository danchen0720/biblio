package com.danchen.biblio.viewmodel;

import java.util.List;

import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.hibernate.dao.TagDAO;

public class ForumViewModel {
	private int selectedIndex;
	private TagDAO tagDao = new TagDAO();
	private List<Tag> tags;
	private Tag tag;
	
	//constructor
	public ForumViewModel() {
		tags = tagDao.findAll();
	}
	
	// getter and setter
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
}
