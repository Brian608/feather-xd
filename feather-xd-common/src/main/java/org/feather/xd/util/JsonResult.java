package org.feather.xd.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.feather.xd.enums.BizCodeEnum;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.util
 * @className: JsonData
 * @author: feather
 * @description: 统一返回结构体
 * @since: 2024-08-09 16:30
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> implements   java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private static final String SUCCESS_MSG = "success";

    private static final int SUCCESS_CODE = 200;

    private static final int ERROR_CODE = -1;
    /**
     * 状态码 0 表示成功
     */

    private Integer code;
    /**
     * 数据
     */
    private T data;
    /**
     * 描述
     */
    private String msg;



    /**
     * 成功，不传入数据
     * @return
     */
    public static <T> JsonResult<T> buildSuccess() {
        return new JsonResult<T>(SUCCESS_CODE, null, SUCCESS_MSG);
    }

    /**
     *  成功，传入数据
     * @param data
     * @return
     */
    public static <T> JsonResult<T> buildSuccess(T data) {
        return new JsonResult<T>(SUCCESS_CODE, data, SUCCESS_MSG);
    }

    /**
     * 失败，传入描述信息
     * @param msg
     * @return
     */
    public static  <T> JsonResult<T> buildError(String msg) {
        return new JsonResult<T>(ERROR_CODE, null, msg);
    }
    /**
     * 失败，传入描述code 和错误信息
     * @param msg
     * @return
     */
    public static  <T> JsonResult<T> buildError(int code,String msg) {
        return new JsonResult<T>(code, null, msg);
    }



    /**
     * 自定义状态码和错误信息
     * @param code
     * @param msg
     * @return
     */
    public static <T> JsonResult<T> buildCodeAndMsg(int code, String msg) {
        return new JsonResult<T>(code, null, msg);
    }

    /**
     * 传入枚举，返回信息
     * @param codeEnum
     * @return
     */
    public static <T> JsonResult<T> buildResult(BizCodeEnum codeEnum){
        return JsonResult.buildCodeAndMsg(codeEnum.getCode(),codeEnum.getMessage());
    }
}