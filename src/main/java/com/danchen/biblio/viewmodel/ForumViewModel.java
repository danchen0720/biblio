package com.danchen.biblio.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;

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
	
	@Wire("#viewArea")
	private Div viewArea;
	
	@Wire("#veiwInner")
	private Include veiwInner;
	private Include articleInner;

	@AfterCompose(superclass=true)
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
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
		if(articleId !=null){
			if (articleInner == null) {
				articleInner = new Include();
				articleInner.setSrc("/article.zul");
				articleInner.setDynamicProperty("articleId",articleId);
				articleInner.setDynamicProperty("onTreeView",_onTreeView);
				viewArea.insertBefore(articleInner, viewArea);
			}else{
				articleInner.setDynamicProperty("articleId", articleId);
				articleInner.setDynamicProperty("onTreeView",_onTreeView);
				viewArea.insertBefore(articleInner, viewArea);
				articleInner.invalidate();
			}
		}
	}
	
	@Command
	public void select(@BindingParam("tagId")String tagId) {
		int id = Integer.parseInt(tagId);
		
		if(_selectedTagId != id){
			_selectedTagId = id;
			articleClose();
			//refresh include
			veiwInner.invalidate();
		}
	}
	
	//constructor
	public ForumViewModel() {
		_tags = tagServ.getAll();
	}
	
	// getter and setter
	public List<Tag> getTags() {
		return _tags;
	}
	public void setTags(List<Tag> tags) {
		_tags = tags;
	}

	public TreeModel getTm() {
		return new TagsTreeModel(tagServ.getArticleTreeByTag(_selectedTagId));
	}

	public List<Article> getArts() {
		return tagServ.getArtsByTag(_selectedTagId);
	}
	
}
