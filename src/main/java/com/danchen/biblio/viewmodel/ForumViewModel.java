package com.danchen.biblio.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Button;

import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.service.TagService;

public class ForumViewModel {
	private TagService tagServ = new TagService();
	private List<Tag> tags;
	private Button selectedItem;
	private int tagId;
	
	@Command("select")
	public void select(@BindingParam("btn")Button btn, @BindingParam("id")String id) {
		if(selectedItem != null)
			selectedItem.setDisabled(false);
		
		//update selectedBtn
		selectedItem = btn;
		selectedItem.setDisabled(true);
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
	
}
