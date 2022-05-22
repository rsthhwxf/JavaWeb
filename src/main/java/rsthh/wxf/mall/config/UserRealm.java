package rsthh.wxf.mall.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import rsthh.wxf.mall.po.Token;
import rsthh.wxf.mall.po.User;
import rsthh.wxf.mall.service.UserService;
import rsthh.wxf.mall.utils.JwtUtil;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Token;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();//拿到user对象
        //设置当前用户的角色
        info.addStringPermission(String.valueOf(user.getUserType()));
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthorizationInfo");

        Token token2 =  (Token)token;
        System.out.println(token2.getToken());
        System.out.println(JwtUtil.getIDByToken(token2.getToken()));
        User user = userService.getById(JwtUtil.getIDByToken(token2.getToken()));
        System.out.println("user"+user);
        if (user == null) {//没有这个人
            throw new AuthenticationException();
        }

        // 密码认证，shiro做
        return new SimpleAuthenticationInfo(token.getPrincipal(),token.getCredentials(),"");
    }
}


