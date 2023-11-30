package buzz.yixiaobai.usercenter.exception;

import buzz.yixiaobai.usercenter.common.BaseResponse;
import buzz.yixiaobai.usercenter.common.ErrorCode;
import buzz.yixiaobai.usercenter.common.Utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author yixiaobai
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler<T> {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<T> businessExceptionHandler(BusinessException e) {
        log.error("businessException：" + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<T> runtimeExceptionHandler(RuntimeException e){
        log.error("runtimeException: ", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
     }
}
