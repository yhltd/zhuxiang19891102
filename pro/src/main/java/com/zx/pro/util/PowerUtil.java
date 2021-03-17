package com.zx.pro.util;

import com.zx.pro.entity.UserPower;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author wanghui
 * @date 2021/1/23 15:07
 */
public class PowerUtil{

    private List<UserPower> powerList;

    private PowerUtil(){}

    private static PowerUtil powerUtil;

    public static PowerUtil getPowerUtil(HttpSession session){
        if(StringUtils.isNull(powerUtil)){
            powerUtil = new PowerUtil();
        }
        powerUtil.powerList = SessionUtil.getPower(session);
        return powerUtil;
    }

    public boolean isAdd(String viewName) {
        try{
            for(UserPower userPower : powerList){
                if(userPower.getViewName().equals(viewName)){
                    return userPower.getAdds() > 0;
                }
            }
        }catch (Exception e){
            return true;
        }
        return true;
        //return viewName != "员工管理";
    }

    public boolean isDelete(String viewName) {
        try{
            for(UserPower userPower : powerList){
                if(userPower.getViewName().equals(viewName)){
                    return userPower.getDeletes() > 0;
                }
            }
        }catch (Exception e){
            return true;
        }
        return true;
        //return viewName != "员工管理";
    }

    public boolean isUpdate(String viewName) {
        try{
            for(UserPower userPower : powerList){
                if(userPower.getViewName().equals(viewName)){
                    return userPower.getUpdates() > 0;
                }
            }
        }catch (Exception e){
            return true;
        }
        return true;
        //return viewName != "员工管理";
    }

    public boolean isSelect(String viewName) {
        try{
            for(UserPower userPower : powerList){
                if(userPower.getViewName().equals(viewName)){
                    return userPower.getSelects() > 0;
                }
            }
        }catch (Exception e){
            return true;
        }
        return true;
        //return viewName != "员工管理";
    }
}
