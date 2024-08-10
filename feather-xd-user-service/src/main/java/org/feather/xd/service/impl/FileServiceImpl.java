package org.feather.xd.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.config.OSSConfig;
import org.feather.xd.service.IFileService;
import org.feather.xd.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.service.impl
 * @className: FileServiceImpl
 * @author: feather
 * @description:
 * @since: 2024-08-10 19:24
 * @version: 1.0
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class FileServiceImpl  implements IFileService {

    private final OSSConfig ossConfig;
    @Override
    public String uploadUserImg(MultipartFile file) {
        //获取相关配置
        String bucketname = ossConfig.getBucketname();
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();

        //创建OSS对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //原始文件名
        String originalFilename = file.getOriginalFilename();

        //JDK8的日期格式
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        //拼装路径,oss上存储的路径  user/2024/08/01/sdfdsafsdfdsf.jpg
        String folder = dtf.format(ldt);
        String fileName = CommonUtil.generateUUID();
        assert originalFilename != null;
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 在OSS上的bucket下创建 user 这个文件夹
        String newFileName = "user/"+folder+"/"+fileName+ extension;

        try {
            PutObjectResult putObjectResult = ossClient.putObject(bucketname,newFileName,file.getInputStream());
            //拼装返回路径
            if(putObjectResult != null){
              return "https://"+bucketname+"."+endpoint+"/"+newFileName;
            }

        } catch (IOException e) {
            log.error("oss文件上传失败:[{}]",e);
        } finally {
            //oss关闭服务，不然会造成OOM
            ossClient.shutdown();
        }

        return null;
    }
}
