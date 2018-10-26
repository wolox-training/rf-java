package wolox.training.models;

import com.google.common.base.Preconditions;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    
    private String genre;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private Integer pages;

    @Column(nullable = false)
    private String isbn;

    public Book() {}

    public Book(String genre, String author, String image, String title, String subtitle, String publisher, String year, Integer pages, String isbn) {
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
    }

    public Long getId() { return id; }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = Preconditions.checkNotNull(genre, "Null in not accepted");
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = Preconditions.checkNotNull(author, "Null in not accepted");
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = Preconditions.checkNotNull(image, "Null in not accepted");;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Preconditions.checkNotNull(title, "Null in not accepted");
    }

    public String getSubtitle() { return subtitle; }

    public void setSubtitle(String subtitle) {
        this.subtitle = Preconditions.checkNotNull(subtitle, "Null in not accepted");
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = Preconditions.checkNotNull(publisher, "Null in not accepted");
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = Preconditions.checkNotNull(year, "Null in not accepted");
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = Preconditions.checkNotNull(pages, "Null in not accepted");
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = Preconditions.checkNotNull(isbn, "Null in not accepted");
    }
}