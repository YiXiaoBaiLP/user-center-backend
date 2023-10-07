package buzz.yixiaobai.usercenter.service.impl;

import buzz.yixiaobai.usercenter.mapper.UserMapper;
import buzz.yixiaobai.usercenter.model.domain.User;
import buzz.yixiaobai.usercenter.service.UserService;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yixiaobai
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-09-24 22:47:50
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword))
            return -1;
        if(userAccount.length() < 4) return -1;
        if(userPassword.length() < 8 || checkPassword.length() < 8)return -1;
        // 账户不能包含特殊字符
        String validPattern = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(!matcher.find()) return -1;
        // 密码和校验密码是否相同
        if(StringUtils.equals(userPassword, checkPassword))return -1;
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count>0) return -1;
        // 2. 加密
        final String SALT = "xiaoliu";
        String encryptPassword = DigestUtil.md5Hex((SALT + userPassword).getBytes());
        // 3. 输入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if(!saveResult) return -1;
        return user.getId();
    }
}




