package cn.mxlog.sscloud.base;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by F.Du on 2017/9/8.
 */
public class BaseService extends BaseActor {



    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    public static void copyProperties(Object src, Object target, boolean ignoreNull) {
        if (ignoreNull) {
            BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
        } else {
            BeanUtils.copyProperties(src, target);
        }
    }

}
