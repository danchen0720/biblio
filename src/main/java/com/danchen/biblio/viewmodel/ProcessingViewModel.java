package com.danchen.biblio.viewmodel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.service.ArticleService;

public class ProcessingViewModel {
	private User user;
	private ArticleService artServ = new ArticleService();
	private static ExecutorService service = Executors.newFixedThreadPool(10);
	private static Map futureMap = new HashMap();
	private Include processingIner;
	private List<Article> arts;

	public ProcessingViewModel() {
		user = (User) Executions.getCurrent().getSession().getAttribute("user");
		arts = artServ.processingArt(user);
		processingIner = (Include) Path.getComponent("/processingInner");
		
		if (processingIner != null)
			if (arts.size() <= 0)
				processingIner.setVisible(false);
			else {
				
				processingIner.setVisible(true);
				
				//processing event
			    Iterator itr = arts.iterator();
		        for (int i = 0; itr.hasNext(); i++) {
		        	Article art = (Article)itr.next();
		        	EventQueue<Event> que = EventQueues.lookup("update_"+art.getId(), EventQueues.APPLICATION, true);
    				que.subscribe(new EventListener<Event>() {
    			        public void onEvent(Event evt) {
    			        	Map data = (Map) evt.getData();
    			        	EventQueue que = (EventQueue) data.get("eventQueue");
    						Article art = (Article) data.get("article");
    						
    						ArticleService artServ = new ArticleService();
    						artServ.updateState(art);
    						
    						//server push refresh the web
    						EventQueues.lookup("artsUpdate", EventQueues.APPLICATION, false).publish(new Event("refresh", null));;
    						
    						EventQueues.remove("update_"+art.getId(), EventQueues.APPLICATION);
    			        }
    				}); 
		        	futureMap.put(art.getId(),service.submit(new Task(que,art)));
		        } 
			}
	}
	@Command
	public void cancel(@BindingParam("id") int id){
		EventQueues.remove("update_"+id, EventQueues.APPLICATION);
		ArticleService artServ = new ArticleService();
		
		//call editor
		Map data = new HashMap();
		data.put("modifyProcessing", true);
		data.put("articleId", id);
		Window window = (Window)Executions.createComponents(
	                "/editor.zul", null, data);
		window.doModal();
		
	}
	
	//getter,setter
	public List<Article> getArts() {
		return arts;
	}
	public void setArts(List<Article> arts) {
		this.arts = arts;
	}
	
}

class Task implements Runnable {
	private EventQueue<Event> _qe;
	private Article _art;

	public Task(EventQueue<Event> eventQueue, Article article) {
		_qe = eventQueue;
		_art = article;
	}
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			Thread.currentThread().sleep(10000);
			Map data = new HashMap();
			data.put("eventQueue", _qe);
			data.put("article", _art);
			_qe.publish(new Event("update", null,data));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	public Article get_art() {
		return _art;
	}
	public void set_art(Article _art) {
		this._art = _art;
	}
}
