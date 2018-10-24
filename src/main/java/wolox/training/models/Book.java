package wolox.training.models;

import javax.annotation.Generated;
import javax.persistence.Column;

import javax.persistence.Id;

import javax.persistence.Entity;
@Entity
public class Book {

    
    private String Genre;

    @Column(nullable = false)
    private String Author;

    @Column(nullable = false)
    private String Image;

    @Column(nullable = false)
    private String Title;

    @Column(nullable = false)
    private String Subtitle;

    @Column(nullable = false)
    private String Publisher;

    @Column(nullable = false)
    private String Year;

    @Column(nullable = false)
    private Integer Pages;

    @Column(nullable = false)
    private String isbn;

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public Integer getPages() {
        return Pages;
    }

    public void setPages(Integer pages) {
        Pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}