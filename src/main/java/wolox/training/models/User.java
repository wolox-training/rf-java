package wolox.training.models;

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
    private List<Book> books;

    public User(String username, String user, LocalDate birthdate) {
        this.username = username;
        this.user = user;
        this.birthdate = birthdate;
        this.books = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

}
