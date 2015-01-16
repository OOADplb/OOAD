package ooad.fudan.project3.control;

import java.sql.Date;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;
import ooad.fudan.project3.model.*;

public class ReadBook {
	Book book;
	String[] commentAbstract;
	String[] noteContent;
	String[] uri;
	
	public ReadBook(Book b, String[] ca, String[] nc, String[] uri){
		this.book = b;
		this.commentAbstract = ca;
		this.noteContent = nc;
		this.uri = uri;
	}
	//Library library;
	
	public Reading read(IPersistenceManager pm){
		if(uri.length != commentAbstract.length){
			System.err.println("Comment abstracts and URIs are not matched!");
			return null;
		}
		
		//增加一条阅读记录
		Reading result = Reading.create(book, pm);
		//写评论
		if(commentAbstract != null){
			for(int i=0;i<commentAbstract.length;i++){
			book.createComment(commentAbstract[i], uri[i], pm);
			pm.save(book);
			}
		}
		//写读书笔记
		if(noteContent != null){
			for(int i=0;i<noteContent.length;i++){
			result.createNote(noteContent[i], pm);
			}
		}
		//结束阅读
		result.setEnd(new Date(System.currentTimeMillis()));
		pm.save(result);
		return result;
	}
}
