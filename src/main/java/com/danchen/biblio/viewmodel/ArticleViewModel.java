package com.danchen.biblio.viewmodel;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.service.ArticleService;

public class ArticleViewModel {
	private List<Article> arts;
	private ArticleService artServ = new ArticleService();

	public List<Article> getArts() {
		String articleId = (String) Executions.getCurrent().getAttribute("articleId");
		boolean onTreeView = (Boolean) Executions.getCurrent().getAttribute("onTreeView");

		return artServ.getArtsById(articleId,onTreeView);
	}
	public void setArts(List<Article> arts) {
		this.arts = arts;
	}
	
}
