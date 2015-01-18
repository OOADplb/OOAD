package ooad.fudan.project3.control;

import ooad.fudan.project3.database.LoadUtil;
import ooad.fudan.project3.model.*;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class LibraryActionTest extends HibernateBaseTest{
	@Test
	public void Test(){
		Library l = Library.getInstance(getPersistenceManager());		
		l.init(getPersistenceManager());
		//Test paperbook
		LibraryAction la = new LibraryAction(l);
		la.addBook("pb", "Paperbook", getPersistenceManager());		
		Book test1 = la.getBookByTitle("pb");		
		assertEquals("pb",test1.getTitle());
		
		//Test EBook
		la.addBook("eb2", "EBook", getPersistenceManager());		
		Book test2 = la.getBookByTitle("eb2");
		assertEquals("eb2",test2.getTitle());
		
		//Test deleting book
		la.deleteBook("pb", getPersistenceManager());
		String hql = "from PaperBook pb";
		assertEquals(0,getPersistenceManager().createQuery(hql).list().size());
		
		la.deleteBook("eb2", getPersistenceManager());
		String hql2 = "from EBook eb2";
		assertEquals(0,getPersistenceManager().createQuery(hql2).list().size());
	
		//Test adding friend
		la.addFriend("liuli", getPersistenceManager());
		Friend f = LoadUtil.getFriendByName("liuli", getPersistenceManager());
		assertEquals("liuli",f.getName());
		
		//Test getting comment
		Book plb1 = la.addBook("plb1", "Paperbook", getPersistenceManager());	
		ReadBook rb = new ReadBook(plb1);
		Reading rd = rb.startReading(getPersistenceManager());
		rb.writeComment(getPersistenceManager(), "abstract", "uri");
		Comment[] c = la.getCommentByBook("plb1", getPersistenceManager());
		assertEquals("abstract",c[0].getAbstracts());
		assertEquals("uri",c[0].getURI());
		
		//Test getting note
		rb.writeNote(getPersistenceManager(), rd, "note");
		Note[] n = la.getNoteByBook("plb1", getPersistenceManager());
		assertEquals("note",n[0].getContent());
		
		//Test getting status
		la.addBook("plb2", "EBook", getPersistenceManager());
		assertEquals("EBook",la.getStatusByTitle("plb2"));
		
		
	}
}
