package com.danchen.biblio.viewmodel;

import java.io.IOException;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.service.ArticleService;

public class IndexViewModel {
	private ArticleService artServ;
	private List<Article> _topics;
	private List<Article> _posts;
	private List<Article> _personal;
	
	public IndexViewModel() {
		artServ = new ArticleService();
		_topics = artServ.getToptics();
		_posts = artServ.getPosts();
		//username
		String username = "Davidxxx";
		_personal = artServ.getPersonal(username);
	}
	
	@Command
	public void clickArtTitle(@BindingParam("articleId")String articleId) {
		if (articleId != null) {
			Executions.getCurrent().getSession().setAttribute("articleId", artServ.getTopicByPost(articleId).getId());
			Executions.sendRedirect("/forum.zul");
		}
	}
	
	//  getter setter
	public List<Article> getTopics() {
		return _topics;
	}
	public void setTopics(List<Article> topics) {
		_topics = topics;
	}

	public List<Article> getPosts() {
		return _posts;
	}
	public void setPost(List<Article> posts) {
		_posts = posts;
	}

	public List<Article> getPersonal() {
		return _personal;
	}
	public void setPersonal(List<Article> personal) {
		_personal = personal;
	}
}
