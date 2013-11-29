package com.danchen.biblio.viewmodel;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Window;

public class EditorViewModel {
	@Wire
	private Window editorWindow;
	
	@Wire
	private Auxheader header;
	
	@AfterCompose(superclass=true)
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
    }
	
	@Command
	public void cancel(){
		editorWindow.detach();
	}
}
