package org.feather.xd.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.request.UserLoginRequest;
import org.feather.xd.request.UserRegisterRequest;
import org.feather.xd.service.IFileService;
import org.feather.xd.service.IUserService;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.LoginInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feather
 * @since 2024-08-04
 */
@RequiredArgsConstructor
@Api(tags = "用户模块")
@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    private final IFileService fileService;

    private final IUserService userService;


    /**
     * 上传用户头像
     *
     * 默认最大是1M,超过则报错
     *
     * @param file 文件
     * @return
     */
    @ApiOperation("用户头像上传")
    @PostMapping(value = "/upload")
    public JsonResult<String> uploadUserImg(
            @ApiParam(value = "文件上传",required = true)
            @RequestPart("file") MultipartFile file){

        String result = fileService.uploadUserImg(file);
        return result!=null? JsonResult.buildSuccess(result): JsonResult.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);
    }
    @ApiOperation(value = "注册用户",httpMethod = "POST", produces = "application/json")
    @PostMapping("/register")
    public JsonResult<Boolean> register(@ApiParam("用户注册对象") @RequestBody @Validated UserRegisterRequest registerRequest){
        return JsonResult.buildSuccess( userService.register(registerRequest));
    }

    @ApiOperation(value = "登录",httpMethod = "POST", produces = "application/json")
    @PostMapping("/login")
    public JsonResult<LoginInfo> register(@ApiParam("用户登录对象") @RequestBody @Validated UserLoginRequest userLoginRequest){
        return JsonResult.buildSuccess( userService.login(userLoginRequest));
    }

    //    刷新token的方案
//    @PostMapping("refresh_token")
//    public JsonResult getRefreshToken(Map<String,Object> param){
//
//        //先去redis,找refresh_token是否存在
//        //refresh_token存在，解密accessToken
//        //重新调用JWTUtil.geneJsonWebToken() 生成accessToken
//        //重新生成refresh_token，并存储redis，设置30天过期时间
//        //返回给前端
//        return null;
//    }


}

