package ooad.fudan.project3.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class BorrowRecord extends BaseModelObject {
	
	public static BorrowRecord create(PaperBook paperBook, Friend friend, IPersistenceManager pm) {
		BorrowRecord result = new BorrowRecord();
		result.setPaperBook(paperBook);
		result.setFriend(friend);
		result.setStart(new Date(System.currentTimeMillis()));
		pm.save(result);
		return result;
	}
	
	@ManyToOne
	private PaperBook paperBook;
	
	@ManyToOne
	private Friend friend;
	
	private Date start;
	
	private Date end;

	public PaperBook getPaperBook() {
		return paperBook;
	}

	public void setPaperBook(PaperBook paperBook) {
		this.paperBook = paperBook;
	}

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void update(IPersistenceManager pm) {
		pm.save(this);
		
	}
	
}
