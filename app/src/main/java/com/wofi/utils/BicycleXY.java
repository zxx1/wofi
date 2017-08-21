package com.wofi.utils;

/**
 * Created by shaohao on 2017/8/9.
 */

public class BicycleXY {
    private String bicycleId;
    private double bicycleCurrentX;
    private double bicycleCurrentY;
    private String bicycleLastTime;
    private Integer bicycleStatement;

    public double getBicycleCurrentX()
    {
        return bicycleCurrentX;
    }

    public void setBicycleCurrentX(double bicycleCurrentX) {
        this.bicycleCurrentX = bicycleCurrentX;
    }

    public double getBicycleCurrentY()
    {
        return bicycleCurrentY;
    }

    public void setBicycleCurrentY(double bicycleCurrentY) {
        this.bicycleCurrentY = bicycleCurrentY;
    }

    public String getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(String bicycleId) {
        this.bicycleId = bicycleId;
    }

    public String getBicycleLastTime() {
        return bicycleLastTime;
    }

    public void setBicycleLastTime(String bicycleLastTime) {
        this.bicycleLastTime = bicycleLastTime;
    }

    public Integer getBicycleStatement() {
        return bicycleStatement;
    }

    public void setBicycleStatement(Integer bicycleStatement) {
        this.bicycleStatement = bicycleStatement;
    }
}
