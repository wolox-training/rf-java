package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Book findByAuthor(String author);
    Book findByIsbn(String isbn);
    @Query(
        value = "SELECT * FROM book WHERE ( book.publisher LIKE CONCAT('%',:publisher,'%') AND " +
                "                           book.genre LIKE CONCAT('%',:genre,'%') AND " +
                "                           book.year LIKE CONCAT('%',:year,'%'))" +
                " ORDER BY book.id",
        countQuery = "SELECT count(*) FROM book",
        nativeQuery = true)
    Page<Book> findByPublisherAndGenreAndYear(@Param("publisher") String publisher,
                                              @Param("genre") String genre,
                                              @Param("year") String year,
                                              Pageable pageable);
}