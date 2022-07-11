package rsthh.wxf.mall.utils;

import rsthh.wxf.mall.po.User;

public class ThreadLocalUtil {
    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void setUser(User user){
        threadLocal.set(user);
    }

    public static User getUser(){

        return threadLocal.get();
    }

    public static void clear(){
        threadLocal.remove();
    }
}
