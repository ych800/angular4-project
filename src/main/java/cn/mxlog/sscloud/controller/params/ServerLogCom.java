
package cn.mxlog.sscloud.controller.params;

import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.ServerLog;

/*
 * ServerLogCom
 *
 * @author F.Du Created on 18 十月 2017
 */
public class ServerLogCom {
	private ServerLog serverlog;
    private Pager pager;

    public ServerLog getServerLog() {
        return serverlog;
    }

    public void setServerLog(ServerLog serverlog) {
        this.serverlog = serverlog;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

}
