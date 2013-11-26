package com.danchen.biblio.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;

import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.service.TagService;

public class ForumViewModel {
	private TagService tagServ = new TagService();
	private List<Tag> tags;
	private Button selectedItem;
	private int tagId;
	private int selectedTagId;
	@Wire("#treeIn")
	private Include include;
	
	@AfterCompose(superclass=true)
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, false);
    }
	
	@Command("select")
	public void select(@BindingParam("btn")Button btn, @BindingParam("tagId")String tagId) {
		if(selectedItem != null)
			selectedItem.setDisabled(false);
		
		//update selectedBtn
		selectedItem = btn;
		selectedItem.setDisabled(true);
		selectedTagId = Integer.parseInt(tagId);
		include.invalidate();
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

	public int getSelectedTagId() {
		return selectedTagId;
	}
	public void setSelectedTagId(int selectedTagId) {
		this.selectedTagId = selectedTagId;
	}
	
}
