package cn.mxlog.sscloud.util;

import cn.mxlog.sscloud.model.Server;
import cn.mxlog.sscloud.model.User;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by F.Du on 2017/10/16.
 */
public class Shadowsocket {

    protected final static Logger logger = LoggerFactory.getLogger(Shadowsocket.class
            .getClass());

    /**
     * 获取服务器端口状态, 流量
     *
     * @param node
     * @return
     */
    public static List<Map<String, Object>> ping(Server node) {
        String resp = Util.udp(node.getIp(), node.getPort(), "ping");

        System.out.println(resp);
        if(!StringUtils.isEmpty(resp)) {
            String data = resp.replace("stat:", "").trim();

            try {
                JSONObject json = new JSONObject(data);

                List<Map<String, Object>> list = new ArrayList<>();

                Iterator keys = json.keys();
                while(keys.hasNext()){
                    String key = keys.next().toString();
                    Map<String, Object> map = new HashMap<>();
                    map.put("port", key);
                    map.put("traffic", json.get(key));

                    list.add(map);
                }

                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     * 添加用户端口
     *
     * @param node
     * @param user
     * @return
     */
    public static boolean add(Server node, User user) {
        if(user == null || StringUtils.isEmpty(user.getPort()) || StringUtils.isEmpty(user.getPortPwd())){
            logger.error("添加用户端口失败, 端口号或密码为空."+ user.toString());
            return false;
        }
        String params = "add: ";
        JSONObject json = new JSONObject();
        try {
            json.put("server_port", Integer.parseInt(user.getPort()));
            json.put("password", user.getPortPwd());

            params += json.toString();
            System.out.println(params);
            String resp = Util.udp(node.getIp(), node.getPort(), params);
            if ("ok".equalsIgnoreCase(resp)) {
                return true;
            }
        } catch (JSONException e) {
            logger.error("添加用户端口失败."+ user.toString());
            e.printStackTrace();
        }


        return false;
    }

    /**
     * 删除用户端口
     *
     * @param node
     * @param user
     * @return
     */
    public static boolean remove(Server node, User user) {
        String params = "remove: ";

        JSONObject json = new JSONObject();

        try {
            json.put("server_port", user.getPort());
            params += json.toString();
            String resp = Util.udp(node.getIp(), node.getPort(), params);
            if ("ok".equalsIgnoreCase(resp)) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取服务器已设置端口,及密码
     *
     * @param server
     * @return
     */
    public static JSONArray list(Server server) {
        String params = "list";
        String resp = Util.udp(server.getIp(), server.getPort(), params);

        try{
            JSONArray array = new JSONArray(resp);

            return array;
        }catch (JSONException e){
            logger.error("获取服务器已设置端口错误失败! 返回值: "+ resp);
            e.printStackTrace();
        }

        return null;
    }

    public static void reset(Server server, User user){
        String params = "stat: ";
        JSONObject json = new JSONObject();

        try {
            json.put(user.getPort(), 0);
            params += json.toString();

            Util.udp(server.getIp(), server.getPort(), params);

            //System.out.println(resp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
