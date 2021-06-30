package com.example.e_crowd;

public class validateinvestorInfo {
    private String InvestorName,kycUrl,docType,DOB,Gender,investorEmail;

    public validateinvestorInfo(String investorName, String kycUrl, String docType, String DOB, String gender, String investorEmail) {
        InvestorName = investorName;
        this.kycUrl = kycUrl;
        this.docType = docType;
        this.DOB = DOB;
        Gender = gender;
        this.investorEmail = investorEmail;
    }

    public validateinvestorInfo() {
    }

    public String getInvestorName() {
        return InvestorName;
    }

    public void setInvestorName(String investorName) {
        InvestorName = investorName;
    }

    public String getKycUrl() {
        return kycUrl;
    }

    public void setKycUrl(String kycUrl) {
        this.kycUrl = kycUrl;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getInvestorEmail() {
        return investorEmail;
    }

    public void setInvestorEmail(String investorEmail) {
        this.investorEmail = investorEmail;
    }
}
