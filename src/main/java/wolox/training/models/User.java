package wolox.training.models;

import com.google.common.base.Preconditions;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    private String password;

    private String role = "ROLE_ADMIN";

    @OneToMany(cascade=CascadeType.ALL, targetEntity=Book.class)
    private List<Book> books = new ArrayList<>();

    public User() {}

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = Preconditions.checkNotNull(username, "Null in not accepted");
    }


    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = Preconditions.checkNotNull(birthdate, "Null in not accepted");
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void setBooks(List<Book> books) {
        this.books = Preconditions.checkNotNull(books, "Null in not accepted");
    }

    public Book getBook(Long id) {
        for (Book book: this.books) {
            if(book.getId().equals(id)){
                return book;
            }
        }
        return null;
    }

    public void addBook(Book book) {
        if(this.getBook(book.getId()) == null){
            this.books.add(Preconditions.checkNotNull(book, "Null in not accepted"));
        }
    }
    
    public void removeBook(Book book) {
        this.books.remove(book);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
