package buzz.yixiaobai.usercenter.service;

import buzz.yixiaobai.usercenter.model.domain.User;
import buzz.yixiaobai.usercenter.model.request.UserRegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户接口测试类
 */
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

    @Test
    void userRegister() {
        String userAccount = "xialiu";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserAccount(userAccount);
        userRegisterRequest.setUserPassword(userPassword);
        userRegisterRequest.setCheckPassword(checkPassword);
        userRegisterRequest.setPlanetCode(planetCode);
        long result = userService.userRegister(userRegisterRequest);
        Assertions.assertEquals(-1, result);
        userAccount = "xiao";
        userRegisterRequest.setUserAccount(userAccount);
        result = userService.userRegister(userRegisterRequest);
        Assertions.assertEquals(-1, result);
        userAccount = "xiaxiao";
        userPassword = "12345678";
        userRegisterRequest.setUserAccount(userAccount);
        userRegisterRequest.setUserPassword(userPassword);
        result = userService.userRegister(userRegisterRequest);
        Assertions.assertEquals(-1, result);
        userAccount = "xiaxiao &&&liu (*&@#$";
        userPassword = "12345678";
        userRegisterRequest.setUserAccount(userAccount);
        userRegisterRequest.setUserPassword(userPassword);
        result = userService.userRegister(userRegisterRequest);
        Assertions.assertEquals(-1, result);
        userPassword = "12345678";
        checkPassword = "12345678";
        userRegisterRequest.setUserPassword(userPassword);
        userRegisterRequest.setCheckPassword(checkPassword);
        result = userService.userRegister(userRegisterRequest);
        Assertions.assertEquals(-1, result);
        userAccount = "xiaoliu";
        checkPassword = "12345678";
        userRegisterRequest.setUserAccount(userAccount);
        userRegisterRequest.setCheckPassword(checkPassword);
        result = userService.userRegister(userRegisterRequest);
        Assertions.assertEquals(-1, result);
        userAccount = "xiaoliu";
        userPassword = "Pa$$w0rd";
        userRegisterRequest.setUserAccount(userAccount);
        userRegisterRequest.setUserPassword(userPassword);
        result = userService.userRegister(userRegisterRequest);
        Assertions.assertTrue(result > 0);
    }
}
