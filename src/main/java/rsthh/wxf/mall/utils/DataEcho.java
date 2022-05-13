package rsthh.wxf.mall.utils;

import java.util.HashMap;
import java.util.Map;

public class DataEcho {
    public static String NoAuthority(){
        Map returnData = new HashMap<>();
        returnData.put("status", 1);
        returnData.put("msg", "没有权限,请先登录!");
        returnData.put("data", "");
        return JsonUtil.toJson(returnData);
    }

    public static String NoDataSuccess(){
        Map returnData = new HashMap<>();
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", "");
        return JsonUtil.toJson(returnData);
    }
}
