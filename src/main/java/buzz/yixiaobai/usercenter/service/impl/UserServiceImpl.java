package buzz.yixiaobai.usercenter.service.impl;

import buzz.yixiaobai.usercenter.domain.convert.struct.UserStruct;
import buzz.yixiaobai.usercenter.mapper.UserMapper;
import buzz.yixiaobai.usercenter.model.domain.User;
import buzz.yixiaobai.usercenter.service.UserService;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static buzz.yixiaobai.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author yixiaobai
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-09-24 22:47:50
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    /**
     * 用户转换类
     */
    @Resource
    private UserStruct userStruct;

    /**
     * 加密盐，混淆密码
     */
    static final String SALT = "xiaoliu";

    @Autowired
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword))
            return -1;
        if (userAccount.length() < 4) return -1;
        if (userPassword.length() < 8 || checkPassword.length() < 8) return -1;
        // 账户不能包含特殊字符
        String validPattern = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) return -1;
        // 密码和校验密码是否相同
        if (!StringUtils.equals(userPassword, checkPassword)) return -1;
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) return -1;
        // 2. 加密
        // 加密盐
        String encryptPassword = DigestUtil.md5Hex((SALT + userPassword).getBytes());
        // 3. 输入数据
        User user = new User();
        user.setUserAccount(userAccount);
        // 保存加密后的密码
        user.setUserPassword(encryptPassword);
        // 保存数据
        boolean saveResult = this.save(user);
        if (!saveResult) return -1;
        // 返回添加的用户ID
        return user.getId();
    }

    /**
     * 登录
     *
     * @param userAccount  用户名
     * @param userPassword 用户密码
     * @return 登录用户信息
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword))
            return null;
        if (userAccount.length() < 4) return null;
        if (userPassword.length() < 8) return null;
        // 账户不能包含特殊字符
        String validPattern = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) return null;
        // 2. 加密
        // 加密盐
        String encryptPassword = DigestUtil.md5Hex((SALT + userPassword).getBytes());
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (ObjectUtils.isEmpty(user)) {
            log.info("User login failed, userAccount cannot match userPassword!");
            return null;
        }
        // 3. 用户脱敏
        User safetyUser = this.getSafetyUser(user);
        // 4. 记录用户登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        // 返回添加的用户信息
        return safetyUser;
    }

    /**
     * 查询用户信息
     *
     * @param username 用户名称
     * @return 用户集合
     */
    @Override
    public List<User> searchUserList(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), User::getUsername, username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(users)) return new ArrayList<>();
        return users.stream().peek(user -> user.setUserRole(null)).collect(Collectors.toList());
    }

    /**
     * 根据ID删除用户信息
     *
     * @param userId 用户ID 信息
     * @return 是否删除成功
     */
    @Override
    public boolean deleteUserById(Long userId) {
        if (ObjectUtils.isEmpty(userId)) return false;
        int isDelete = userMapper.deleteById(userId);
        return isDelete > 0;
    }

    /**
     * 用户数据脱敏
     *
     * @param originUser 需要脱敏的用户数据
     * @return 脱敏后的用户信息
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (ObjectUtil.isEmpty(originUser)) return null;
        return userStruct.convertUser(originUser);
    }
}