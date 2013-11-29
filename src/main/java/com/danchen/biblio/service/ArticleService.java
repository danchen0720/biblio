package com.danchen.biblio.service;

import java.util.ArrayList;
import java.util.List;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.dao.ArticleDAO;
import com.danchen.biblio.hibernate.dao.UserDAO;

public class ArticleService {
	private ArticleDAO artDao;
	private UserDAO userDao;
	
	public ArticleService() {
		artDao = new ArticleDAO();
		userDao = new UserDAO();
	}
	
	public List<Article> getToptics(){
		return artDao.findMainArts(10);
	}
	
	public List<Article> getPosts(){
		return artDao.findNoParentArts(10);
	}
	
	public Article getTopicByPost(String articleId) {
		if (articleId != null) {
			int id = Integer.parseInt(articleId);
			Article tempArt = artDao.findOne(id);
			for(; tempArt.getParent() > 0 ; ){
				tempArt = artDao.findOne(tempArt.getParent());
			}
			return tempArt;
		}
		return null;
	}
	
	public List<Article> getArtsById(String articleId,boolean onTreeView) {
		if (articleId != null) {
			int id = Integer.parseInt(articleId);
			if (id >= 0) {
				List<Article> temp = new ArrayList();
				if(onTreeView) {
					temp.add(artDao.findOne(id));
					return temp;
				} else {
					return artDao.getArtFamily(id);
				}
			}
		}
		return new ArrayList();
	}

	public List<Article> getPersonal(String userName) {
		return artDao.getByUser(userDao.findOneBy(userName), 10);
	}
}
