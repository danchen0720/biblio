package com.danchen.biblio.viewmodel;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.service.ArticleService;

public class SimpleEditViewMondel {
	private static final Logger log = LoggerFactory.getLogger(SimpleEditViewMondel.class);
	private Integer postId;
	private ArticleService artServ;
	private Article parentArt;
	private User user;
	private String content;
	
	public SimpleEditViewMondel(){
		Execution exec = Executions.getCurrent();
		artServ = new ArticleService();
		Object obj = exec.getAttribute("id");
		if (obj != null) {
			postId = (Integer) obj;
			user = (User) exec.getSession().getAttribute("user");
			parentArt = artServ.getArtById(postId);
			log.debug("User:"+user+" want reply Article:"+parentArt);
		}
	}
	
	@Command
	public void confirm() {
		if( artServ.getArtById(postId) != null ){
			if (content != null && content.length() > 0) {
				int topic = parentArt.getTopic();
				artServ.insertPost(user,new Date(),parentArt.getId(),topic != 0 ? topic : parentArt.getId(),
						content,"Re:"+parentArt.getTitle(),parentArt.getTags());
				Path.getComponent("/articleInner").invalidate();
				Path.getComponent("/veiwInner").invalidate();
			}
		} else {
			Messagebox.show("This topic has been deleted.", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
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
