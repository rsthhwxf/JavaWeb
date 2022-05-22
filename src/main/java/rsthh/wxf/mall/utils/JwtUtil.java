package rsthh.wxf.mall.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {

    // 加密密文，私钥
    public static final String secret = "wxf";

    // 过期时间，单位毫秒
    public static final long expire = Consts.expire;


    /**
     * 通过UserId，创建一个token
     * @param userId
     * @return
     */
    public static String getToken(int userId){

        Algorithm algorithm = Algorithm.HMAC256(secret);//使用算法加密secret密钥
        JWTCreator.Builder builder = JWT.create();
        String token = builder.withClaim("userId", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + expire))
                .sign(algorithm);
        return token;

    }

    /**
     * 通过token获取userId
     * @param token
     * @return
     */
    public static int getIDByToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        int userId = decodedJWT.getClaim("userId").asInt();
        return userId;
    }

    public static Date getTimeByToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        Date date = decodedJWT.getClaim("exp").asDate();
        System.out.println(date);
        return date;
    }

    /**
     *验证密钥
     * @param token
     */
    public static void validate(String token){
        Algorithm algorithm =Algorithm.HMAC256(secret);//对密钥进行加密算法处理
        JWTVerifier verifier = JWT.require(algorithm).build();//创建一个jwt的verifier对象
        verifier.verify(token);//验证密钥和token（加密后的密钥部分）是否一致---sign
    }


    public static Integer getIDByRequest(HttpServletRequest httpServletRequest) throws Exception {
        String token = httpServletRequest.getHeader("token");
        if (token == null) return null;
        Integer userID = null;
        String temp = String.valueOf(JwtUtil.getIDByToken(token));
        if (temp == null) return null;
        userID = Integer.parseInt(temp);
        return userID;
    }



}