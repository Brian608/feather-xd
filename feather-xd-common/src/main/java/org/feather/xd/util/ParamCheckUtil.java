package org.feather.xd.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.feather.xd.constant.CommonConstant;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.exception.BizException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.util
 * @className: ParamCheckUtil
 * @author: feather
 * @description:
 * @since: 2024-08-16 16:00
 * @version: 1.0
 */
public class ParamCheckUtil {
    public static void checkObjectNonNull(Object o) throws BizException {
        if (Objects.isNull(o)) {
            throw new BizException(BizCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }
    public static void checkObjectNonNull(Object o,String msg) throws BizException {
        if (Objects.isNull(o)) {
           throw new BizException(CommonConstant.DEFAULT_ERROR_CODE,msg);
        }
    }

    public static void checkObjectNonNull(Object o, BizCodeEnum bizCodeEnum, Object... arguments) throws BizException {
        if (Objects.isNull(o)) {
            throw new BizException(bizCodeEnum.getCode(), bizCodeEnum.getMessage(),arguments);
        }
    }

    public static void checkObjectNull(Object o, BizCodeEnum bizCodeEnum, Object... arguments) throws BizException {
        if (Objects.nonNull(o)) {
            throw new BizException(bizCodeEnum.getCode(), bizCodeEnum.getMessage(),arguments);
        }
    }
    public static void checkObjectNull(Object o, String msg, Object... arguments) throws BizException {
        if (Objects.nonNull(o)) {
            throw new BizException(CommonConstant.DEFAULT_ERROR_CODE, msg,arguments);
        }
    }


    public static void checkStringNonEmpty(String s) throws BizException {
        if (StringUtils.isBlank(s)) {
            throw new BizException(BizCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }
    public static void checkStringNonEmpty(String s,String msg) throws BizException {
        if (StringUtils.isBlank(s)) {
            throw new BizException(CommonConstant.DEFAULT_ERROR_CODE,msg);
        }
    }

    public static void checkStringNonEmpty(String s,  BizCodeEnum bizCodeEnum, Object... arguments) throws BizException {
        if (StringUtils.isBlank(s)) {
            throw new BizException(bizCodeEnum.getCode(), bizCodeEnum.getMessage(),arguments);
        }
    }

    public static void checkStringNonEmpty(String s,  String msg, Object... arguments) throws BizException {
        if (StringUtils.isBlank(s)) {
            throw new BizException(CommonConstant.DEFAULT_ERROR_CODE,msg,arguments);
        }
    }

    public static void checkIntAllowableValues(Integer i, Set<Integer> allowableValues,  BizCodeEnum bizCodeEnum, Object... arguments) throws BizException {
        if (Objects.nonNull(i) && !allowableValues.contains(i)) {
            throw new BizException(bizCodeEnum.getCode(), bizCodeEnum.getMessage(),arguments);
        }
    }
    public static void checkIntAllowableValues(Integer i, Set<Integer> allowableValues,  String msg, Object... arguments) throws BizException {
        if (Objects.nonNull(i) && !allowableValues.contains(i)) {
            throw new BizException(CommonConstant.DEFAULT_ERROR_CODE,msg,arguments);
        }
    }

    public static void checkIntMin(Integer i, int min,  BizCodeEnum bizCodeEnum,Object... arguments) throws BizException {
        if (Objects.isNull(i) || i<min) {
            throw new BizException(bizCodeEnum.getCode(), bizCodeEnum.getMessage(),arguments);
        }
    }

    public static void checkLongMin(Long i, Long min, BizCodeEnum bizCodeEnum, Object... arguments) throws BizException {
        if (Objects.isNull(i) || i<min) {
            throw new BizException(bizCodeEnum.getCode(), bizCodeEnum.getMessage(),arguments);
        }
    }

    public static void checkCollectionNonEmpty(Collection<?> collection) throws BizException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(BizCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }

    public static void checkCollectionNonEmpty(Collection<?> collection,String msg) throws BizException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(CommonConstant.DEFAULT_ERROR_CODE,msg);
        }
    }

    public static void checkCollectionNonEmpty(Collection<?> collection,  BizCodeEnum bizCodeEnum, Object... arguments) throws BizException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(bizCodeEnum.getCode(), bizCodeEnum.getMessage(),arguments);
        }
    }

    public static void checkIntSetAllowableValues(Set<Integer> intSet, Set<Integer> allowableValues, BizCodeEnum bizCodeEnum,Object... arguments) throws BizException {
        if (CollectionUtils.isNotEmpty(intSet) && !diffSet(intSet,allowableValues).isEmpty()) {
            throw new BizException(bizCodeEnum.getCode(), bizCodeEnum.getMessage(),arguments);
        }
    }

    public static void checkSetMaxSize(Set<?> setParam, Integer maxSize, BizCodeEnum bizCodeEnum,Object... arguments) throws BizException {
        if (CollectionUtils.isNotEmpty(setParam) && setParam.size() > maxSize) {
            throw new BizException(bizCodeEnum.getCode(), bizCodeEnum.getMessage(),arguments);
        }
    }
    /**
     * 求set 差集合
     * @param set1
     * @param set2
     * @return
     */
    private static Set<Integer> diffSet(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> result = new HashSet<>();
        result.addAll(set1);
        result.removeAll(set2);
        return result;
    }
}
