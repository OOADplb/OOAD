package ooad.fudan.project3.control;

import java.sql.Date;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;
import ooad.fudan.project3.model.*;

public class Borrow_Return {
	PaperBook paperbook;
	Friend friend;
	
	public BorrowRecord borrowBook(IPersistenceManager pm){
		
		//判断是否可借阅
		if(paperbook.isBorrow()){
			System.err.println("The book has been borrowed!");
			return null;
		}	
		//增加一条借阅记录
		paperbook.createRecord(friend, pm);
		//书本设为已借阅状态
		paperbook.setStatus(true);
		pm.save(paperbook);
		return paperbook.getLatestRecord();
	}
	
	public BorrowRecord returnBook(IPersistenceManager pm){
		if(!paperbook.isBorrow()){
			System.err.println("The book is not borrowed yet!");
			return null;
		}
		//取出书本的最后一条借阅记录，加上结束时间
		BorrowRecord record = paperbook.getLatestRecord();
		record.setEnd(new Date(System.currentTimeMillis()));
		pm.save(record);
		//书本设为未借阅状态
		paperbook.setStatus(false);
		pm.save(paperbook);
		return record;
	}
}
