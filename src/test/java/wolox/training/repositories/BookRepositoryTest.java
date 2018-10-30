package wolox.training.repositories;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.models.Book;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void  findByAuthorSuccess() {
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

        entityManager.persistAndFlush(book);
        Book found = bookRepository.findByAuthor(book.getAuthor());
        assertEquals(found.getAuthor(), book.getAuthor());
    }

    @Test
    public void  createBookSuccess() {
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

        entityManager.persistAndFlush(book);
        Book found = bookRepository.getOne(book.getId());
        assertNotNull(found);
    }

    @Test
    public void wrongCreateBook() {
        Book wrongBook = new Book();

        entityManager.persistAndFlush(wrongBook);
        Book bookNotFound = bookRepository.getOne(wrongBook.getId());
        assertNull(bookNotFound);
    }

    @Test
    public void findByAuthorNotFound() {
        String badAuthor = "this author not found";
        Book bookNotFound = bookRepository.findByAuthor(badAuthor);
        assertNull(bookNotFound);
    }

}