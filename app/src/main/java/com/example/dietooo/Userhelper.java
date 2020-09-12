package com.example.dietooo;

public class Userhelper {

 String Email, Username, Password,Gender,Profile;
 Double Height,Weight,BMI;
 Integer Workcount,Dietcount;


    public Userhelper(String Email, String Username, String Password, Double Height, Double Weight, Double BMI, String Gender,String Profile, Integer Workcount,Integer Dietcount) {
        this.Email = Email;
        this.Username = Username;
        this.Password = Password;
        this.Height = Height;
        this.Weight = Weight;
        this.BMI = BMI;
        this.Gender = Gender;
        this.Profile = Profile;
        this.Workcount = Workcount;
        this.Dietcount = Dietcount;
    }

    public Userhelper() {

    }

    public String getEmail() {
        return Email;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public Double getHeight() {
        return Height;
    }

    public Double getWeight() {
        return Weight;
    }

    public Double getBMI() {
        return BMI;
    }

    public String getGender() {
        return Gender;
    }

    public String getProfile() {
        return Profile;
    }

    public Integer getWorkcount() {
        return Workcount;
    }

    public Integer getDietcount() {
        return Dietcount;
    }
}
