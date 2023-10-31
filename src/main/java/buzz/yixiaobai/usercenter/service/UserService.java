package buzz.yixiaobai.usercenter.service;

import buzz.yixiaobai.usercenter.model.domain.User;
import buzz.yixiaobai.usercenter.model.request.UserLoginRequest;
import buzz.yixiaobai.usercenter.model.request.UserRegisterRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

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
     *
     * @param userRegisterRequest 用户注册请求信息
     * @return 新用户ID
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 登录
     *
     * @param userLoginRequest 用户请求信息
     * @param request 请求信息
     * @return 用户信息
     */
    User userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

                   /**
     * 用户注销
     * @param request 请求用户注销
     */
    void userLogout(HttpServletRequest request);

    /**
     * 通过用户名称来查询用户信息
     *
     * @param username 用户名称
     * @return 用户集合
     */
    List<User> searchUserList(String username);

    /**
     * 根据用户id来删除用户信息
     *
     * @param userId 用户ID 信息
     * @return 是否删除成功
     */
    boolean deleteUserById(Long userId);

    /**
     * 用户数据脱敏
     *
     * @param originUser 需要脱敏的用户数据
     * @return 脱敏后的用户信息
     */
    User getSafetyUser(User originUser);
}
