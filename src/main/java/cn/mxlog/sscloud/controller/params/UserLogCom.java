
package cn.mxlog.sscloud.controller.params;

import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.UserLog;

/*
 * UserLogCom
 *
 * @author F.Du Created on 18 十月 2017
 */
public class UserLogCom {
	private UserLog userlog;
    private Pager pager;

    public UserLog getUserLog() {
        return userlog;
    }

    public void setUserLog(UserLog userlog) {
        this.userlog = userlog;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

}
