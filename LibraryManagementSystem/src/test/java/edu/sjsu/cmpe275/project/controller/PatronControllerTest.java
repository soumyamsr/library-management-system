package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.dao.MyCalendarDao;
import edu.sjsu.cmpe275.project.model.*;
import edu.sjsu.cmpe275.project.service.*;
import edu.sjsu.cmpe275.project.util.CustomTimeService;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PatronControllerTest {


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
    PatronControllerDummy patronController;



    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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

        Checkout ch1 = new Checkout();
        Checkout ch2 = new Checkout();

        BooksHoldList booksHoldList = new BooksHoldList();

        when(checkoutService.findByBookId(anyInt())).thenReturn(Arrays.asList(ch1,ch2));
        when(bookService.findById((String)any())).thenReturn(book);
        when(bookCopyService.findAllByBook((Book)any())).thenReturn(bookscopy);
        when(holdListService.findAllBookCopies( anyInt())).thenReturn(bookscopy);
        when(holdListService.getFirstInLineForBook( anyInt())).thenReturn(booksHoldList);
        when(checkoutService.findByUserId(anyInt())).thenReturn(Arrays.asList(ch1,ch2));



        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    public void testSearchBookByText() throws Exception {
        User u1 = new User();
        u1.setFirstName("A1");
        u1.setEmail("A1@a1.com");
        u1.setId(123);

        Book b1 = new Book();
        b1.setTitle("Title1");
        b1.setId(122);

        List<Book> books = Arrays.asList(b1);

        when(bookService.findByTitle("Title1")).thenReturn(books);
        when(userService.findByEmail("A1@a1.com")).thenReturn(u1);

        mockMvc.perform(get("/patron/search-book-Title1")
                .with(user("A1@a1.com").roles("USER"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.ALL)).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public  void testAddToCartById(){
        patronController.addToCart("12" ,"11",(ModelMap) any());



    }

}
