package ooad.fudan.project3.model;

import java.sql.Date;

import junit.framework.Assert;

import org.hibernate.Query;
import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class ReadingTest extends HibernateBaseTest {

	@Test
	public void newReadingAndNoteTest(){
		PaperBook paperBook = PaperBook.create(null, "pb1", getPersistenceManager());
		Reading r = Reading.create(paperBook, getPersistenceManager());
		Note n = r.createNote("1", getPersistenceManager());
		
		String hql1 = "from Reading r where r.book.title='pb1'";
		String hql2 = "from Note n";
		
		assertEquals(1,getPersistenceManager().createQuery(hql1).list().size());
		assertEquals(1,getPersistenceManager().createQuery(hql2).list().size());
	}
	
	@Test
	public void updateReadingTest(){
		PaperBook paperBook = PaperBook.create(null, "pb1", getPersistenceManager());
		Reading r = Reading.create(paperBook, getPersistenceManager());
		r.createNote("1", getPersistenceManager());
		String hql1 = "from Reading r where r.book.title='pb1'";
		Query query = getPersistenceManager().createQuery(hql1);
		Assert.assertEquals(1,query.list().size());
		
		r.setEnd(new Date(System.currentTimeMillis()));
		r.update(getPersistenceManager());
		Assert.assertEquals(1,query.list().size());
	}

}
