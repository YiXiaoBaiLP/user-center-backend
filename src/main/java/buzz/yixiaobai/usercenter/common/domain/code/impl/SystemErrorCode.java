package buzz.yixiaobai.usercenter.common.domain.code.impl;

import buzz.yixiaobai.usercenter.common.domain.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统错误状态码（此类返回码应该高度重视）
 *
 * @Author yixiaobai
 * @Date 2023-10-30 16:38:35
 */
@Getter
@AllArgsConstructor
public enum SystemErrorCode implements ErrorCode {

    SYSTEM_ERROR(10001, "系统似乎出现了点小问题"),

    ;

    private final int code;

    private final String msg;

    private final String level;

    SystemErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.level = LEVEL_SYSTEM;
    }

}
