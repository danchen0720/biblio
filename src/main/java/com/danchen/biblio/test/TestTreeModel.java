package com.danchen.biblio.test;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zul.AbstractTreeModel;

import com.danchen.biblio.hibernate.bean.Article;

public class TestTreeModel extends AbstractTreeModel<Object> {
	final Logger logger = LoggerFactory.getLogger(TestTreeModel.class);
	private List<Article> _tree = null;
	private int _treeSize;
	private Article _root;
	
	public TestTreeModel(List<Article> tree) {
		super(tree.get(0));
		
		_root = tree.get(0);
		_tree = tree;
		_treeSize = _tree.size();
	}

	public Article getChild(Object parent, int index) {
		if(_root.equals(parent))
			return _tree.get(index+1);
		else
			return (Article) ((Article)parent).getChildren().toArray()[index];
	}

	public int getChildCount(Object parent) {
		if(_root.equals(parent))
			return _tree.size()-1;
		else
			return ((Article)parent).getChildren().size();
	}

	public boolean isLeaf(Object node) {
		return ((Article) node).getChildren().size() == 0 ? true : false;
	}

}
