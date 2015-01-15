package ooad.fudan.project3.model;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class CommentTest extends HibernateBaseTest {

	@Test
	public void test() {
		PaperBook paperBook = PaperBook.create(null, "pb1", getPersistenceManager());
		Comment c = Comment.create(paperBook, "c1", getPersistenceManager());
		c.setURI("127.0.0.1");
		
		String hql1 = "from Comment c1 where c1.book.title='pb1' and c1.URI='127.0.0.1'";
		
		assertEquals(1,getPersistenceManager().createQuery(hql1).list().size());
	}

}
