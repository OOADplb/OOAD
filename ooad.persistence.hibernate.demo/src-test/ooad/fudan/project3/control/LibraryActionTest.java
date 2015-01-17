package ooad.fudan.project3.control;

import ooad.fudan.project3.model.*;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class LibraryActionTest extends HibernateBaseTest{
	@Test
	public void Test(){
		Library l = Library.getInstance(getPersistenceManager());		
		
		PaperBook pb = PaperBook.create(null, "pb", getPersistenceManager());
		EBook eb = EBook.create(null,"eb2",getPersistenceManager());
		
		LibraryAction la = new LibraryAction(l);
		
		la.addBook(pb, getPersistenceManager());
		la.addBook(eb, getPersistenceManager());
		
		Book test1 = la.getBookByTitle("pb");
		Book test2 = la.getBookByTitle("eb2");
		
		assertEquals(2,test1.getTitle().length());
		assertEquals(3,test2.getTitle().length());
	}
}
