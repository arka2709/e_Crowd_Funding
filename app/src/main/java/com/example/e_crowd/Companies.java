package com.example.e_crowd;

public class Companies {
    private String CompanyName,CompanyDescription,Fundtarget,ImageUrl,InterestOffered,Price,Tenure,Phone,RegisteredBy,RegistrationDate,RegistrationTime;

    public Companies(String companyName, String companyDescription, String fundtarget, String imageUrl, String interestOffered, String price, String tenure, String phone, String registeredBy, String registrationDate, String registrationTime) {
        CompanyName = companyName;
        CompanyDescription = companyDescription;
        Fundtarget = fundtarget;
        ImageUrl = imageUrl;
        InterestOffered = interestOffered;
        Price = price;
        Tenure = tenure;
        Phone = phone;
        RegisteredBy = registeredBy;
        RegistrationDate = registrationDate;
        RegistrationTime = registrationTime;
    }

    public Companies() {
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyDescription() {
        return CompanyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        CompanyDescription = companyDescription;
    }

    public String getFundtarget() {
        return Fundtarget;
    }

    public void setFundtarget(String fundtarget) {
        Fundtarget = fundtarget;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getInterestOffered() {
        return InterestOffered;
    }

    public void setInterestOffered(String interestOffered) {
        InterestOffered = interestOffered;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTenure() {
        return Tenure;
    }

    public void setTenure(String tenure) {
        Tenure = tenure;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRegisteredBy() {
        return RegisteredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        RegisteredBy = registeredBy;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        RegistrationDate = registrationDate;
    }

    public String getRegistrationTime() {
        return RegistrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        RegistrationTime = registrationTime;
    }
}
