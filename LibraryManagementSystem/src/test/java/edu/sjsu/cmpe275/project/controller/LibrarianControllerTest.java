package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.dao.MyCalendarDao;
import edu.sjsu.cmpe275.project.model.Book;
import edu.sjsu.cmpe275.project.model.BookCopy;
import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.service.*;
import edu.sjsu.cmpe275.project.util.CustomTimeService;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LibrarianControllerTest {
    @Mock
    SessionFactory sessionFactory;

    @Mock
    BookService bookService;
    @Mock
    UserService userService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private MyCalendarDao myCalendarDao;

    @Mock
    private CheckoutService checkoutService;

    @Mock
    private BookCartService cartService;

    @Mock
    private WaitListService waitListService;

    @Mock
    private BookCopyService bookCopyService;

    @Mock
    private HoldListService holdListService;

    @Mock
    private CustomTimeService myTimeService;


    @InjectMocks
    LibrarianControllerDummy librarianController;

    ModelMap modelMap;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        User user = new User();
        user.setFirstName("A1");
        user.setEmail("A1@a1.com");
        user.setId(123);

        Book book = new Book();
        book.setTitle("Title1");
        book.setId(122);

        Book book2 = new Book();
        book.setTitle("Title2");
        book.setId(123);

        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(122);

        BookCopy bookCopy2 = new BookCopy();
        bookCopy2.setId(123);

        List<Book> books = Arrays.asList(book, book2);
        List<BookCopy> bookscopy = Arrays.asList(bookCopy, bookCopy2);
        when(bookService.findAllBooks()).thenReturn(books);
        when(bookService.findById((String)any())).thenReturn(book);
        when(bookCopyService.findAllByBook((Book)any())).thenReturn(bookscopy);

        when(userService.findByEmail("A1@a1.com")).thenReturn(user);
        modelMap = new ModelMap();
    }

    @Test
    public void testDeleteBookBySearch(){
        Book book = new Book();
        book.setTitle("Title1");
        book.setId(122);
        List<Book> books = Arrays.asList(book);

        when(bookService.findById("122")).thenReturn(book);
        when(bookService.findByTitle("Title1")).thenReturn(books);

        librarianController.deleteBookFromSearch("122",modelMap);

        verify(bookService ,times(1)).deleteBook(122);


    }

    @Test
    public void testHomeAction(){

        librarianController.renderIndex(modelMap);
        verify(bookService, times(1)).findAllBooks();
        verify(userService, times(1)).findByEmail("A1@a1.com");
    }

    @Test
    public void testCreatingNewBook(){
        ModelMap modelMap2 = new ModelMap();
        assertEquals("newBook",librarianController.renderBookRegistration(modelMap2));
        assertTrue( modelMap2.get("book") instanceof Book );
    }

    @Test
    public void testEditBookById(){
        ModelMap modelMap2 = new ModelMap();
        librarianController.renderBookEdit((String) any(), modelMap2);
        assertEquals(2,modelMap2.get("copies"));
        assertEquals(true,modelMap2.get("edit"));

    }
    @Test
    public void testUpdateBookNoCopies(){
        Book book = new Book();
        String id = "123";
        librarianController.updateBook(book,id,"cn");
        verify(bookCopyService,times(0)).deleteCopy((BookCopy)any());
        verify(bookCopyService,times(1)).saveCopy((BookCopy)any());
    }

    @Test
    public void testUpdateBookWithCopies(){
        Book book = new Book();
        String id = "123";
        librarianController.updateBook(book,id,"3");
        verify(bookCopyService,times(2)).deleteCopy((BookCopy)any());
        verify(bookCopyService,times(3)).saveCopy((BookCopy)any());
    }
}
