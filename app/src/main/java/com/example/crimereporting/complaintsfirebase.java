package com.example.crimereporting;

public class complaintsfirebase {



        String Id_number;
        String FullName;
        String Email;
        String DOC;
        String TOC;
        String gender;
        String OBnumber;
        String Videouri;
        String Status;
        String phone;
        String assignto;

    public complaintsfirebase() {

    }

    public complaintsfirebase(String id_number, String fullName, String email, String DOC, String TOC, String gender,String OBnumber,String videouri,String Status,String phone,String assignto) {
        Id_number = id_number;
        FullName = fullName;
        Email = email;
        this.DOC = DOC;
        this.TOC = TOC;
        this.gender = gender;
        this.OBnumber=OBnumber;
        this.Videouri=videouri;
        this.Status=Status;
        this.phone=phone;
        this.assignto=assignto;


    }

    public String getAssignto() {
        return assignto;
    }

    public void setAssignto(String assignto) {
        this.assignto = assignto;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDOC() {
        return DOC;
    }

    public void setDOC(String DOC) {
        this.DOC = DOC;
    }

    public String getTOC() {
        return TOC;
    }

    public void setTOC(String TOC) {
        this.TOC = TOC;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getOBnumber() {
        return OBnumber;
    }

    public String getVideouri() {
        return Videouri;
    }

    public void setVideouri(String videouri) {
        Videouri = videouri;
    }

    public void setOBnumber(String OBnumber) {
        this.OBnumber = OBnumber;
    }
}

