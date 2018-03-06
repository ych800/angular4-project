
package cn.mxlog.sscloud.controller.params;

import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.User;

/*
 * UserCom
 *
 * @author F.Du Created on 18 十月 2017
 */
public class UserCom {
	private User user;
    private Pager pager;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

}
