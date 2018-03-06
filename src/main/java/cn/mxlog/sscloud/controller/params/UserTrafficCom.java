
package cn.mxlog.sscloud.controller.params;

import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.model.UserTraffic;

/*
 * UserTrafficCom
 *
 * @author F.Du Created on 18 十月 2017
 */
public class UserTrafficCom {
	private UserTraffic usertraffic;
    private Pager pager;

    public UserTraffic getUserTraffic() {
        return usertraffic;
    }

    public void setUserTraffic(UserTraffic usertraffic) {
        this.usertraffic = usertraffic;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

}
