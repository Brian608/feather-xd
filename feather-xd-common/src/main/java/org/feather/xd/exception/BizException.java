package org.feather.xd.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.feather.xd.constant.CommonConstant;
import org.feather.xd.enums.BizCodeEnum;

import java.text.MessageFormat;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.exception
 * @className: BizException
 * @author: feather
 * @description:
 * @since: 2024-08-09 16:38
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends  RuntimeException{


    private int code;
    private String msg;

    public BizException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(BizCodeEnum bizCodeEnum){
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }

    public BizException(int code, String msg, Object... arguments) {
        super(MessageFormat.format(msg, arguments));
        this.code = code;
        this.msg = MessageFormat.format(msg, arguments);
    }

    public BizException(String msg){
        this.code = CommonConstant.ERROR_CODE;
        this.msg = msg;
    }



}
