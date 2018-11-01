package wolox.training.controllers;

import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository serviceUser;

    @MockBean
    private BookRepository serviceBook;

    @MockBean
    private User aUser;

    @MockBean
    private Book aBook;

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

    @Test
    public void findOneNotFound() throws Exception {
        given(serviceUser.findById(1l)).willReturn(Optional.of(aUser));
        mvc.perform(get("/api/books/{id}", 1l))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void create() throws Exception {
        String bodyBookJson = "\n{\"username\":\"test username\",\"user\":\"test user\",\"birthdate\":\"2015-10-10\"}";
        mvc.perform(post("/api/Users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyBookJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void createWrongUser() throws Exception {
        User user = new User();
        user.setUsername("username test");

        mvc.perform(post("/api/Users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.asJsonString(user)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteOne() throws Exception {
        given(aUser.getId()).willReturn(1l);
        given(serviceUser.findById(aUser.getId())).willReturn(Optional.of(aUser));
        mvc.perform(delete("/api/Users/{id}", aUser.getId()))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteOneNotFound() throws Exception {
        mvc.perform(delete("/api/Users/{id}", 2l))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateSuccess() throws Exception {
        String bodyBookJson = "\n{\"id\": 1,\"username\":\"test username\",\"user\":\"test user\",\"birthdate\":\"2015-10-10\"}";
        given(serviceUser.findById(1l)).willReturn(Optional.of(aUser));
        mvc.perform(put("/api/Users/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyBookJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBadId() throws Exception {
        given(aUser.getId()).willReturn(1l);
        String  bodyUserJson = "{\"id\":\1\"}";
        mvc.perform(put("/api/Users/{id}", 2l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyUserJson))
                .andExpect(status().is4xxClientError());
    }

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

        mvc.perform(put("/api/Users/1/books/1"))
                .andExpect(status().is2xxSuccessful());
    }

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

        mvc.perform(put("/api/Users/1/books/1")) // add again the book with id:1
                .andExpect(status().is4xxClientError());
    }
}
