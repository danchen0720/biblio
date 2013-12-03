package com.danchen.biblio.viewmodel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.service.ArticleService;

public class ArticleViewModel {
	private static final Logger log = LoggerFactory.getLogger(ArticleViewModel.class);
	private List<Article> arts;
	private ArticleService artServ = new ArticleService();
	@Command
	public void reply(@BindingParam("btn")Button btn, @BindingParam("post")int id) {
		Include inner = new Include();
		inner.setSrc("/simpleedit.zul");
		inner.setDynamicProperty("id", id);
		btn.setVisible(false);
		btn.getParent().appendChild(inner);
		log.debug("Clicked reply button and set id:"+id+" for simpleedit");
	}

	public List<Article> getArts() {
		Object temp = Executions.getCurrent().getAttribute("articleId");
		if (temp != null) {
			String articleId = temp+"";
			boolean onTreeView = (Boolean) Executions.getCurrent().getAttribute("onTreeView");
			log.debug("get articleId:"+articleId+" and onTreeView:"+onTreeView+" to get Article");
			return artServ.getArtsById(articleId,onTreeView); 
		}
		return null;
	}
	public void setArts(List<Article> arts) {
		this.arts = arts;
	}	
}
