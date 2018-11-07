package wolox.training.controllers;

import org.junit.Before;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;
import wolox.training.services.OpenLibraryService;
import wolox.training.utils.Utils;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import wolox.training.models.Book;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.mockito.BDDMockito.given;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.repositories.BookRepository;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    private MockMvc mvc;

    @MockBean
    private BookRepository service;

    @MockBean
    private Book aBook;

    @MockBean
    private OpenLibraryService serviceLibraryService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;
    
    private Book mockBook;

    @Before
    public void setup() {
        User user = new User();
        user.setBirthdate(LocalDate.now());
        user.setUser("test OTRO");
        user.setUsername("test OTRO");
        user.setPassword("pass");
        userRepository.saveAndFlush(user);

        mockBook = new Book();
        mockBook.setTitle("title test");
        mockBook.setGenre("genre test");
        mockBook.setAuthor("author test");
        mockBook.setImage("image test");
        mockBook.setIsbn("isbn test");
        mockBook.setPages(4321);
        mockBook.setSubtitle("subtitle test");
        mockBook.setPublisher("publisher test");
        mockBook.setYear("year test");
        service.saveAndFlush(mockBook);

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void findAll() throws Exception {
        Book book = new Book();
        book.setTitle("title test");
        book.setGenre("genre test");
        book.setAuthor("author test");
        book.setImage("image test");
        book.setIsbn("isbn test");
        book.setPages(4321);
        book.setSubtitle("subtitle test");
        book.setPublisher("publisher test");
        book.setYear("year test");

        List<Book> allBooks = Arrays.asList(book);

        given(service.findAll()).willReturn(allBooks);
        mvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is(book.getTitle())));
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void findOne() throws Exception {
        aBook = new Book();
        aBook.setTitle("title test");
        aBook.setGenre("genre test");
        aBook.setAuthor("author test");
        aBook.setImage("image test");
        aBook.setIsbn("isbn test");
        aBook.setPages(4321);
        aBook.setSubtitle("subtitle test");
        aBook.setPublisher("publisher test");
        aBook.setYear("year test");

        given(service.findById(1l)).willReturn(Optional.of(aBook));
        mvc.perform(get("/api/books/{id}", 1l))
                .andExpect(status().isOk());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void findOneNotFound() throws Exception {
        mvc.perform(get("/api/books/{id}", 1l))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void create() throws Exception {
        Book book = new Book();
        book.setTitle("title test");
        book.setGenre("genre test");
        book.setAuthor("author test");
        book.setImage("image test");
        book.setIsbn("isbn test");
        book.setPages(4321);
        book.setSubtitle("subtitle test");
        book.setPublisher("publisher test");
        book.setYear("year test");

        mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.asJsonString(book))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void createWrongBook() throws Exception {
        Book book = new Book();
        book.setTitle("title test");

        mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.asJsonString(book))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void deleteOne() throws Exception {
        given(aBook.getId()).willReturn(1l);
        given(service.findById(aBook.getId())).willReturn(Optional.of(aBook));

        mvc.perform(delete("/api/books/{id}", aBook.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void deleteOneNotFound() throws Exception {
        mvc.perform(delete("/api/books/{id}", 2l)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void updateSuccess() throws Exception {
        String bodyBookJson = "{\n\t\"id\": 1,\n\t\"genre\": \"otro genre\",\n\t\"author\": \"author test\",\n\t\"image\": \"otro test\",\n\t\"title\": \"title test\",\n\t\"subtitle\": \"subtitle test\",\n\t\"publisher\": \"publisher test\",\n\t\"year\":\"year test\",\n\t\"pages\":123,\n\t\"isbn\":\"isbn test\"\n}";
        given(service.findById(1l)).willReturn(Optional.of(aBook));
        mvc.perform(put("/api/books/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyBookJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void updateBadId() throws Exception {
        given(aBook.getId()).willReturn(1l);
        String bodyBookJson = "{\"id\":\1\"}";
        mvc.perform(put("/api/books/{id}", 2l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyBookJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is4xxClientError());

    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test public void findNotFound() throws Exception {
        mvc.perform(get("/api/books/search?isbn=sarasa"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test public void findByIsbnFoundOpenLibrary() throws Exception {
        String isbnInOpenLibrary = "0385472579";
        mvc.perform(get("/api/books/search?isbn=" + isbnInOpenLibrary))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(isbnInOpenLibrary));
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test public void findByIsbnExists() throws Exception {
        aBook = new Book();
        aBook.setTitle("title test");
        aBook.setGenre("genre test");
        aBook.setAuthor("author test");
        aBook.setImage("image test");
        aBook.setIsbn("isbn sarasa");
        aBook.setPages(4321);
        aBook.setSubtitle("subtitle test");
        aBook.setPublisher("publisher test");
        aBook.setYear("year test");

        given(service.findByIsbn(aBook.getIsbn())).willReturn(aBook);
        mvc.perform(get("/api/books/search?isbn=" + aBook.getIsbn())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(aBook.getIsbn()));
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void findByPublisherAndGenreAndYear() throws Exception {
        List<Book> allBooks = Arrays.asList(mockBook);
        String url = "/api/books/complexsearch?publisher=" +
                    mockBook.getPublisher() + "&genre=" +
                    mockBook.getGenre()+"&year=" +
                    mockBook.getYear();
        given(service.findByPublisherAndGenreAndYear(mockBook.getPublisher(), mockBook.getGenre(), mockBook.getYear())).willReturn(allBooks);
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is(mockBook.getTitle())));
    }
}
