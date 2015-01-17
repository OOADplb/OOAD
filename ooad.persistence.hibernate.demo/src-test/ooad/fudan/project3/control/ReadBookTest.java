package ooad.fudan.project3.control;

import ooad.fudan.project3.model.*;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class ReadBookTest extends HibernateBaseTest{
	
	@Test
	public void Test(){
		PaperBook pb = PaperBook.create(null, "pb", getPersistenceManager());
		assertObjectPersisted(pb);
		EBook eb = EBook.create(null, "eb2", getPersistenceManager());
		
		String commentAbstract = "Comment";
		String noteContent = "noteContent";
		String uri = "testURI";
		
		
	//Test reading paperbook
		String hql1 = "from Reading rd";
		
		ReadBook rb1 = new ReadBook(pb);
		Reading rd = rb1.startReading(getPersistenceManager());
		Comment c1 = rb1.writeComment(getPersistenceManager(), commentAbstract, uri);
		Note n1 = rb1.writeNote(getPersistenceManager(), rd, noteContent);
		rb1.endReading(getPersistenceManager(), rd);
		//�������ۼ���ɹ�
		assertEquals("Comment",c1.getAbstracts());
		//���ԱʼǼ���ɹ�
		assertEquals("noteContent",n1.getContent());
		//����URI��¼����ɹ�
		assertEquals("testURI",c1.getURI());
		//�����Ķ���¼�Ѽ���
		assertEquals(1,getPersistenceManager().createQuery(hql1).list().size());
	
	//Test Reading EBook
		ReadBook rb2 = new ReadBook(eb);	
		Reading rd2 = rb2.startReading(getPersistenceManager());
		
		String hql2 = "from Reading rd2 where rd2.end is not null";
	
		Comment c2 = rb2.writeComment(getPersistenceManager(), commentAbstract, uri);
		Note n2 = rb2.writeNote(getPersistenceManager(), rd2, noteContent);
		rb2.endReading(getPersistenceManager(), rd2);
		//�������ۼ���ɹ�
		assertEquals("Comment",c2.getAbstracts());
		//����URI��¼����ɹ�
		assertEquals("testURI",c2.getURI());
		//���ԱʼǼ���ɹ�
		assertEquals("noteContent",n2.getContent());
		//�����Ķ���¼�Ѽ���
		assertEquals(2,getPersistenceManager().createQuery(hql2).list().size());		
	}
}
