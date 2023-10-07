package buzz.yixiaobai.usercenter.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户登录 request
 *
 * @author yixiaobai
 * @date 2023/10/08 00:11:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest implements Serializable {

    /**
     * 用户名称
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;
}
