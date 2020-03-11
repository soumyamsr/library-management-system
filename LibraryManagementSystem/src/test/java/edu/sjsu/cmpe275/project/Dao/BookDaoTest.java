package edu.sjsu.cmpe275.project.Dao;

import edu.sjsu.cmpe275.project.dao.BookDao;
import edu.sjsu.cmpe275.project.dao.BookDaoImpl;
import edu.sjsu.cmpe275.project.dao.CheckoutDaoImpl;
import edu.sjsu.cmpe275.project.model.Book;
import edu.sjsu.cmpe275.project.model.Checkout;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class BookDaoTest {
    @Mock
    SessionFactory sessionFactory;

    @Mock
    Session session;

    @Mock
    Criteria criteria;

    @InjectMocks
    BookDaoImpl bookDaoImpl;

    Book book1;
    Book book2;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        book1 = new Book();
        book1.setId(234);
        book1.setTitle("My book 1");

        book2 = new Book();
        book2.setId(567);
        book2.setTitle("My book 2");

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createCriteria((Class) anyObject())).thenReturn(criteria);

    }

    @Test
    public void testsave(){
        Book book = new Book();
        bookDaoImpl.save(book);
        verify(session,times(1)).persist(book);
    }

/*    @Test
    public void testFindByTitle(){
        List<Book> bookList = new ArrayList<>();
        List<Book> result = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        when(criteria.list()).thenReturn(bookList);
        result = bookDaoImpl.findByTitle(book2.getTitle());
        Assert.assertEquals(1,result.size());
        Assert.assertEquals(book2.getId(),result.get(0).getId());

    }*/

    @Test
    public void testModify(){
        Book book = new Book();
        bookDaoImpl.modify(book);
        verify(session,times(1)).update(book);
    }
}
