package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Book findByAuthor(String author);
    Book findByIsbn(String isbn);
    Page<Book> findByPublisherAndGenreAndYear(String publisher, String genre, String year, Pageable pageable);
}