package buzz.yixiaobai.usercenter.exception;

import buzz.yixiaobai.usercenter.common.ErrorCode;
import lombok.Getter;

/**
 * 自定义异常类
 *
 * @author yixiaobai
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }
}
