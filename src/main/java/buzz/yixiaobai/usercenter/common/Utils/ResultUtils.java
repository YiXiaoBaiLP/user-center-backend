package buzz.yixiaobai.usercenter.common.Utils;

import buzz.yixiaobai.usercenter.common.BaseResponse;
import buzz.yixiaobai.usercenter.common.ErrorCode;

/**
 * 返回工具类
 *
 * @author yixiaobai
 */
public class ResultUtils {

    /**
     * 成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(20000, data, "ok", "");
    }

    /**
     * 失败
     * @param errorCode
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }
}
