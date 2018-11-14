package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import wolox.training.models.User;

import java.time.LocalDate;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);
    @Query(value = "SELECT u FROM User u WHERE (:from is NULL OR :from >= u.birthdate) AND (:to is NULL OR :to <= u.birthdate) AND (:username is NULL OR UPPER(u.username) LIKE CONCAT('%', UPPER (:username),'%')) ORDER BY u.id")
    Page<User> findByBirthdateBetweenAndUsernameContainingIgnoreCase(@Param("from") LocalDate from, @Param("to") LocalDate to, @Param("username") String username, Pageable pageable);
}