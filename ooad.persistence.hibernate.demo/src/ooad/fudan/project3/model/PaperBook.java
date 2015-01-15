package ooad.fudan.project3.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
@DiscriminatorValue("PAPER")
public class PaperBook extends Book {
	
	public static PaperBook create(Library library, String title, IPersistenceManager pm) {
		PaperBook result = new PaperBook();
		result.setLibrary(library);
		result.setTitle(title);
		pm.save(result);
		return result;
	}
	
	private boolean borrow;
	
	@OneToMany(mappedBy="paperBook", cascade = {CascadeType.ALL})
	Collection<BorrowRecord> records = new ArrayList<BorrowRecord>();
	
	public Collection<BorrowRecord> getRecords() {
		return records;
	}
	
	public void createRecord(Friend friend, IPersistenceManager pm){
		BorrowRecord record = BorrowRecord.create(this, friend, pm);
		records.add(record);
		friend.addRecord(record);
	}

	public BorrowRecord getLatestRecord(){
		return ((ArrayList<BorrowRecord>)records).get(records.size() - 1);
	}
	
	public boolean isBorrow() {
		return borrow;
	}

	public void setStatus(boolean borrow) {
		this.borrow = borrow;
	}
	
}
