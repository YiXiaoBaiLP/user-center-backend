package buzz.yixiaobai.usercenter.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author yixiaobai
 * @date 2023/10/07 23:49:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest implements Serializable {

    /**
     * 用户登录名
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 确认密码
     */
    private String checkPassword;

    /**
     * 编号
     */
    private String planetCode;
}
