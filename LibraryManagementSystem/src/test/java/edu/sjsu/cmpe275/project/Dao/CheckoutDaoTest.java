package edu.sjsu.cmpe275.project.Dao;

import edu.sjsu.cmpe275.project.dao.CheckoutDaoImpl;
import edu.sjsu.cmpe275.project.model.Book;
import edu.sjsu.cmpe275.project.model.BookCopy;
import edu.sjsu.cmpe275.project.model.Checkout;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class CheckoutDaoTest {

    @Mock
    SessionFactory sessionFactory;

    @Mock
    Session session;

    @Mock
    Criteria criteria;

    @Mock
    BookCopy bookCopy;

    @InjectMocks
    CheckoutDaoImpl checkoutDaoImpl;

    Checkout checkout1;
    Checkout checkout2;
    Book book1;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        book1 = new Book();

        checkout1 = new Checkout();
        checkout1.setUserId(123);
        checkout1.setBookId(234);
        checkout1.setCheckoutDate(new Date());
        checkout1.setCopy(bookCopy);

        checkout2 = new Checkout();
        checkout2.setUserId(789);
        checkout2.setBookId(678);
        checkout2.setCheckoutDate(new Date());
        checkout2.setCopy(bookCopy);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createCriteria((Class) anyObject())).thenReturn(criteria);
        when(bookCopy.getBooks()).thenReturn(book1);
    }

    @Test
    public void testInsert(){
        Checkout entity = new Checkout();
        checkoutDaoImpl.insert(entity);
        verify(session,times(1)).save(entity);
    }

    @Test(expected = Exception.class)
    public void testInsertForException(){
        Checkout entity = new Checkout();
        doThrow(new Exception()).when(session).save(entity);
        checkoutDaoImpl.insert(entity);
    }

    @Test
    public void testRemove(){
        Checkout entity = new Checkout();
        checkoutDaoImpl.remove(entity);
        verify(session,times(1)).delete(entity);
    }

    @Test
    public void testFindByBookIdForNull() {
        List<Checkout> checkoutList = new ArrayList<>();
        when(criteria.list()).thenReturn(checkoutList);
        List<Checkout> chList = checkoutDaoImpl.findByBookId(234);
        Assert.assertEquals(0, chList.size());
    }

    @Test
    public void testFindByBookId() {
        List<Checkout> checkoutList = new ArrayList<>();
        checkoutList.add(checkout1);
        when(criteria.list()).thenReturn(checkoutList);
        List<Checkout> chList = checkoutDaoImpl.findByBookId(234);
        Assert.assertEquals(1, chList.size());
    }

    @Test
    public void testFindByUserIdForNull() {
        List<Checkout> checkoutList = new ArrayList<>();
        when(criteria.list()).thenReturn(checkoutList);
        List<Checkout> chList = checkoutDaoImpl.findByUserId(123);
        Assert.assertEquals(0, chList.size());
    }

    @Test
    public void testFindByUserId() {
        List<Checkout> checkoutList = new ArrayList<>();
        checkoutList.add(checkout1);
        when(criteria.list()).thenReturn(checkoutList);
        List<Checkout> chList = checkoutDaoImpl.findByUserId(123);
        Assert.assertEquals(1, chList.size());
    }

    @Test
    public void testModify() {
        Checkout entity = new Checkout();
        checkoutDaoImpl.modify(entity);
        verify(session,times(1)).update(entity);
    }


    @Test
    public void testFindAllCopies() {
        List<Checkout> checkoutList = new ArrayList<>();
        checkoutList.add(checkout1);
        checkoutList.add(checkout2);
        when(criteria.list()).thenReturn(checkoutList);
        List<Checkout> allCopies = checkoutDaoImpl.findAllCopies();
        verify(criteria,times(1)).list();
        Assert.assertEquals(2, allCopies.size());
    }
}
