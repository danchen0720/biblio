package com.danchen.biblio.viewmodel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Window;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.misc.OpenSessionInViewListener;
import com.danchen.biblio.service.ArticleService;
import com.danchen.biblio.service.TagService;
import com.danchen.biblio.tm.TagsTreeModel;

public class ForumViewModel {
	private static final Logger log = LoggerFactory.getLogger(OpenSessionInViewListener.class);
	private TagService tagServ = new TagService();
	private List<Tag> tags;
	private boolean onTreeView = false;
	private int selectedTagId = -1;
	@Wire("#viewArea")//wire component by id
	private Div viewArea;
	@Wire("#veiwInner")
	private Include veiwInner;
	private Include articleInner;
	
	//constructor
	public ForumViewModel() {
		tags = tagServ.getAll();

        //post new topic will refresh
        EventQueue<Event> que = EventQueues.lookup("artsUpdate", EventQueues.APPLICATION, true);
		que.subscribe(new EventListener<Event>() {
	        public void onEvent(Event evt) {
	        	veiwInner.invalidate();
	        	Path.getComponent("/processingInner").invalidate();
	        	articleClose();
	        }
		}); 
	}

	@AfterCompose(superclass=true)
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        //get id from index and open the topic 
        Session session = Executions.getCurrent().getSession();
        Object temp = session.getAttribute("articleId");
        if (temp != null) {
        	String articleId = (String) temp;
        	
			session.removeAttribute("articleId");
			clickArtTitle(articleId);
        }
    }

	@Command
	public void changeView(@BindingParam("onTreeView")boolean onTreeView) {
		if(onTreeView)
			veiwInner.setSrc("/tree.zul");
		else
			veiwInner.setSrc("/list.zul");
		
		this.onTreeView = onTreeView;
		articleClose();		
		//refresh the view
		veiwInner.invalidate();
	}
	private void articleClose(){
		if(articleInner != null){
			viewArea.removeChild(articleInner);
		}
	}
	
	@Command
	public void clickArtTitle(@BindingParam("articleId")String articleId) {
		if (articleId !=null) {
			if (articleInner == null) {
				articleInner = new Include();
				articleInner.setSrc("/article.zul");
				articleInner.setId("articleInner");
			} 
			articleInner.setDynamicProperty("articleId", articleId);
			log.debug("articleInner set articleId:"+articleId);
			articleInner.setDynamicProperty("onTreeView",onTreeView);
			log.debug("articleInner set onTreeView:"+onTreeView);
			viewArea.insertBefore(articleInner, viewArea);
			//How to show article base on include was builded or not
			if (articleInner == null)
				viewArea.insertBefore(articleInner, viewArea);
			else
				articleInner.invalidate();
		}
	}
	
	@Command
	public void addNewTopic() {
		Window window = (Window)Executions.createComponents(
	                "/editor.zul", null, null);
	    window.doModal();
	}
	
	@Command
	public void tagClick(@BindingParam("tagId")String tagId) {
		int id = Integer.parseInt(tagId);
		//change article counts by tag

		if (selectedTagId != id) {
			log.debug("set selectedTagId:"+tagId);
			articleClose();
			veiwInner.invalidate();
			selectedTagId = id;
		}
	}
	
	@Command
	public void logout() {
		Execution exec = Executions.getCurrent();
		exec.getSession().removeAttribute("user");
		exec.sendRedirect("/login.zul");
	}
	
	// getter and setter
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public TreeModel getTreeModel() {
		//dynamic change the item in tree
		return new TagsTreeModel(tagServ.getArtsByTag(selectedTagId,true));
	}

	public List<Article> getArtsList() {
		return tagServ.getArtsByTag(selectedTagId,false);
	}
	
}
