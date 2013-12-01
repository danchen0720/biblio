package com.danchen.biblio.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.service.UserService;

public class LoginViewModel {
	private UserService userServ;
	private String userName;
	private String passWord;
	
	public LoginViewModel() {
		userServ = new UserService();
	}
	
	@Command
	public void login() {
		User user = userServ.getUser(userName, passWord);
		if (user != null) {
			Session session = Executions.getCurrent().getSession();
			session.setAttribute("user", user);
			Executions.getCurrent().sendRedirect("/index.zul");
		}
	}
	
	//getter and setter
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}
