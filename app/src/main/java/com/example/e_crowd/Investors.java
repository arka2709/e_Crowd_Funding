package com.example.e_crowd;

public class Investors {
   private String BankAccountNo,DOB,Gender,IFSCCode,InvestorEmail,InvestorName,InvestorPhone,PanUrl,kycType,kycUrl;

    public Investors(String bankAccountNo, String DOB, String gender, String IFSCCode, String investorEmail, String investorName, String investorPhone, String panUrl, String kycType, String kycUrl) {
        BankAccountNo = bankAccountNo;
        this.DOB = DOB;
        Gender = gender;
        this.IFSCCode = IFSCCode;
        InvestorEmail = investorEmail;
        InvestorName = investorName;
        InvestorPhone = investorPhone;
        PanUrl = panUrl;
        this.kycType = kycType;
        this.kycUrl = kycUrl;
    }

    public Investors() {
    }

    public String getBankAccountNo() {
        return BankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        BankAccountNo = bankAccountNo;
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

    public String getIFSCCode() {
        return IFSCCode;
    }

    public void setIFSCCode(String IFSCCode) {
        this.IFSCCode = IFSCCode;
    }

    public String getInvestorEmail() {
        return InvestorEmail;
    }

    public void setInvestorEmail(String investorEmail) {
        InvestorEmail = investorEmail;
    }

    public String getInvestorName() {
        return InvestorName;
    }

    public void setInvestorName(String investorName) {
        InvestorName = investorName;
    }

    public String getInvestorPhone() {
        return InvestorPhone;
    }

    public void setInvestorPhone(String investorPhone) {
        InvestorPhone = investorPhone;
    }

    public String getPanUrl() {
        return PanUrl;
    }

    public void setPanUrl(String panUrl) {
        PanUrl = panUrl;
    }

    public String getKycType() {
        return kycType;
    }

    public void setKycType(String kycType) {
        this.kycType = kycType;
    }

    public String getKycUrl() {
        return kycUrl;
    }

    public void setKycUrl(String kycUrl) {
        this.kycUrl = kycUrl;
    }
}
