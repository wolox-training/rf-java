package wolox.training.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wolox.training.Exceptions.*;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

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
    public User updateUser(@RequestBody User userUpdated, @PathVariable Long id) {
        if (!userUpdated.getId().equals(id)) {
            throw new UserIdMismatchException("Mismatch ID");
        }
        User actualUser = userRepository.findById(id).orElseThrow(UserIdNotFoundException::new);
        userUpdated.setPassword(actualUser.getPassword());
        return userRepository.save(userUpdated);
    }

    @PutMapping("/{id}/books/{idBook}")
    public void addBook(@PathVariable Long id, @PathVariable Long idBook) {
        Book book = bookRepository.findById(idBook)
                .orElseThrow(BookNotFoundException::new);
        User user = userRepository.findById(id)
                .orElseThrow(UserIdNotFoundException::new);

        if (user.getBook(idBook) == null) {
            user.getBooks().add(book);
            userRepository.save(user);
        } else {
            throw new BookAlreadyOwnedException();
        }
    }

    @DeleteMapping("/{id}/books/{idBook}")
    public void deleteBook(@PathVariable Long id, @PathVariable Long idBook) {
        Book book = bookRepository.findById(idBook)
                .orElseThrow(BookNotFoundException::new);
        userRepository.findById(id)
                .orElseThrow(UserIdNotFoundException::new)
                .removeBook(book);
    }

    @RequestMapping(value = "/logueduser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String currentUser(Principal principal) {
        JSONObject body = new JSONObject();
        body.put("username", principal.getName());
        return body.toString();
    }

    @RequestMapping(value = "/complexsearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> findByBirthdateBetweenAndUsernameContaining(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate from,
                                                                  @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate to,
                                                                  @RequestParam("username") String username) {


        if(from == null || to == null) { //check corrects format dates
            throw new BadDateException();
        }

        List<User> users = userRepository.findByBirthdateBetweenAndUsernameContainingIgnoreCase(from, to, username);
        return users;

    }

}