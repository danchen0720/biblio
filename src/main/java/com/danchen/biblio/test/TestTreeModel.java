package com.danchen.biblio.test;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zul.AbstractTreeModel;

import com.danchen.biblio.hibernate.bean.Article;

public class TestTreeModel extends AbstractTreeModel<Object> {
	final Logger logger = LoggerFactory.getLogger(TestTreeModel.class);
	private ArrayList<Article> _tree = null;

	public TestTreeModel(ArrayList<Article> tree) {
		super(tree.get(0));
		_tree = tree;
	}

	public Article getChild(Object parent, int index) {
		if(((Article) parent).getId() == 0 && index == 0)
			return _tree.get(index+1);
			
		return _tree.get(index);
//		Object[] children = ((Article) parent).getChildren().toArray();
//		return (Article) children[index];
	}

	public int getChildCount(Object parent) {
		return ((Article) parent).getChildren().size();
	}

	public boolean isLeaf(Object node) {
		return ((Article) node).getChildren().size() == 0 ? true : false;
	}

}
