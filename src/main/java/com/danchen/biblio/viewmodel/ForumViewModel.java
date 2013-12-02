package com.danchen.biblio.viewmodel;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
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
import com.danchen.biblio.service.ArticleService;
import com.danchen.biblio.service.TagService;
import com.danchen.biblio.tm.TagsTreeModel;

public class ForumViewModel {
	private TagService tagServ = new TagService();
	private ArticleService artServ = new ArticleService();
	private List<Tag> _tags;
	private List<Article> _arts;
	private boolean _onTreeView = false;
	private int _selectedTagId = -1;
	
	@Wire("#viewArea")//wire component by id
	private Div viewArea;
	
	@Wire("#veiwInner")
	private Include veiwInner;
	private Include articleInner;
	@Wire("#processingInner")
	private Include processingInner;

	@AfterCompose(superclass=true)
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        Object temp = Executions.getCurrent().getSession().getAttribute("articleId");
        if (temp != null) {
        	String articleId = temp+"";
    		if (articleId != null) {
    			Executions.getCurrent().getSession().removeAttribute("articleId");
    			clickArtTitle(articleId);
    		}
        }
        Executions.getCurrent().getSession().setAttribute("processingInner",processingInner);
    }
	
	@Command
	public void changeView(@BindingParam("onTreeView")boolean onTreeView) {
		if(onTreeView)
			veiwInner.setSrc("/tree.zul");
		else
			veiwInner.setSrc("/list.zul");
		
		_onTreeView = onTreeView;
		articleClose();
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
				articleInner.setDynamicProperty("articleId",articleId);
				articleInner.setDynamicProperty("onTreeView",_onTreeView);
				viewArea.insertBefore(articleInner, viewArea);
			} else{ 
				articleInner.setDynamicProperty("articleId", articleId);
				articleInner.setDynamicProperty("onTreeView",_onTreeView);
				viewArea.insertBefore(articleInner, viewArea);
				articleInner.invalidate();
			}
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
		if (_selectedTagId != id) {
			_selectedTagId = id;
			articleClose();
			//refresh include
			veiwInner.invalidate();
		}
	}
	
	@Command
	public void logout() {
		Execution exec = Executions.getCurrent();
		exec.getSession().removeAttribute("user");
		exec.sendRedirect("/login.zul");
	}
	
	//constructor
	public ForumViewModel() {
		_tags = tagServ.getAll();

        //check have new arts or not
        EventQueue<Event> que = EventQueues.lookup("artsUpdate", EventQueues.APPLICATION, true);
		que.subscribe(new EventListener<Event>() {
	        public void onEvent(Event evt) {
				Executions.getCurrent().sendRedirect("");
	        }
		}); 
	}
	
	// getter and setter
	public List<Tag> getTags() {
		return _tags;
	}
	public void setTags(List<Tag> tags) {
		_tags = tags;
	}

	public TreeModel getTm() {
		//dynamic change the item in tree
		return new TagsTreeModel(tagServ.getArtsByTag(_selectedTagId,true));
	}

	public List<Article> getArts() {
		return tagServ.getArtsByTag(_selectedTagId,false);
	}
	
}
