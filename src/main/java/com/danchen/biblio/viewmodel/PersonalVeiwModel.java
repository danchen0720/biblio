package com.danchen.biblio.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.service.ArticleService;

public class PersonalVeiwModel {
	private List<Article> arts;
	private ArticleService artServ;
	private User user;
	
	public PersonalVeiwModel() {
		artServ = new ArticleService();
		user = (User) Executions.getCurrent().getSession().getAttribute("user");
		arts = artServ.getByUserId(user.getId());
	}
	@Command
	public void modify(@BindingParam("id")int id) {
		Map args = new HashMap();
		args.put("isModify", true);
		args.put("articleId", id);
		
		//show editor
		Window window = (Window)Executions.createComponents(
	                "/editor.zul", null,args);
	    window.doModal();
	}
	@Command
	public void delete(@BindingParam("id") final int id) {
		Messagebox.show("Question is pressed. Are you sure?", "Question",
			Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,  new EventListener<Event>() {
		       public void onEvent(Event event) throws Exception {
		           //if clicks ok to do something
		           if (((Integer) event.getData()).intValue() == Messagebox.OK) {
		        	   artServ.delete(id);
		        	   Executions.getCurrent().sendRedirect("");
		           }
		       }
		});
	}
	
	//getter, setter
	public List<Article> getArts() {
		return arts;
	}
	public void setArts(List<Article> arts) {
		this.arts = arts;
	}
}
