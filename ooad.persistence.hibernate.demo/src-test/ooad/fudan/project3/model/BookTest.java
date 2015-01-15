package ooad.fudan.project3.model;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class BookTest extends HibernateBaseTest {

	@Test
	public void test() {
		Library library = Library.create(getPersistenceManager());
		PaperBook paperBook = PaperBook.create(library, "pb 1", getPersistenceManager());
		EBook eBook = EBook.create(library,"eb 1", getPersistenceManager());
		assertObjectPersisted(paperBook);
		assertObjectPersisted(eBook);
		
		String hql1 = "from PaperBook pb";
		String hql2 = "from EBook eb";
		String hql3 = "from Book b";
		
		assertEquals(1,getPersistenceManager().createQuery(hql1).list().size());
		assertEquals(1,getPersistenceManager().createQuery(hql2).list().size());
		assertEquals(2,getPersistenceManager().createQuery(hql3).list().size());
	}

}
