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
	
	public List<Article> getArtsById(String articleId,boolean onTreeView) {
		
		if(articleId != null){
			int id = Integer.parseInt(articleId);
			if(id >= 0){
				if(onTreeView) {
					List<Article> temp = new ArrayList();
					temp.add(artDao.findOne(id));
					return temp;
				} else
					return artDao.getArtFamily(id);
			}
		}
		
		return new ArrayList();
	}
}
