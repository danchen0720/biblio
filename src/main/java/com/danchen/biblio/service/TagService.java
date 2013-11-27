package com.danchen.biblio.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.hibernate.dao.ArticleDAO;
import com.danchen.biblio.hibernate.dao.TagDAO;

public class TagService {
	private TagDAO tagDao;
	
	public TagService() {
		tagDao = new TagDAO();
	}
	
	public Tag getTagById(int id) {
		Tag result = null;
		if (id >= 0)
			result = tagDao.findOne(id);
		return result;
	}
	public Tag getTagById(String id) {
		Tag result = null;
		if (id != null && id.length() > 0)
			result = tagDao.findOne(Integer.parseInt(id));
		return result;
	}
	
	public List<Tag> getAll() {
		List<Tag> result = tagDao.findAll();
		if (result.isEmpty())
			result = null;
		return result;
	}
	
	public List<Article> getArticleTreeByTag(int id) {
		List<Article> artTree =  new ArrayList<Article>();
		ArticleDAO artDao = new ArticleDAO();
	
		if(id < 0) 
			artTree.addAll(artDao.findMainArts());
		else 
			artTree.addAll(tagDao.findOne(id).getArticles());

	
		//add root	
		artTree.add(0,artDao.findOne(0));
		return artTree;
	}
	
}
