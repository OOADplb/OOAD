package ooad.fudan.project3.control;

import java.sql.Date;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;
import ooad.fudan.project3.model.*;

public class Borrow_Return {
	PaperBook paperbook;
	Friend friend;
	
	public BorrowRecord borrowBook(IPersistenceManager pm){
		
		//�ж��Ƿ�ɽ���
		if(paperbook.isBorrow()){
			System.err.println("The book has been borrowed!");
			return null;
		}	
		//����һ�����ļ�¼
		paperbook.createRecord(friend, pm);
		//�鱾��Ϊ�ѽ���״̬
		paperbook.setStatus(true);
		pm.save(paperbook);
		return paperbook.getLatestRecord();
	}
	
	public BorrowRecord returnBook(IPersistenceManager pm){
		if(!paperbook.isBorrow()){
			System.err.println("The book is not borrowed yet!");
			return null;
		}
		//ȡ���鱾�����һ�����ļ�¼�����Ͻ���ʱ��
		BorrowRecord record = paperbook.getLatestRecord();
		record.setEnd(new Date(System.currentTimeMillis()));
		pm.save(record);
		//�鱾��Ϊδ����״̬
		paperbook.setStatus(false);
		pm.save(paperbook);
		return record;
	}
}
