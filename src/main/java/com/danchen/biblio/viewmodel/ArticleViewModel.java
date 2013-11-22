package com.danchen.biblio.viewmodel;

import java.util.List;

import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.hibernate.dao.TagDAO;

public class ArticleViewModel {
	private TagDAO tagdao = new TagDAO();
	private List<Tag> tagList;
	private Tag selectedTag;
	
	public List<Tag> getTagList() {
		return tagList;
	}
	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}
	
	public Tag getSelectedTag() {
		return selectedTag;
	}
	public void setSelectedTag(Tag selectedTag) {
		this.selectedTag = selectedTag;
	}

	public ArticleViewModel() {
		tagList = tagdao.findAll();
	}
}
