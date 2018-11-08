package wolox.training.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);
    List<User> findByBirthdateBetweenAndUsernameContainingIgnoreCase(LocalDate from, LocalDate to, String username);
}