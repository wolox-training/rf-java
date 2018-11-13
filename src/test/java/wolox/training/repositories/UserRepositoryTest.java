package wolox.training.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import wolox.training.models.Book;
import wolox.training.models.User;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;

    @Before
    public void setup(){
        user = new User();
        user.setBirthdate(LocalDate.now());
        user.setUsername("test OTRO");
        user.setPassword("pass");
    }

    @Test
    public void  findByUsernameSuccess() {
        entityManager.persistAndFlush(user);
        User found = userRepository.findByUsername(user.getUsername());
        Assert.isTrue(user.getUsername().equals(found.getUsername()));
    }

    @Test
    public void  createUserSuccess() {
        entityManager.persistAndFlush(user);
        User found = userRepository.findById(user.getId()).get();
        Assert.notNull(found);
    }

    @Test
    public void createWrongUser() {
        User user = new User();
        userRepository.save(user);
        User found = userRepository.findById(user.getId()).get();
        Assert.isNull(found.getUsername());
    }

    @Test
    public void findByUsernameNotFound() {
        String badUsername = "this author not found";
        User userNotFound = userRepository.findByUsername(badUsername);
        Assert.isNull(userNotFound);
    }

    @Test
    public void addBook() {
        Book book = new Book(); //create the book
        book.setTitle("title test");
        book.setGenre("genre test");
        book.setAuthor("author test");
        book.setImage("image test");
        book.setIsbn("isbn test");
        book.setPages(4321);
        book.setSubtitle("subtitle test");
        book.setPublisher("publisher test");
        book.setYear("year test");

        bookRepository.save(book);  //save the book
        entityManager.persistAndFlush(user); //save the user

        Book bookToTest = bookRepository.findById(book.getId()).get(); // get book from db
        user.addBook(book);
        entityManager.persistAndFlush(user); //save the user with book
        User userWithBook = userRepository.findById(user.getId()).get(); // get user from db
        Assert.isTrue(userWithBook.getBooks().size() == 1);
    }

    @Test
    public void removeBook() {
        Book book = new Book(); //create the book
        book.setTitle("title test");
        book.setGenre("genre test");
        book.setAuthor("author test");
        book.setImage("image test");
        book.setIsbn("isbn test");
        book.setPages(4321);
        book.setSubtitle("subtitle test");
        book.setPublisher("publisher test");
        book.setYear("year test");

        bookRepository.save(book); //save the book
        entityManager.persistAndFlush(user); //save the user

        Book bookToTest = bookRepository.findById(book.getId()).get(); // get book from db
        user.addBook(bookToTest);
        entityManager.persistAndFlush(user); //save the user with book
        user.removeBook(bookToTest); //remove the book
        entityManager.persistAndFlush(user); //save the user with no books
        User userWithNoBooks = userRepository.findById(user.getId()).get(); // get user from db
        Assert.isTrue(userWithNoBooks.getBooks().size() == 0);
    }

    @Test
    public void addBookAlready() {
        Book book = new Book(); //create the book
        book.setTitle("title test");
        book.setGenre("genre test");
        book.setAuthor("author test");
        book.setImage("image test");
        book.setIsbn("isbn test");
        book.setPages(4321);
        book.setSubtitle("subtitle test");
        book.setPublisher("publisher test");
        book.setYear("year test");

        bookRepository.save(book);  //save the book
        entityManager.persistAndFlush(user); //save the user

        Book bookToTest = bookRepository.findById(book.getId()).get(); // get book from db
        user.addBook(bookToTest);
        entityManager.persistAndFlush(user); //save the user with book
        User userWithBook = userRepository.findById(user.getId()).get(); // get user from db
        userWithBook.addBook(bookToTest); //add two times the same book, the second book must be is ignored
        userWithBook.addBook(bookToTest);
        Assert.isTrue(userWithBook.getBooks().size() == 1);
    }
}
