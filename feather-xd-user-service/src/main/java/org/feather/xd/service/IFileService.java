package org.feather.xd.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.service
 * @className: IFileService
 * @author: feather
 * @description:
 * @since: 2024-08-10 19:22
 * @version: 1.0
 */
public interface IFileService {

    /**
     * description:  上传用户头像
     * @param file
     * @return {@link String}
     * @author: feather
     * @since: 2024-08-10 19:23
     **/
    String uploadUserImg(MultipartFile file);
}
