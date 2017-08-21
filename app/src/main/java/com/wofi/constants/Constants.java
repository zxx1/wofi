package com.wofi.constants;


/**
 * Created by shaohao on 2017/8/2.
 */

public class Constants {
    /**
     * 服务器地址
     */
    //public final static String URL="http://192.168.32.73:8080";
    public final static String URL="http://59.110.238.9:8080/bicycleSharingServer_war";


    /**
     * API
     */
    public final static String LOGIN_URL = URL+"/api-user-register/";   //userName
    public final static String RECHAGEBILL_URL =URL+"/api-user-queryRecharge/"; //userName
    public final static String RECHAGE_URL =URL+"/api-user-recharge/";  //{rechargeAmount}/{username}
    public final static String JOURNEY_URL =URL+"/api-user-queryBorrow/"; //userName
    public final static String USERINFO_URL =URL+"/api-user-userInfo/";   //userName
    public final static String CASH_URL =URL+"/api-user-submitUserCash/"; //userName
    public final static String GET_CASH_URL =URL+"/api-user-getUserCash/";//userName
    public final static String RETURN_CASH_URL =URL+"/api-user-returnUserCash/";//userName
    public final static String USERFEEDBACK_URL =URL+"/api-userFeedback-add/"; //反馈标题+内容+userName+bicycleId

    public final static String BICYCLE_URL =URL+"/api-bicycle-queryByLocation/"; //bicycleCurrentX（经度)+bicycleCurrentY（纬度）
    public final static String BORROWBICYCLE_URL=URL+"/api-borrow-borrowBicycle/";//{bicycleId}/{userName}
    public final static String RETURNBICYCLE_URL=URL+"/api-borrow-returnBicycle/";//{bicycleId}/{userName}/ex/ey/cost/end



}
