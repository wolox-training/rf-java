package wolox.training.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Book findByAuthor(String author);
    Book findByIsbn(String isbn);
    List<Book> findByPublisherAndGenreAndYear(String publisher, String genre, String year);
}