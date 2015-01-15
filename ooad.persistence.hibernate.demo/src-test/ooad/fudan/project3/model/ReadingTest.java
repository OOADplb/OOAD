package ooad.fudan.project3.model;

import junit.framework.Assert;

import org.hibernate.Query;
import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;
import edu.fudan.ss.persistence.hibernate.demo.Teacher;

public class ReadingTest extends HibernateBaseTest {
	
	@Test
	public void findReadingByHQL(){
		PaperBook paperBook = PaperBook.create(null, "pb1", getPersistenceManager());
		Reading r = Reading.create(paperBook, getPersistenceManager());
		r.createNote("1", getPersistenceManager());
		String hql1 = "from Reading r where r.book.title='pb1'";
		String hql2 = "from Note n";
		Query query = getPersistenceManager().createQuery(hql1);
		Assert.assertEquals(1,query.list().size());
		assertEquals(1,getPersistenceManager().createQuery(hql2).list().size());
	}

}
