package buzz.yixiaobai.usercenter.controller;

import buzz.yixiaobai.usercenter.common.BaseResponse;
import buzz.yixiaobai.usercenter.common.ErrorCode;
import buzz.yixiaobai.usercenter.common.Utils.ResultUtils;
import buzz.yixiaobai.usercenter.exception.BusinessException;
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

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册信息
     * @return 注册用户编号
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (ObjectUtil.isEmpty(userRegisterRequest))
            throw new BusinessException(ErrorCode.NULL_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkUserPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkUserPassword))
            return null;
        long userId = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(userId);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录参数
     * @param request          用户登录请求
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (ObjectUtil.isEmpty(userLoginRequest))
            return null;
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword))
            return null;
        User user = userService.userLogin(userLoginRequest, request);
        if (ObjectUtil.isEmpty(user))
            return null;
        return ResultUtils.success(user);
    }

    /**
     * 退出登录
     *
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
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User userCurrent = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (ObjectUtils.isEmpty(userCurrent)) return null;
        // 获取到用户id
        Long id = userCurrent.getId();
        // TODO: 校验用户是否合法
        User user = userService.lambdaQuery().eq(User::getId, id).one();
        // 用户数据脱敏
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 查询用户
     *
     * @param username 用户名称
     * @return 用户集合
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUserList(String username, HttpServletRequest request) {
        if (this.isAdmin(request)) {
            return null;
        }
        List<User> resultList = userService.searchUserList(username);
        return ResultUtils.success(resultList);
    }

    /**
     * 根据用户id来删除用户信息
     *
     * @param userId 用户id
     * @return 是否删除成功
     */
    @DeleteMapping
    public BaseResponse<Boolean> deleteUser(@RequestBody Long userId, HttpServletRequest request) {
        if (this.isAdmin(request)) return null;
        boolean result = userService.deleteUserById(userId);
        return ResultUtils.success(result);
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
