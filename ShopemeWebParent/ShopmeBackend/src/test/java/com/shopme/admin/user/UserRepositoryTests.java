package com.shopme.admin.user;


import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

 
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User user1 = new User("email@gmail.com", "user", "Phuc", "Mai");
        user1.addRole(roleAdmin);
        User savedUser = repo.save(user1);
        assert (savedUser.getId() > 0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles() {
        User user2 = new User("email14234@gmail.com", "user2", "Phuc", "Mai");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        user2.addRole(roleEditor);
        user2.addRole(roleAssistant);
        User savedUser = repo.save(user2);

        assert (savedUser.getId() > 0);

    }

    @Test
    public void testListAllUsers() {
        Iterable<User> allUsers = repo.findAll();
        allUsers.forEach(System.out::println);

    }

    @Test
    public void testGetUserById() {
        User user = repo.findById(1).get();
        System.out.println(user);


        assertThat(user).isNotNull();
    }

    @Test
    void testUpdateUserDetails() {
        User user = repo.findById(1).get();
        user.setEnabled(true);
        user.setEmail("phucdev@gmail.com");

        repo.save(user);
    }

    @Test
    void testUpdateRolesUser() {
        User user = repo.findById(1).get();
        Role roleEditor = new Role(3);
        Role roleSalePerson = new Role(2);
        user.getRoles().remove(roleEditor);
        user.getRoles().add(roleSalePerson);
        repo.save(user);
    }

    @Test
    void testDeleteUser() {
        Integer userId = 2;
        repo.deleteById(2);
    }

    @Test
    void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println(encodedPassword);

        assertThat(matches).isTrue();
    }

}
