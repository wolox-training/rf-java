package wolox.training.controllers;

import org.junit.Before;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookRepository service;

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

    @Test
    public void findOne() throws Exception {

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
        mvc.perform(get("/api/books/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is(book.getTitle())));
    }

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
                .content(Utils.asJsonString(book)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteOne() throws Exception {
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

        mvc.perform(delete("/api/books/{id}", book.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void updateOne() throws Exception {
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

        mvc.perform(put("/api/books/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.asJsonString(book)))
                .andExpect(status().isOk());
    }
}