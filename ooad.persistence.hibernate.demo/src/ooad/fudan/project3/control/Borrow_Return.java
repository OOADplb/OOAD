package ooad.fudan.project3.control;

import java.sql.Date;
import java.util.List;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;
import ooad.fudan.project3.database.LoadUtil;
import ooad.fudan.project3.model.*;

public class Borrow_Return {
	PaperBook paperbook;
	Friend friend;
	
	public Borrow_Return(PaperBook pb, Friend f){
		this.paperbook = pb;
		this.friend = f;
	}
	
	public BorrowRecord borrowBook(IPersistenceManager pm){
		
		//判断是否可借阅
		if(paperbook.isBorrow()){
			System.err.println("The book has been borrowed!");
			return null;
		}	
		//增加一条借阅记录
		paperbook.createRecord(friend, pm);
		//书本设为已借阅状态
		paperbook.setBorrow(true);
		paperbook.update(pm);
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
		record.update(pm);
		//书本设为未借阅状态
		paperbook.setBorrow(false);
		paperbook.update(pm);
		return record;
	}

	
	
	public BorrowRecord[] getHistoryByName(String name, IPersistenceManager pm){
		Friend f = LoadUtil.getFriendByName(name, pm);
		if(f == null){
			return null;
		}
		int id = f.getId();
		List<?> history = LoadUtil.getFromDB("BorrowRecord", "friend", id, pm);
		BorrowRecord result[] = new BorrowRecord[history.size()];
		
		for(int i=0;i<result.length;i++){
			result[i] = (BorrowRecord)history.get(i);
		}
		return result;
	}
}
