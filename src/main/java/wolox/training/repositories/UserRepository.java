package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

import java.time.LocalDate;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);
    Page<User> findByBirthdateBetweenAndUsernameContainingIgnoreCase(LocalDate from, LocalDate to, String username, Pageable pageable);
}