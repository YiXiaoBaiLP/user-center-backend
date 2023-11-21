package buzz.yixiaobai.usercenter.common.Utils;

import buzz.yixiaobai.usercenter.common.BaseResponse;

/**
 * 返回工具类
 *
 * @author yixiaobai
 */
public class ResultUtils {

    public static <T>BaseResponse<T> success(T data){
        return new BaseResponse<>(0, data, "ok");
    }
}
