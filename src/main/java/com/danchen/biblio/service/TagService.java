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
	
	public List<Article> getArtsByTag(int id, boolean isTree) {
		List<Article> arts = new ArrayList<Article>();
		ArticleDAO artDao = new ArticleDAO();
		if(id < 0) 
			//get topic's top new posts
			arts.addAll(artDao.findMainArts());	
		else {
			//get article by tag and check state
			List<Article> tempList = new ArrayList(tagDao.findOne(id).getArticles());
			Article art;
			for(Iterator<Article> itr = tempList.iterator(); itr.hasNext(); ){
				art = itr.next();
				if(art.getState() != 1 || art.getParent() != 0)
					itr.remove();
			}
			arts.addAll(tempList);
		}
		if(!arts.isEmpty())
			lastPostTimeSort(arts);
		if(isTree)
			arts.add(0,artDao.findRoot(0));//add root	
		
		return arts;
	}
	
	private void lastPostTimeSort(List<Article> arts) {
		Collections.sort(arts, new Comparator<Article>() {
			public int compare(Article o1, Article o2) {
				Article post1;
				Article post2;
				
				if (o1.getPosts().size() > 0)
					post1 = o1.getPosts().iterator().next();
				else
					post1 = o1;
				
				if (o2.getPosts().size() > 0)
					post2 = o2.getPosts().iterator().next();
				else
					post2 = o2;
				
				if (post1.getTime().before(post2.getTime())) {
					return 1;
				} else if (post1.getTime().after(post2.getTime())) {
					return -1;
				} else {
					return 0;
				} 
			}
		});
	}
	
	
}
