package com.dbmsjack.dbms.objects;

/**
 * Created by root on 1/11/17.
 */

public class ViewListItem {

    public String name;
    public String reg;
    public String school;
    public String cgpa;

    public ViewListItem(){

    }

    public ViewListItem(String name,
                        String reg,
                        String school,
                        String cgpa){

        this.name = name;
        this.reg = reg;
        this.school = school;
        this.cgpa = cgpa;

    }

    public String getCgpa() {
        return cgpa;
    }

    public String getName() {
        return name;
    }

    public String getReg() {
        return reg;
    }

    public String getSchool() {
        return school;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
