package ooad.fudan.project3.database;

import java.util.Collection;

import ooad.fudan.project3.control.*;
import ooad.fudan.project3.model.*;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class DBGetTest extends HibernateBaseTest{
	
	@Test
	public void Test(){
		Library l = Library.getInstance(getPersistenceManager());
		
		l.init(getPersistenceManager());
		
		LibraryAction la = new LibraryAction(l);
		la.addBook("plb1", "PaperBook", getPersistenceManager());
		assertEquals("plb1",la.getBookByTitle("plb1").getTitle());
		assertEquals(2,l.getBooks().size());
		Collection<Book> c = l.getBooks();
		Book cc = (Book) c.toArray()[0];
		assertEquals(1,cc.getReadings().size());
		
		PaperBook coc = (PaperBook) c.toArray()[0];
		assertEquals(1,coc.getRecords().size());
	}
}
