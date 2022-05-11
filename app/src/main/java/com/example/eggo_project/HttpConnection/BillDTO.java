package com.example.eggo_project.HttpConnection;

public class BillDTO {

    //필요한 필드값
    private String UserId;
    private String Date;
    private int TotalFee;
    private int WaterFee;
    private int WaterUsage;
    private int ElectricityFee;
    private int ElectricityUsage;

    public BillDTO(String UserId, String Date, int TotalFee,int WaterFee,int WaterUsage,int ElectricityFee, int ElectricityUsage) {
        this.UserId = UserId;
        this.Date = Date;
        this.TotalFee =TotalFee;
        this.WaterFee=WaterFee;
        this.WaterUsage=WaterUsage;
        this.ElectricityFee=ElectricityFee;
        this.ElectricityUsage=ElectricityUsage;
    }

    //출력
    public String getUserId() {
        return UserId;
    }
    public String getDate() {
        return Date;
    }
    public int getTotalFee() {
        return TotalFee;
    }
    public int getWaterFee() {
        return WaterFee;
    }
    public int getWaterUsage() {
        return WaterUsage;
    }
    public int getElectricityFee() {
        return ElectricityFee;
    }
    public int getElectricityUsage() {
        return ElectricityUsage;
    }

    //입력
    public void setUserId(String UserId) {
        this.UserId = UserId;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }
    public void setTotalFee(int TotalFee) {
        this.TotalFee = TotalFee;
    }
    public void setWaterFee(int WaterFee) {
        this.WaterFee=WaterFee;
    }
    public void setWaterUsage(int WaterUsage) {
        this.WaterUsage=WaterUsage;
    }
    public void setElectricityFee(int ElectricityFee) {
        this.ElectricityFee=ElectricityFee;
    }
    public void setElectricityUsage(int ElectricityUsage) {
        this.ElectricityUsage=ElectricityUsage;
    }
}