package com.example.motorfreerider;

import android.accounts.Account;

public class user {

    private String Identity;
    private String ID;
    private String Email;
    private android.accounts.Account Account;
    private String LastName;
    private String FirstName;
    private String Sex;
    private String MobileNumber, CarryPlace, FinishPlace, Description, CarType, CarNumber;
    private String Score, Count, Now;
    private String CarryPlaceLongitude, CarryPlaceLatitude, FinishPlaceLongitude, FinishPlaceLatitude, time, Start, OwnerID, PassengerID;

    public user() {
        this.Count = "0";
        this.Score = "0";
        this.Now = "NoCar";
    }

    public String getID() {
        return ID;
    }

    public String getSex() {
        return Sex;
    }

    public String getNow() {
        return Now;
    }

    public String getCarryPlace() {
        return CarryPlace;
    }

    public String getFinishPlace() {
        return FinishPlace;
    }

    public String getScore() {
        return Score;
    }

    public String getCount() {
        return Count;
    }

    public String getIdentity() {
        return Identity;
    }

    public String getEmail() {
        return Email;
    }

    public String getAccount() {
        return Account.type;
    }

    public String getLastName() {
        return LastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getCarType() {
        return CarType;
    }

    public String getCarNumber() {
        return CarNumber;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getCarryPlaceLongitude() {
        return CarryPlaceLongitude;
    }

    public String getCarryPlaceLatitude() {
        return CarryPlaceLatitude;
    }

    public String getFinishPlaceLongitude() {
        return FinishPlaceLongitude;
    }

    public String getFinishPlaceLatitude() {
        return FinishPlaceLatitude;
    }

    public String getDescription() {
        return Description;
    }

    public String getPassengerID() {
        return PassengerID;
    }

    public String getTime() {
        return time;
    }

    public void setCarType(String CarType) {
        this.CarType = CarType;
    }

    public void setCarNumber(String CarNumber) {
        this.CarNumber = CarNumber;
    }

    public String getOwnerID() {
        return OwnerID;
    }

    public String getStart() {
        return Start;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public void setPassengerID(String PassengerID) {
        this.PassengerID = PassengerID;
    }

    public void setStart(String Start) {
        this.Start = Start;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setScore(String Score) {
        this.Score = Score;
    }

    public void setNow(String Now) {
        this.Now = Now;
    }

    public void setCount(String Count) {
        this.Count = Count;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public void setMobileNumber(String MobileNumber) {
        this.MobileNumber = MobileNumber;
    }

    public void setIdentity(String Identity) {
        this.Identity = Identity;
    }

    public void setCarryPlace(String CarryPlace) {
        this.CarryPlace = CarryPlace;
    }

    public void setFinishPlace(String FinishPlace) {
        this.FinishPlace = FinishPlace;
    }

    public void setCarryPlaceLongitude(String CarryPlaceLongitude) {
        this.CarryPlaceLongitude = CarryPlaceLongitude;
    }

    public void setCarryPlaceLatitude(String CarryPlaceLatitude) {
        this.CarryPlaceLatitude = CarryPlaceLatitude;
    }

    public void setFinishPlaceLongitude(String FinishPlaceLongitude) {
        this.FinishPlaceLongitude = FinishPlaceLongitude;
    }

    public void setFinishPlaceLatitude(String FinishPlaceLatitude) {
        this.FinishPlaceLatitude = FinishPlaceLatitude;
    }

    public void setOwnerID(String OwnerID) {
        this.OwnerID = OwnerID;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}