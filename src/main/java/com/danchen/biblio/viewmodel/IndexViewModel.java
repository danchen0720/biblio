package com.danchen.biblio.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.service.ArticleService;

public class IndexViewModel {
	private ArticleService artServ;
	private List<Article> topics;
	private List<Article> posts;
	private List<Article> personal;
	
	public IndexViewModel() {
		artServ = new ArticleService();
		topics = artServ.getToptics();
		posts = artServ.getPosts();
		User user = (User) Executions.getCurrent().getSession().getAttribute("user");
		personal = artServ.getPersonal(user.getUsername());
	}
	
	@Command
	public void clickArtTitle(@BindingParam("articleId")String articleId) {
		//save data in session, and open the topic on forum
		if (articleId != null) {
			Execution exec = Executions.getCurrent();
			Article clickedArt = artServ.getArtById(Integer.parseInt(articleId));
			exec.getSession().setAttribute("articleId",
					clickedArt.getTopic() == 0? articleId:clickedArt.getTopic());
			exec.sendRedirect("/forum.zul");
		}
	}
	
	//  getter setter
	public List<Article> getTopics() {
		return topics;
	}
	public void setTopics(List<Article> topics) {
		this.topics = topics;
	}

	public List<Article> getPosts() {
		return posts;
	}
	public void setPost(List<Article> posts) {
		this.posts = posts;
	}

	public List<Article> getPersonal() {
		return personal;
	}
	public void setPersonal(List<Article> personal) {
		this.personal = personal;
	}
}
