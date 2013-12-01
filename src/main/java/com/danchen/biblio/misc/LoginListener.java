package com.danchen.biblio.misc;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.ExecutionInit;

public class LoginListener implements ExecutionInit  {

	public void init(Execution exec, Execution parent) throws Exception {
		if (exec != null)
			if (!exec.getDesktop().getRequestPath().equals("/login.zul")) {
				Session sess = exec.getSession();
				if (sess.getAttribute("user") == null) {
					exec.sendRedirect("/login.zul");
				}
			}
		
	}

}
