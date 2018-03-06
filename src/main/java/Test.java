import cn.mxlog.sscloud.model.Server;
import cn.mxlog.sscloud.model.User;
import cn.mxlog.sscloud.util.Shadowsocket;

/**
 * Created by F.Du on 2017/10/16.
 */
public class Test {

    public static  void main(String[] agrs){

        Server server = new Server();
        server.setIp("192.168.0.124");
        server.setPort("9999");

        User user = new User();
        user.setPort("8382");
        user.setPortPwd("243243");
        boolean flag = Shadowsocket.add(server, user);

        System.out.println(flag ? "添加成功":"添加失败");
        //Shadowsocket.list(server);
        Shadowsocket.ping(server);

        Shadowsocket.reset(server, user);

        Shadowsocket.ping(server);



//        Shadowsocket.ping(server);
//        Shadowsocket.ping(server);
//        Shadowsocket.ping(server);

//        Date endDate = DateUtils.addMonths(Util.getCurrentDate(), 1);
//        endDate = DateUtils.setDays(endDate, 1);
//
//        System.out.println(endDate.toString());
    }

}
