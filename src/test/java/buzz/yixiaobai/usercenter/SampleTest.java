package buzz.yixiaobai.usercenter;

import buzz.yixiaobai.usercenter.mapper.UserMapper;
import buzz.yixiaobai.usercenter.model.domain.User;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    /**
     */
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(userList.isEmpty(), "");
        userList.forEach(System.out::println);
    }
}
