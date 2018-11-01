package wolox.training.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import wolox.training.models.User;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void  findByUsernameSuccess() {
        User user = new User();
        user.setBirthdate(LocalDate.now());
        user.setUser("test OTRO");
        user.setUsername("test OTRO");

        entityManager.persistAndFlush(user);
        User found = userRepository.findByUsername(user.getUsername());
        Assert.isTrue(user.getUsername().equals(found.getUsername()));
    }

    @Test
    public void  createUserSuccess() {
        User user = new User();
        user.setBirthdate(LocalDate.now());
        user.setUser("test OTRO");
        user.setUsername("test OTRO");

        entityManager.persistAndFlush(user);
        User found = userRepository.getOne(user.getId());
        Assert.notNull(found);
    }

    @Test
    public void findByUsernameNotFound() {
        String badUsername = "this author not found";
        User userNotFound = userRepository.findByUsername(badUsername);
        Assert.isNull(userNotFound);
    }
}
