package com.danchen.biblio.viewmodel;

import java.util.List;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.dao.ArticleDAO;

public class ArticleViewModel {
	private ArticleDAO artDao = new ArticleDAO();
	private List<Article> artList;
	private Article article;
	
	public List<Article> getArtList() {
		return artList;
	}
	public void setArtList(List<Article> artList) {
		this.artList = artList;
	}

	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}

	public ArticleViewModel() {
		artList = artDao.findAll();
	}
}
