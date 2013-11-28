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
	private List<Tag> tags;
	private List<Article> arts;
	private int selectedTagId = -1;
	
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
		
		articleClose();
		
		veiwInner.invalidate();
	}
	private void articleClose(){
		if(articleInner != null){
			articleInner.setSrc("");
			articleInner.invalidate();
		}
	}
	@Command
	public void clickArtTitle(@BindingParam("articleId")String articleId) {
		if(articleId !=null){
			if (articleInner == null) {
				articleInner = new Include();
				articleInner.setId("articleInner");
				articleInner.setSrc("/article.zul");
				articleInner.setDynamicProperty("articleId",articleId);
				viewArea.insertBefore(articleInner, viewArea);
			}else{
				articleInner.setSrc("/article.zul");
				articleInner.setDynamicProperty("articleId", articleId);
				articleInner.invalidate();
			}
		}
	}
	
	@Command
	public void select(@BindingParam("tagId")String tagId) {
		int id = Integer.parseInt(tagId);
		
		if(selectedTagId != id){
			selectedTagId = id;
			//refresh include
			veiwInner.invalidate();
			articleClose();
		}
	}
	
	//constructor
	public ForumViewModel() {
		tags = tagServ.getAll();
	}
	
	// getter and setter
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public TreeModel getTm() {
		return new TagsTreeModel(tagServ.getArticleTreeByTag(selectedTagId));
	}

	public List<Article> getArts() {
		return tagServ.getArtsByTag(selectedTagId);
	}
	
}
