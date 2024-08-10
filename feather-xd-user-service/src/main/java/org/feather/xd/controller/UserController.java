package org.feather.xd.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.service.IFileService;
import org.feather.xd.util.JsonData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
    public JsonData uploadUserImg(
            @ApiParam(value = "文件上传",required = true)
            @RequestPart("file") MultipartFile file){

        String result = fileService.uploadUserImg(file);
        return result!=null? JsonData.buildSuccess(result):JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);
    }

}

