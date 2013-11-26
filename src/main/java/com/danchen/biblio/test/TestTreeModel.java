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
	private int _treeSize;
	
	public TestTreeModel(ArrayList<Article> tree) {
		super(tree.get(0));
		_tree = tree;
		_treeSize = _tree.size();
	}

	public Article getChild(Object parent, int index) {
		if(_tree.size() > 1){
			return _tree.remove(1);
		}else {
			//clear up the list
			
			Object[] children = ((Article) parent).getChildren().toArray();
			return (Article) children[index];
		}
	}

	public int getChildCount(Object parent) {
		//return doesn't have root size
		if(_tree.size() >= 1)
			return _treeSize-1;
			
		return ((Article) parent).getChildren().size();
	}

	public boolean isLeaf(Object node) {
		return ((Article) node).getChildren().size() == 0 ? true : false;
	}

}
