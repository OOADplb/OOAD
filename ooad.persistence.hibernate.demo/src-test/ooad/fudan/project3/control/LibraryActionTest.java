package ooad.fudan.project3.control;

import ooad.fudan.project3.model.*;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class LibraryActionTest extends HibernateBaseTest{
	@Test
	public void Test(){
		Library l = Library.getInstance(getPersistenceManager());		
		
		//Test paperbook
		PaperBook pb = PaperBook.create(null, "pb", getPersistenceManager());		
		LibraryAction la = new LibraryAction(l);
		la.addBook(pb, getPersistenceManager());		
		Book test1 = la.getBookByTitle("pb");		
		assertEquals(2,test1.getTitle().length());
		
		//Test EBook
		EBook eb = EBook.create(null,"eb2",getPersistenceManager());
		la.addBook(eb, getPersistenceManager());
		Book test2 = la.getBookByTitle("eb2");
		assertEquals(3,test2.getTitle().length());
	}
}
