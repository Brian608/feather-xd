package org.feather.xd.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.exception.BizException;
import org.feather.xd.model.LoginUser;

import java.util.Date;
import java.util.Objects;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.util
 * @className: JWTUtil
 * @author: feather
 * @description:
 * @since: 2024-08-12 15:34
 * @version: 1.0
 */
@Slf4j
public class JWTUtil {

    /**
     * token 过期时间，正常是7天
     */
    private static  final long EXPIRE_TIME = 60 * 60 * 1000 *24 *7;

    /**
     * 加密的秘钥
     */
    private static final String SECRET = "feather.net608";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "featherxd1024shop";

    /**
     * subject
     */
    private static final String SUBJECT = "featherxd";

    /**
     * description: 根据用户信息生成令牌
     * @param loginUser
     * @return {@link String}
     * @author: feather
     * @since: 2024-08-12 15:49
     **/
    public static String createToken(LoginUser loginUser){
        if (Objects.isNull(loginUser)){
            throw  new BizException("登录对象为空");
        }
       return TOKEN_PREFIX+ Jwts.builder().setSubject(SUBJECT)
                //payload
                .claim("head_img", loginUser.getHeadImg())
                .claim("id", loginUser.getId())
                .claim("name", loginUser.getName())
                .claim("mail", loginUser.getMail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    };
    /**
     * description: 校验token
     * @param token
     * @return {@link Claims}
     * @author: feather
     * @since: 2024-08-12 15:50
     **/

    public static Claims checkJWT(String token) {

        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

        } catch (Exception e) {
            log.info("jwt token解密失败");
            return null;
        }

    }



}
