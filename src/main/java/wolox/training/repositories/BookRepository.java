package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import wolox.training.models.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Book findByAuthor(String author);
    Book findByIsbn(String isbn);
    Page<Book> findByPublisherAndGenreAndYear(String publisher, String genre, String year, Pageable pageable);
}