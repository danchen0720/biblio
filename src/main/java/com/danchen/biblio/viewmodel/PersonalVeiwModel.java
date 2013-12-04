package com.danchen.biblio.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.service.ArticleService;

public class PersonalVeiwModel {
	private static final Logger log = LoggerFactory.getLogger(PersonalVeiwModel.class);
	private List<Article> arts;
	private ArticleService artServ;
	private User user;
	
	public PersonalVeiwModel() {
		artServ = new ArticleService();
		user = (User) Executions.getCurrent().getSession().getAttribute("user");
		log.debug("PersonalVeiwModel get user:"+user);
		arts = artServ.getByUserId(user.getId());
	}
	
	@Command
	public void modify(@BindingParam("id")int id) {
		Map args = new HashMap(2);
		args.put("isModify", true);
		args.put("articleId", id);
		
		//show editor
		Window window = (Window)Executions.createComponents(
	                "/editor.zul", null,args);
	    window.doModal();
	}
	
	@Command
	public void delete(@BindingParam("id") final int id) {
		Messagebox.show("Post will be deleted. Are you sure?", "Question",
			Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,  new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (((Integer) event.getData()).intValue() == Messagebox.OK){
						//remove article on cache
						artServ.delete(id);
						setArts(artServ.getByUserId(user.getId()));
						log.debug("deleted article id:"+id);
						//send refresh Event and update view
						EventQueues.lookup("artsUpdate", EventQueues.APPLICATION, false).publish(new Event("refresh", null));
						BindUtils.postNotifyChange(null, null, PersonalVeiwModel.this, "arts");
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
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
