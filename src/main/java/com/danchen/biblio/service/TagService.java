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
		Tag tag = tagDao.findOne(id);
		if(tag != null){
			List<Article> artTree =  new ArrayList<Article>();
			ArticleDAO artDao = new ArticleDAO();

			artTree.addAll(tag.getArticles());
			//sort 
//			Collections.sort(artTree, new Comparator<Article>() {
//				public int compare(Article o1, Article o2) {
//					 if (o1.getTime().before(o2.getTime())) {
//				            return -1;
//				        } else if (o1.getTime().after(o2.getTime())) {
//				            return 1;
//				        } else {
//				            return 0;
//				        }    
//				}
//			});

			//add root
			artTree.add(0,artDao.findOne(0));
			System.out.println(artTree.size());
			return artTree;
		}
		return null;
	}
	
}
