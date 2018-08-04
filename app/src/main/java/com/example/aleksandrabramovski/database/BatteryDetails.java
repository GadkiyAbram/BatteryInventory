package com.example.aleksandrabramovski.database;


public class BatteryDetails {
    private int _id;
    private String boxNumber;
    private String serNum1;
    private String serNum2;
    private String serNum3;
    private String dateOfManuf;
    private String condition;
    private String contIn;
    private String comment;

    public BatteryDetails (int _id, String boxNumber, String serNum1, String serNum2,
                           String serNum3, String dateOfManuf,String condition,
                           String contIn, String comment){
        this._id = _id;
        this.boxNumber = boxNumber;
        this.serNum1 = serNum1;
        this.serNum2 = serNum2;
        this.serNum3 = serNum3;
        this.dateOfManuf = dateOfManuf;
        this.condition = condition;
        this.contIn = contIn;
        this.comment = comment;
    }

    public int get_id(){
        return this._id;
    }

    public void set_id(int _id){
        this._id = _id;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public String getSerNum1() {
        return serNum1;
    }

    public String getSerNum2() {
        return serNum2;
    }

    public String getSerNum3() {
        return serNum3;
    }

    public String getDateOfManuf() {
        return dateOfManuf;
    }

    public String getCondition() {
        return condition;
    }

    public String getContIn() {
        return contIn;
    }

    public String getComment() {
        return comment;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public void setSerNum1(String serNum1) {
        this.serNum1 = serNum1;
    }

    public void setSerNum2(String serNum2) {
        this.serNum2 = serNum2;
    }

    public void setSerNum3(String serNum3) {
        this.serNum3 = serNum3;
    }

    public void setDateOfManuf(String dateOfManuf) {
        this.dateOfManuf = dateOfManuf;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setContIn(String contIn) {
        this.contIn = contIn;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
