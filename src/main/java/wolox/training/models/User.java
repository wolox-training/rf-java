package wolox.training.models;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private  String user;

    @Column(nullable = false)
    private LocalDate birthdate;

    @OneToMany(cascade=CascadeType.ALL, targetEntity=Book.class)
    @JoinColumn(name="book")
    private List<Book> books = new ArrayList<>();

    public User() {}

    public User(String username, String user, LocalDate birthdate) {
        this.username = username;
        this.user = user;
        this.birthdate = birthdate;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = Preconditions.checkNotNull(username, "Null in not accepted");
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = Preconditions.checkNotNull(user, "Null in not accepted");
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = Preconditions.checkNotNull(birthdate, "Null in not accepted");
    }

    public List<Book> getBooks() {
        return books;
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

}
