
package cn.mxlog.sscloud.controller.params;

import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.Combo;
import cn.mxlog.sscloud.model.Server;
import cn.mxlog.sscloud.model.UserOrder;

/*
 * UserOrderCom
 *
 * @author F.Du Created on 18 十月 2017
 */
public class UserOrderCom {
	private UserOrder userorder;
    private Pager pager;
    private Server server;
    private Combo combo;

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }

    public UserOrder getUserOrder() {
        return userorder;
    }

    public void setUserOrder(UserOrder userorder) {
        this.userorder = userorder;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
