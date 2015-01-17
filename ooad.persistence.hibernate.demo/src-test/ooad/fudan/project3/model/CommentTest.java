package ooad.fudan.project3.model;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class CommentTest extends HibernateBaseTest {

	@Test
	public void commentAddingTest() {
		PaperBook paperBook = PaperBook.create(null, "pb1", getPersistenceManager());
		Comment c = Comment.create(paperBook, "c1", "127.0.0.1", getPersistenceManager());
		assertObjectPersisted(c);
		
		String hql1 = "from Comment c1 where c1.book.title='pb1' and c1.URI='127.0.0.1'";
		
		assertEquals(1,getPersistenceManager().createQuery(hql1).list().size());
	}

}
