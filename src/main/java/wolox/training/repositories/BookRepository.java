package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByAuthor(String author);
    Book findByIsbn(String isbn);
    List<Book> findByPublisherAndGenreAndYear(String publisher, String genre, String year);
}