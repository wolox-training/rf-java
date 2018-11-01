package wolox.training.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import wolox.training.models.Book;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

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
        Assert.isTrue(book.getAuthor().equals(found.getAuthor()));
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
        Assert.notNull(found);
    }

    @Test
    public void findByAuthorNotFound() {
        String badAuthor = "this author not found";
        Book bookNotFound = bookRepository.findByAuthor(badAuthor);
        Assert.isNull(bookNotFound);
    }

}