package com.danchen.biblio.service;

import java.util.ArrayList;
import java.util.List;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.dao.ArticleDAO;

public class ArticleService {
	private ArticleDAO artDao;
	
	public ArticleService() {
		artDao = new ArticleDAO();
	}
	
	public List<Article> getArtsById(String articleId) {
		
		if(articleId != null){
			int id = Integer.parseInt(articleId);
			if(id >= 0){
				return artDao.getArtFamily(id);
			}
		}
		
		return new ArrayList();
	}
}
