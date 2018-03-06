
package cn.mxlog.sscloud.controller.params;

import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.Server;

/*
 * ServerCom
 *
 * @author F.Du Created on 18 十月 2017
 */
public class ServerCom {
	private Server server;
    private Pager pager;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

}
