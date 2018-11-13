package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import wolox.training.models.User;

import java.time.LocalDate;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);
    Page<User> findByBirthdateBetweenAndUsernameContainingIgnoreCase(LocalDate from, LocalDate to, String username, Pageable pageable);
}