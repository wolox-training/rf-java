package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.Exceptions.BookNotFoundException;
import wolox.training.Exceptions.UserIdMismatchException;
import wolox.training.Exceptions.UserIdNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/Users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserIdNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User User) {
        return userRepository.save(User);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.findById(id)
                .orElseThrow(UserIdNotFoundException::new);
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User User, @PathVariable Long id) {
        if (!User.getId().equals(id)) {
            throw new UserIdMismatchException("Mismatch ID");
        }
        userRepository.findById(id)
                .orElseThrow(UserIdNotFoundException::new);
        return userRepository.save(User);
    }

    @PutMapping("/{id}/books/{idBook}")
    public void addBook(@PathVariable Long id, @PathVariable Long idBook) {
        Book book = bookRepository.findById(idBook)
                .orElseThrow(BookNotFoundException::new);
        userRepository.findById(id)
                .orElseThrow(UserIdNotFoundException::new)
                .addBook(book);
    }

    @DeleteMapping("/{id}/books/{idBook}")
    public void deleteBook(@PathVariable Long id, @PathVariable Long idBook) {
        Book book = bookRepository.findById(idBook)
                .orElseThrow(BookNotFoundException::new);
        userRepository.findById(id)
                .orElseThrow(UserIdNotFoundException::new)
                .removeBook(book);
    }

}