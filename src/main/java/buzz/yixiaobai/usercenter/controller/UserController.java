package buzz.yixiaobai.usercenter.controller;

import buzz.yixiaobai.usercenter.model.domain.User;
import buzz.yixiaobai.usercenter.model.request.UserLoginRequest;
import buzz.yixiaobai.usercenter.model.request.UserRegisterRequest;
import buzz.yixiaobai.usercenter.service.UserService;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static buzz.yixiaobai.usercenter.constant.UserConstant.ADMIN_ROLE;
import static buzz.yixiaobai.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author yixiaobai
 * @date 2023/10/07 23:45:42
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (ObjectUtil.isEmpty(userRegisterRequest))
            return null;
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkUserPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkUserPassword))
            return null;
        return userService.userRegister(userRegisterRequest);
    }

    /**
     * 用户登录
     * @param userLoginRequest  用户登录参数
     * @param request 用户登录请求
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (ObjectUtil.isEmpty(userLoginRequest))
            return null;
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword))
            return null;
        User user = userService.userLogin(userLoginRequest, request);
        if (ObjectUtil.isEmpty(user))
            return null;
        return user;
    }

    /**
     * 退出登录
     * @param request 用户登录请求
     * @return 退出是否成功
     */
    @ApiOperation("退出登录")
    @PostMapping("/outLogin")
    public void userLogout(HttpServletRequest request) {
        userService.userLogout(request);
    }

    /**
     * 获取用户的登录信息
     *
     * @param request 请求信息
     * @return 用户信息
     */
    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request) {
        User userCurrent = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (ObjectUtils.isEmpty(userCurrent)) return null;
        // 获取到用户id
        Long id = userCurrent.getId();
        // TODO: 校验用户是否合法
        User user = userService.lambdaQuery().ge(User::getId, id).one();
        // 用户数据脱敏
        return userService.getSafetyUser(user);
    }

    /**
     * 查询用户
     *
     * @param username 用户名称
     * @return 用户集合
     */
    @GetMapping("/search")
    public List<User> searchUserList(String username, HttpServletRequest request) {
        if (this.isAdmin(request)) {
            return new ArrayList<>();
        }
        return userService.searchUserList(username);
    }

    /**
     * 根据用户id来删除用户信息
     *
     * @param userId 用户id
     * @return 是否删除成功
     */
    @DeleteMapping
    public boolean deleteUser(@RequestBody Long userId, HttpServletRequest request) {
        if (this.isAdmin(request)) return false;
        return userService.deleteUserById(userId);
    }

    /**
     * 判断当前登录用户是否为管理员
     *
     * @param request request请求信息
     * @return 是否为管理员
     */
    private boolean isAdmin(HttpServletRequest request) {
        User attribute = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        return ObjectUtil.isEmpty(attribute) || Objects.equals(attribute.getUserRole(), ADMIN_ROLE);
    }
}
