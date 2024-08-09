package org.feather.xd.component;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.service
 * @className: IMailService
 * @author: feather
 * @description:
 * @since: 2024-08-09 21:22
 * @version: 1.0
 */
public interface IMailService {

    /**
     * description: 发送邮件
     * @param to  收件人
     * @param subject  主题
     * @param content 内容
     * @author: feather
     * @since: 2024-08-09 21:23
     **/
    void sendMail(String to,String subject,String content);
}
