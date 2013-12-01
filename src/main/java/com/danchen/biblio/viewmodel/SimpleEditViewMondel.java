package com.danchen.biblio.viewmodel;

import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.service.ArticleService;

public class SimpleEditViewMondel {
	private Integer postId;
	private ArticleService artServ;
	private Article parentArt;
	private Include inner;
	//article bean
	private User user;
	private String content;
	
	public SimpleEditViewMondel(){
		artServ = new ArticleService();
		Object obj = Executions.getCurrent().getAttribute("id");
		if(obj != null){
			postId = (Integer) obj;
			user = (User) Executions.getCurrent().getSession().getAttribute("user");
			inner = (Include) Executions.getCurrent().getAttribute("inner");
			parentArt = artServ.getArtById(postId);
		}
	}
	
	@Command
	public void confirm() {
		if (content != null && content.length() > 0) {
			int topic = parentArt.getTopic();
			artServ.insertPost(user,new Date(),parentArt.getId(),topic != 0 ? topic : parentArt.getId(),
					content,"Re:"+parentArt.getTitle(),parentArt.getTags());
			Executions.getCurrent().sendRedirect("/forum.zul");
		}
	}
	
	@Command
	public void cancel(@BindingParam("btn")Button btn) {
		Button replybtn = (Button) btn.getParent().getParent().getParent().getChildren().get(0);
		btn.getParent().getParent().removeChild(btn.getParent());
		replybtn.setVisible(true);
	}

	//gtter and setter
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
