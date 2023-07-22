package com.example.crimereporting;

public class missingFire {
    String Id_number;
    String FullName;
    String location;
    String lastseen;
    String Year;
    String gender;
    String uri;

    public missingFire(){

    }

    public missingFire(String id_number, String fullName, String location, String lastseen, String year, String gender, String uri) {
        Id_number = id_number;
        FullName = fullName;
        this.location = location;
        this.lastseen = lastseen;
        Year = year;
        this.gender = gender;
        this.uri = uri;
    }

    public String getId_number() {
        return Id_number;
    }

    public void setId_number(String id_number) {
        Id_number = id_number;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLastseen() {
        return lastseen;
    }

    public void setLastseen(String lastseen) {
        this.lastseen = lastseen;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
