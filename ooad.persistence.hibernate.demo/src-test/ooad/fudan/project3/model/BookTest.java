package ooad.fudan.project3.model;

import java.util.List;

import ooad.fudan.project3.database.LoadUtil;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class BookTest extends HibernateBaseTest {

	@Test
	public void bookAddingTest() {
		Library library = Library.getInstance(getPersistenceManager());
		PaperBook paperBook = PaperBook.create(library, "pb1", getPersistenceManager());
		EBook eBook = EBook.create(library,"eb1", getPersistenceManager());
		assertObjectPersisted(library);
		assertObjectPersisted(paperBook);
		assertObjectPersisted(eBook);
		
		String hql1 = "from PaperBook pb";
		String hql2 = "from EBook eb";
		String hql3 = "from Book b";
		
		assertEquals(1,getPersistenceManager().createQuery(hql1).list().size());
		assertEquals(1,getPersistenceManager().createQuery(hql2).list().size());
		assertEquals(2,getPersistenceManager().createQuery(hql3).list().size());
	}

	@Test
	public void bookLoadTest() {
		Library library = Library.getInstance(getPersistenceManager());
		PaperBook paperBook = PaperBook.create(library, "pb1", getPersistenceManager());
		EBook eBook = EBook.create(library,"eb1", getPersistenceManager());
		assertObjectPersisted(library);
		assertObjectPersisted(paperBook);
		assertObjectPersisted(eBook);
		
		String hql1 = "from PaperBook pb where pb.library.id = "+ library.getId();
		
		assertEquals(1,getPersistenceManager().createQuery(hql1).list().size());
		
//		List<PaperBook> lp = (List<PaperBook>) LoadUtil.getFromDB("PaperBook", "library", 
//				library.getId(), getPersistenceManager());
		List<PaperBook> lp = (List<PaperBook>) (getPersistenceManager().createQuery(hql1).list());
		assertEquals(lp.get(0).getTitle(),"pb1");
	}
	
	@Test
	public void bookUpdateTest() {
		PaperBook paperBook = PaperBook.create(null, "pb1", getPersistenceManager());
		assertObjectPersisted(paperBook);
		paperBook.setBorrow(true);
		paperBook.update(getPersistenceManager());
		
		String hql1 = "from PaperBook pb where pb.borrow = true";
		
		assertEquals(1,getPersistenceManager().createQuery(hql1).list().size());
	}
}
