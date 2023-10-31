package buzz.yixiaobai.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 *
 * @author yixiaobai
 * @Date 2023年10月7日 22点46分
 * @TableName user
 */
@Data
@TableName(value = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 登录帐号
     */
    @TableField("userAccount")
    private String userAccount;

    /**
     * 姓名
     */
    @TableField("username")
    private String username;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 头像
     */
    @TableField("avatarUrl")
    private String avatarUrl;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 密码
     */
    @TableField("userPassword")
    private String userPassword;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
     */
    @TableField("userStatus")
    private Integer userStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-DD hh:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-DD hh:mm:ss")
    private Date updateTime;

    /**
     * 角色
     */
    @TableField("userRole")
    private Integer userRole;

    /**
     * 编号
     */
    @TableField("planetCode")
    private String planetCode;

    /**
     * 是否删除
     */
    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;

}