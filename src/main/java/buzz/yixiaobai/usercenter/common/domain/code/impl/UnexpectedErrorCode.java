package buzz.yixiaobai.usercenter.common.domain.code.impl;

import buzz.yixiaobai.usercenter.common.domain.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 未预期的错误码（即发生了不可能发生的事情，此类返回码应该高度重视）
 *
 * @Author yixiaobai
 * @Date 2023-10-30 16:38:49
 */
@Getter
@AllArgsConstructor
public enum UnexpectedErrorCode implements ErrorCode {

    BUSINESS_HANDING(20001, "现在业务繁忙，请稍后重试！"),
    PAY_ORDER_ID_ERROR(20002, "付款单id发生了异常，请联系技术人员排查"),

    ;

    private final int code;

    private final String msg;

    private final String level;

    UnexpectedErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.level = LEVEL_UNEXPECTED;
    }

}
