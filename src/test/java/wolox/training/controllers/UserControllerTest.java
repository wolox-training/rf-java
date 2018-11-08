package wolox.training.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wolox.training.models.Book;
import wolox.training.models.User;
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
import wolox.training.repositories.UserRepository;
import wolox.training.utils.Utils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private MockMvc mvc;

    @MockBean
    private UserRepository serviceUser;

    @MockBean
    private BookRepository serviceBook;

    @MockBean
    private User aUser;

    private User mockUser;

    @MockBean
    private Book aBook;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        User user = new User();
        user.setBirthdate(LocalDate.now());
        user.setUser("test OTRO");
        user.setUsername("test OTRO");
        user.setPassword("pass");
        serviceUser.saveAndFlush(user);

        mockUser = new User();
        mockUser.setBirthdate(LocalDate.now());
        mockUser.setUser("mock user");
        mockUser.setUsername("mock username");
        mockUser.setPassword("pass");
        serviceUser.saveAndFlush(mockUser);

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void findAll() throws Exception{

        User user = new User();
        user.setBirthdate(LocalDate.now());
        user.setUser("test OTRO");
        user.setUsername("test OTRO");

        List<User> allUsers = Arrays.asList(user);

        given(serviceUser.findAll()).willReturn(allUsers);

        mvc.perform(get("/api/Users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].user", is("test OTRO")));
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void findOne() throws Exception {
        aUser = new User();
        aUser.setBirthdate(LocalDate.now());
        aUser.setUser("test OTRO");
        aUser.setUsername("test OTRO");
        given(serviceUser.findById(1l)).willReturn(Optional.of(aUser));
        mvc.perform(get("/api/Users/{id}", 1l))
                .andExpect(status().isOk());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void findOneNotFound() throws Exception {
        given(serviceUser.findById(1l)).willReturn(Optional.of(aUser));
        mvc.perform(get("/api/books/{id}", 1l))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void create() throws Exception {
        String bodyBookJson = "\n{\"username\":\"test username\",\"user\":\"test user\",\"birthdate\":\"2015-10-10\"}";
        mvc.perform(post("/api/Users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyBookJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void createWrongUser() throws Exception {
        User user = new User();
        user.setUsername("username test");

        mvc.perform(post("/api/Users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.asJsonString(user)))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void deleteOne() throws Exception {
        given(aUser.getId()).willReturn(1l);
        given(serviceUser.findById(aUser.getId())).willReturn(Optional.of(aUser));
        mvc.perform(delete("/api/Users/{id}", aUser.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }
    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void deleteOneNotFound() throws Exception {
        mvc.perform(delete("/api/Users/{id}", 2l)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void updateSuccess() throws Exception {
        String bodyUserJson = "\n{\"id\": 1,\"username\":\"test username\",\"user\":\"test user\",\"birthdate\":\"2015-10-10\"}";

        aUser = new User();
        aUser.setBirthdate(LocalDate.now());
        aUser.setUser("test OTRO");
        aUser.setUsername("test OTRO");
        aUser.setPassword("pass");

        given(serviceUser.findById(1l)).willReturn(Optional.of(aUser));
        mvc.perform(put("/api/Users/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyUserJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void updateBadId() throws Exception {
        given(aUser.getId()).willReturn(1l);
        String  bodyUserJson = "{\"id\":\1\"}";
        mvc.perform(put("/api/Users/{id}", 2l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyUserJson))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void addBook() throws Exception {
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
        given(serviceBook.findById(1l)).willReturn(Optional.of(aBook));

        given(serviceUser.findById(1l)).willReturn(Optional.of(aUser));

        mvc.perform(put("/api/Users/1/books/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void addBookAlreadyOwned() throws Exception {
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
        given(serviceBook.findById(1l)).willReturn(Optional.of(aBook));
        List<Book> allBooks = Arrays.asList(aBook);

        given(serviceBook.findAll()).willReturn(allBooks); //mock the list of books

        User user = new User();
        user.setBirthdate(LocalDate.now());
        user.setUser("test OTRO");
        user.setUsername("test OTRO");
        user.setBooks(serviceBook.findAll()); //add all books to the user

        given(serviceUser.findById(1l)).willReturn(Optional.of(aUser)); //mock the user with all books

        mvc.perform(put("/api/Users/{idUser}/books/1", aUser.getId())) // add again the book with id:1
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void currentUser() throws Exception {
        mvc.perform(get("/api/Users/logueduser"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is("test OTRO")));
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void findByBirthdateBetweenAndUsernameContaining() throws Exception {
        // check the range two months before and after from now
        LocalDate fromBirthdate = mockUser.getBirthdate().minusMonths(2);
        LocalDate toBirthdate = mockUser.getBirthdate().plusMonths(2);
        String username = mockUser.getUsername();
        String url = "/api/Users/complexsearch?from=" + fromBirthdate.toString() + "&to=" + toBirthdate.toString() + "&username=" + username;

        List<User> allUsers = Arrays.asList(mockUser);
        given(serviceUser.findByBirthdateBetweenAndUsernameContainingIgnoreCase(fromBirthdate, toBirthdate, username)).willReturn(allUsers);

        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username", is(username)));
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void findByBirthdateBetweenAndUsernameContainingWrongDate() throws Exception {
        String fromBirthdate = "sarasa";
        LocalDate toBirthdate = mockUser.getBirthdate();
        String username = mockUser.getUsername();
        String url = "/api/Users/complexsearch?from=" + fromBirthdate + "&to=" + toBirthdate.toString() + "&username=" + username;

        mvc.perform(get(url))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username="test OTRO", password = "pass")
    @Test
    public void findByBirthdateBetweenAndUsernameContainingEmpty() throws Exception {
        // check the range two months before and after from now
        LocalDate fromBirthdate = mockUser.getBirthdate().plusMonths(2);
        LocalDate toBirthdate = mockUser.getBirthdate().plusMonths(8);
        String username = mockUser.getUsername();
        String url = "/api/Users/complexsearch?from=" + fromBirthdate.toString() + "&to=" + toBirthdate.toString() + "&username=" + username;

        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"));
    }
}
