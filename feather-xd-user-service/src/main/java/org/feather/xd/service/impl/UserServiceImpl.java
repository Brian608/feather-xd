package org.feather.xd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.enums.SendCodeEnum;
import org.feather.xd.exception.BizException;
import org.feather.xd.mapper.UserMapper;
import org.feather.xd.model.LoginUser;
import org.feather.xd.model.UserDO;
import org.feather.xd.request.UserLoginRequest;
import org.feather.xd.request.UserRegisterRequest;
import org.feather.xd.service.INotifyService;
import org.feather.xd.service.IUserService;
import org.feather.xd.util.CommonUtil;
import org.feather.xd.util.JWTUtil;
import org.feather.xd.vo.LoginInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-08-04
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements IUserService {
    private final INotifyService notifyService;


    /**
     * 用户注册
     * * 邮箱验证码验证
     * * 密码加密（TODO）
     * * 账号唯一性检查(TODO)
     * * 插入数据库
     * * 新注册用户福利发放(TODO)
     *
     * @param registerRequest
     * @return
     */
    @Override
    public Boolean register(UserRegisterRequest registerRequest) {

        boolean checkCode  = notifyService.checkCode(SendCodeEnum.USER_REGISTER,registerRequest.getMail(),registerRequest.getCode());
        if (!checkCode){
            throw  new BizException(BizCodeEnum.CODE_ERROR);
        }
        if (!checkUnique(registerRequest.getMail())){
            throw  new BizException(BizCodeEnum.ACCOUNT_REPEAT);
        }
        UserDO userDO=new UserDO();
        BeanUtils.copyProperties(registerRequest,userDO);
        //设置密码 生成秘钥 盐
        userDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8));

        //密码+盐处理
        String cryptPwd = Md5Crypt.md5Crypt(registerRequest.getPwd().getBytes(), userDO.getSecret());
        userDO.setPwd(cryptPwd);
        //TODO 账户唯一性检查
        boolean result = this.save(userDO);
        log.info("注册用户result:[{}],用户信息:[{}]", result,userDO);
        //TODO 新用户注册 初始化信息，发送福利等
        userRegisterInitTask(userDO);
        return result;

    }

    /**
     * 验证邮箱是否已经注册
     * @param mail
     * @return
     */
    private boolean checkUnique(String mail) {
       return this.list(new LambdaQueryWrapper<UserDO>().eq(UserDO::getMail, mail)).isEmpty();
    }

    /**
     * 用户注册，初始化福利信息 TODO
     *
     * @param userDO
     */
    private void userRegisterInitTask(UserDO userDO) {


    }

    @Override
    public LoginInfo login(UserLoginRequest userLoginRequest) {
        //检查账户是否已注册
        List<UserDO> userDOList = this.list(new LambdaQueryWrapper<UserDO>().eq(UserDO::getMail, userLoginRequest.getMail()));
        if (userDOList.isEmpty()){
            throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        }
        UserDO userDO = userDOList.get(0);
        String cryptPwd = Md5Crypt.md5Crypt(userLoginRequest.getPwd().getBytes(), userDO.getSecret());
        if (cryptPwd.equals(userDO.getPwd())){

            LoginInfo loginInfo=new LoginInfo();
            BeanUtils.copyProperties(userDO,loginInfo);
            LoginUser loginUser = new LoginUser();
            BeanUtils.copyProperties(userDO,loginUser);
            String token = JWTUtil.createToken(loginUser);
            //登录成功
            log.info("登录成功:[{}]，token:[{}]",userDO.getMail(),token);
            loginInfo.setToken(token);
            return loginInfo;
        }else {
            throw new BizException(BizCodeEnum.ACCOUNT_PWD_ERROR);
        }
    }
}
