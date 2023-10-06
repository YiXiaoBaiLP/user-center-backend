package buzz.yixiaobai.usercenter.service.impl;

import buzz.yixiaobai.usercenter.mapper.UserMapper;
import buzz.yixiaobai.usercenter.model.domain.User;
import buzz.yixiaobai.usercenter.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author yixiaobai
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-09-24 22:47:50
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword))
            if (userAccount == null || userPassword == null || checkPassword == null) {

            }
        return 0;
    }
}




