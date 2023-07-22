package com.example.crimereporting;

public class stationfirebase {
    String incharge_Id;
    String station_name;
    String incharge_name;
    String Location;
    String Station_id;
    String incharge_Phone_no;

    public stationfirebase(String incharge_Id, String station_name, String incharge_name, String location, String station_id, String incharge_Phone_no) {
        this.incharge_Id = incharge_Id;
        this.station_name = station_name;
        this.incharge_name = incharge_name;
        Location = location;
        Station_id = station_id;
        this.incharge_Phone_no = incharge_Phone_no;
    }

    public String getIncharge_Id() {
        return incharge_Id;
    }

    public void setIncharge_Id(String incharge_Id) {
        this.incharge_Id = incharge_Id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getIncharge_name() {
        return incharge_name;
    }

    public void setIncharge_name(String incharge_name) {
        this.incharge_name = incharge_name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getStation_id() {
        return Station_id;
    }

    public void setStation_id(String station_id) {
        Station_id = station_id;
    }

    public String getIncharge_Phone_no() {
        return incharge_Phone_no;
    }

    public void setIncharge_Phone_no(String incharge_Phone_no) {
        this.incharge_Phone_no = incharge_Phone_no;
    }
}
