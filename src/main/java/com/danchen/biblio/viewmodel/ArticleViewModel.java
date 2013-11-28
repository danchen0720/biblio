package com.danchen.biblio.viewmodel;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.service.ArticleService;

public class ArticleViewModel {
	private List<Article> arts;
	private ArticleService artServ = new ArticleService();
	public ArticleViewModel() {
		System.out.println("ArticleVM");
	}

	public List<Article> getArts() {
		String articleId = (String) Executions.getCurrent().getAttribute("articleId");   
		System.out.println(articleId);
		return artServ.getArtsById(articleId);
	}
	public void setArts(List<Article> arts) {
		this.arts = arts;
	}
	
}
