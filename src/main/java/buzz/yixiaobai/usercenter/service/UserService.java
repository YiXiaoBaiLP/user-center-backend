package buzz.yixiaobai.usercenter.service;


import buzz.yixiaobai.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务
 *
 * @author yixiaobai
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-09-24 22:47:50
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 登录用户名
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
}
