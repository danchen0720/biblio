package com.danchen.biblio.viewmodel;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Selectbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.service.ArticleService;
import com.danchen.biblio.service.TagService;

public class EditorViewModel {
	private static final Logger log = LoggerFactory.getLogger(EditorViewModel.class);
	private ArticleService artServ;
	private TagService tagServ;
	private boolean isModify;
	private boolean modifyProcessing;
	private int articleId;
	@Wire
	private Window editorWindow;
	@Wire
	private Textbox titleText;
	@Wire
	private Selectbox tagSelect;
	private Include processingInner;
	private String header;
	private List<Tag> tags;
	private Article bean;
	private int tagSelectedIndex;

	//article bean
	private User user;
	private Date time;
	private String content;
	private String title;
	private Tag tag;
	
	public EditorViewModel() {
		artServ = new ArticleService();
		tagServ = new TagService();
		tags = tagServ.getAll();
		user = (User) Executions.getCurrent().getSession().getAttribute("user");
		processingInner = (Include) Path.getComponent("//forumZul/processingInner");
		
		if (Executions.getCurrent().getArg().get("isModify") != null ) {
			isModify = true;
			articleId = (Integer) Executions.getCurrent().getArg().get("articleId");
			bean = artServ.getArtById(articleId);
			log.debug("get article:"+bean+" on modify type");
		} else if (Executions.getCurrent().getArg().get("modifyProcessing") != null) {
			modifyProcessing = true;
			articleId = (Integer) Executions.getCurrent().getArg().get("articleId");
			bean = artServ.getUnprocessArtById(articleId);
			log.debug("get article:"+bean+" on modifyProcessing type");
		}
		
		if (isModify || modifyProcessing) {
			if (bean.getTags().size() > 0) {
				tagSelectedIndex = tags.indexOf(bean.getTags().iterator().next());
				log.debug("tagSelectedIndex: "+tagSelectedIndex);
			}
			title = bean.getTitle();
			content = bean.getContent();
		}		
	}
	
	@AfterCompose(superclass=true)
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
 
        if (isModify) {
        	titleText.setDisabled(true);
        }
    }
	
	@Command
	public void post() {
		if (isModify) {
			if (content != null && content.length() > 0) {
				artServ.updateModify(content, tags.get(tagSelectedIndex), bean.getId());
				log.debug("EditorViewModel update Article by id:"+tagSelectedIndex+" on modify type");
				Path.getComponent("//personalZul/personalContent").invalidate();
				editorWindow.detach();
			}
		} else {
			//add new topic
			time = new Date();
			if (title != null && title.length() > 0) {
				if (content != null && content.length() > 0) {
					if (modifyProcessing) {
						artServ.updateModify(content, title, tags.get(tagSelectedIndex), bean.getId());
						log.debug("EditorViewModel update Article by id:"+tagSelectedIndex+" on modifyProcessing type");
					} else {
						artServ.insertTopic(user, time, content, title, tags.get(tagSelectedIndex));
						log.debug("EditorViewModel update Article by id:"+tagSelectedIndex+" on add new topic type");
					}
					processingInner.invalidate();
					editorWindow.detach();
				}
			}
		}
	}
	
	//window
	@Command
	public void cancel() {
		if (modifyProcessing) {
			artServ.removeUnProcess(bean.getId());
			processingInner.invalidate();
		}
		editorWindow.detach();
	}
	
	//getter & setter
	public String getHeader() {
		return header;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	public List<Tag> getTags() {
		return tags;
	}

	public int getTagSelectedIndex() {
		return tagSelectedIndex;
	}
	public void setTagSelectedIndex(int tagSelectedIndex) {
		this.tagSelectedIndex = tagSelectedIndex;
	}

}
