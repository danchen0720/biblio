package com.danchen.biblio.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.hibernate.dao.ArticleDAO;
import com.danchen.biblio.hibernate.dao.UserDAO;

public class ArticleService {
	private ArticleDAO artDao;
	private UserDAO userDao;
	
	public void updateModify(Date time, String content, Tag tag,int id) {
		Article bean = artDao.findOne(id);
		if(bean != null){
			artDao.update(time, content, tag, id);
		}
	}
	public void delete(int id) {
		Article bean = artDao.findOne(id);
		if (bean.getParent() != 0)//article is post
			artDao.update(2, id);
		else {
			List<Article> arts = new ArrayList();
			arts.add(bean);
			if(bean.getPosts().size() > 0)
				arts.addAll(bean.getPosts());
			artDao.update(2, arts);
		}
	}
	
	public void insertTopic(User user,Date time,String content,String title,Tag tag) {
		Set<Tag> tags = new HashSet();
		tags.add(tag);
		artDao.insert(new Article(user,time,0,0,0,content,title,tags));
	}
	public void insertPost(User user,Date time,int parent, int topic,String content,String title,Set<Tag> tags) {
		artDao.insert(new Article(user,time,parent,topic,1,content,title,tags));
	}
	
	public ArticleService() {
		artDao = new ArticleDAO();
		userDao = new UserDAO();
	}
	
	public List<Article> getByUserId(int id){
		if (id >= 0){
			User user = userDao.findOneBy(id);
			if(user != null)
				return artDao.getArtByUser(user);
		}
		return new ArrayList();
	}
	
	public List<Article> getToptics(){
		return artDao.findMainArts(10);
	}
	
	public List<Article> getPosts(){
		return artDao.findPosts(10);
	}
	
	public Article getTopicByPost(String articleId) {
		if (articleId != null) {
			int id = Integer.parseInt(articleId);
			Article tempArt = artDao.findOne(id);
			if (tempArt != null) {
				for (; tempArt.getParent() > 0 ; ) {
					tempArt = artDao.findOne(tempArt.getParent());
				}
			}
			return tempArt;
		}
		return null;
	}
	
	public List<Article> getArtsById(String articleId,boolean onTreeView) {
		if (articleId != null) {
			int id = Integer.parseInt(articleId);
			if (id >= 0) {
				List<Article> temp = new ArrayList();
				if (onTreeView) {
					temp.add(artDao.findOne(id));
					return temp;
				} else {
					return artDao.getArtFamily(id);
				}
			}
		}
		return new ArrayList();
	}
	
	public List<Article> getPersonal(String userName) {
		return artDao.getArtByUser(userDao.findOneBy(userName), 10);
	}
	
	public Article getArtById(int postId) {
		if(postId >= 1){
			//article id 0 is root
				return artDao.findOne(postId);
		}
		return null;
	}
}
