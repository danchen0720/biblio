package com.danchen.biblio.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

public class PersonalVeiwModel {
	@Command
	public void modify() {

		Window window = (Window)Executions.createComponents(
	                "/editor.zul", null, null);
	    window.doModal();
		
	}
}
