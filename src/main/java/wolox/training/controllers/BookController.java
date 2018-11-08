package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.Exceptions.*;
import wolox.training.services.OpenLibraryService;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController{

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> findAll(@RequestParam(value = "pagesize", required = false) Integer pageSize,
                              @RequestParam(value = "page", required = false) Integer pageNumber,
                              @RequestParam(value = "orderby", required = false) String orderBy,
                              @RequestParam(value = "sort", required = false) String sort) {

        PageRequest request = Helper.buildPaginationAndSortingRequest(pageSize, pageNumber, orderBy, sort);
        return bookRepository.findAll(request).getContent();
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException("Mismatch ID");
        }
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }

    @GetMapping("/search")
    public Optional find(@RequestParam("isbn") String isbn, HttpServletResponse response) {
        Book book = bookRepository.findByIsbn(isbn);
        response.setStatus(HttpServletResponse.SC_OK);
        if(book == null) {
            OpenLibraryService openLibraryService = new OpenLibraryService();
            book = openLibraryService.find(isbn);

            if(book == null) {
                throw new BookNotFoundException();
            } else {
                book.setImage(""); // image is required for save the book
                bookRepository.save(book);
                response.setStatus(HttpServletResponse.SC_CREATED);
            }
        }
        return Optional.of(book);
    }

    @GetMapping("/complexsearch")
    public List<Book> findByPublisherAndGenreAndYear(@RequestParam("publisher") String publisher,
                                                     @RequestParam("genre") String genre,
                                                     @RequestParam("year") String year) {

        List<Book> books = bookRepository.findByPublisherAndGenreAndYear(publisher, genre, year);

        return books;
    }

}
