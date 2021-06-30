package com.example.e_crowd;

public class Companydata {
    String CompanyName,CompanyDescription,Fundtarget,ImageUrl,InterestOffered,Price,RegisteredBy,Tenure;

    public Companydata(String companyName, String companyDescription, String fundtarget, String imageUrl, String interestOffered, String price, String registeredBy, String tenure) {
        CompanyName = companyName;
        CompanyDescription = companyDescription;
        Fundtarget = fundtarget;
        ImageUrl = imageUrl;
        InterestOffered = interestOffered;
        Price = price;
        RegisteredBy = registeredBy;
        Tenure = tenure;
    }

    public Companydata() {
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

    public String getRegisteredBy() {
        return RegisteredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        RegisteredBy = registeredBy;
    }

    public String getTenure() {
        return Tenure;
    }

    public void setTenure(String tenure) {
        Tenure = tenure;
    }
}
