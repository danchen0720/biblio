package com.danchen.biblio.viewmodel;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.service.ArticleService;

public class ArticleViewModel {
	private List<Article> arts;
	private ArticleService artServ = new ArticleService();
	@Command
	public void reply(@BindingParam("btn")Button btn, @BindingParam("post")int id) {
		Include inner = new Include();
		inner.setSrc("/simpleedit.zul");
		inner.setDynamicProperty("id", id);
		btn.setVisible(false);
		btn.getParent().appendChild(inner);
	}

	public List<Article> getArts() {
		Object temp = Executions.getCurrent().getAttribute("articleId");
		if (temp != null) {
			String articleId = temp+"";
			boolean onTreeView = (Boolean) Executions.getCurrent().getAttribute("onTreeView");
			return artServ.getArtsById(articleId,onTreeView); 
		}
		return null;
	}
	public void setArts(List<Article> arts) {
		this.arts = arts;
	}	
}
