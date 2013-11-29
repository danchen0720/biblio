package com.danchen.biblio.viewmodel;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;

public class SimpleEditViewMondel {
	private String postId;
	public SimpleEditViewMondel(){
		Object temp = Executions.getCurrent().getAttribute("id");
		if(temp != null)
			postId = temp+"";
	}
	
	@Command
	public void postComment() {

	}
	
	@Command
	public void cancel(@BindingParam("btn")Button btn) {
		Button replybtn = (Button) btn.getParent().getParent().getParent().getChildren().get(0);
		btn.getParent().getParent().removeChild(btn.getParent());
		replybtn.setVisible(true);
	}
}
