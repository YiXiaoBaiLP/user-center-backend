package buzz.yixiaobai.usercenter.service;

import buzz.yixiaobai.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {


    @Autowired
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("xiaoliu");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setPhone("123");
        user.setEmail("456");
        user.setUserPassword("****");
        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(save);

    }

}
