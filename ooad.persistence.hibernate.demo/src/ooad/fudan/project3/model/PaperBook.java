package ooad.fudan.project3.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import ooad.fudan.project3.database.LoadUtil;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
@DiscriminatorValue("PAPER")
public class PaperBook extends Book {
	
	public PaperBook(){}
	
	public static PaperBook create(Library library, String title, IPersistenceManager pm) {
		PaperBook result = new PaperBook();
		result.setLibrary(library);
		result.setTitle(title);
		result.setBorrow(false);
		pm.save(result);
		return result;		
	}
	
	private boolean borrow;
	
	@OneToMany(mappedBy="paperBook", cascade = {CascadeType.ALL})
	Collection<BorrowRecord> records = new ArrayList<BorrowRecord>();
	
	public Collection<BorrowRecord> getRecords() {
		return records;
	}
	
	public BorrowRecord createRecord(Friend friend, IPersistenceManager pm){
		BorrowRecord record = BorrowRecord.create(this, friend, pm);
		records.add(record);
		friend.addRecord(record);
		return record;
	}

	public BorrowRecord getLatestRecord(){
		return (BorrowRecord)records.toArray()[records.toArray().length - 1];
	}
	
	public boolean isBorrow() {
		return borrow;
	}

	public void setBorrow(boolean borrow) {
		this.borrow = borrow;
	}
	
	public void init(IPersistenceManager pm){
		super.init(pm);
		records = (Collection<BorrowRecord>) LoadUtil.getFromDB("BorrowRecord", "paperBook", this.getId(), pm);
	}
	
}
