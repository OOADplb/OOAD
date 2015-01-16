package ooad.fudan.project3.control;

import ooad.fudan.project3.model.*;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class ReadBookTest extends HibernateBaseTest{
	
	@Test
	public void Test(){
		PaperBook pb = PaperBook.create(null, "pb", getPersistenceManager());
		assertObjectPersisted(pb);
		String[] ca = {"testComment"};
		String[] nc = {"noteContentTest"};
		String[] uri = {"testURI"};
		
		String hql1 = "from PaperBook pb";
		String hql2 = "from Reading rd";
		
		ReadBook rb = new ReadBook(pb,ca,nc,uri);
		Reading rd = rb.read(getPersistenceManager());
		assertObjectPersisted(rd);
		//�������ۼ���ɹ�
		assertEquals(11,((Comment)((PaperBook)(getPersistenceManager().createQuery(hql1).list().get(0))).getComments().toArray()[0]).getAbstracts().length());
		//���ԱʼǼ���ɹ�
		assertEquals(15,((Note)((Reading)(getPersistenceManager().createQuery(hql2).list().get(0))).getNotes().toArray()[0]).getContent().length());
		//����uri��¼����ɹ�
		assertEquals(7,((Comment)((PaperBook)(getPersistenceManager().createQuery(hql1).list().get(0))).getComments().toArray()[0]).getURI().length());
		//�����Ķ���¼�Ѽ���
		assertEquals(1,getPersistenceManager().createQuery(hql2).list().size());
	}
}
