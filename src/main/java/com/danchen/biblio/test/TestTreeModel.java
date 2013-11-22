package com.danchen.biblio.test;

import org.zkoss.zul.AbstractTreeModel;

import com.danchen.biblio.hibernate.bean.Article;

public class TestTreeModel extends AbstractTreeModel<Article> {

	public TestTreeModel(Article root) {
		super(root);
		// TODO Auto-generated constructor stub
	}

	public Article getChild(Article arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getChildCount(Article arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isLeaf(Article arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
