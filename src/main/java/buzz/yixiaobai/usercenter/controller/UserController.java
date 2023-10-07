package buzz.yixiaobai.usercenter.controller;

import buzz.yixiaobai.usercenter.model.domain.User;
import buzz.yixiaobai.usercenter.model.request.UserLoginRequest;
import buzz.yixiaobai.usercenter.model.request.UserRegisterRequest;
import buzz.yixiaobai.usercenter.service.UserService;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 * @author yixiaobai
 * @date 2023/10/07 23:45:42
 */
@RestController
@RequestMapping
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(ObjectUtil.isEmpty(userRegisterRequest))
            return null;
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkUserPassword = userRegisterRequest.getCheckUserPassword();
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkUserPassword))
            return null;
        return userService.userRegister(userAccount, userPassword, checkUserPassword);
    }

    /**
     *
     * @param userLoginRequest
     * @return
     */
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(ObjectUtil.isEmpty(userLoginRequest))
            return null;
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount, userPassword))
            return null;
        User user = userService.userLogin(userAccount, userPassword, request);
        if(ObjectUtil.isEmpty(user))
            return null;
        return user;
    }
}
