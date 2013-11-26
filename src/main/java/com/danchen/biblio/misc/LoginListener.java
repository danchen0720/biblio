package com.danchen.biblio.misc;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.URIInterceptor;

public class LoginListener implements URIInterceptor {

	public void request(String path) throws Exception {
		Execution exec = Executions.getCurrent();
		Session sess = exec.getSession();
	}

}
