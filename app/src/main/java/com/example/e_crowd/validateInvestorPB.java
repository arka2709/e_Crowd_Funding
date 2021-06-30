package com.example.e_crowd;

public class validateInvestorPB {
    private String BankAccountNo,IFSCCode,InvestorName,MobileNo,PanUrl,investorEmail;

    public validateInvestorPB(String bankAccountNo, String IFSCCode, String investorName, String mobileNo, String panUrl, String investorEmail) {
        BankAccountNo = bankAccountNo;
        this.IFSCCode = IFSCCode;
        InvestorName = investorName;
        MobileNo = mobileNo;
        PanUrl = panUrl;
        this.investorEmail = investorEmail;
    }

    public validateInvestorPB() {
    }

    public String getBankAccountNo() {
        return BankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        BankAccountNo = bankAccountNo;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public void setIFSCCode(String IFSCCode) {
        this.IFSCCode = IFSCCode;
    }

    public String getInvestorName() {
        return InvestorName;
    }

    public void setInvestorName(String investorName) {
        InvestorName = investorName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getPanUrl() {
        return PanUrl;
    }

    public void setPanUrl(String panUrl) {
        PanUrl = panUrl;
    }

    public String getInvestorEmail() {
        return investorEmail;
    }

    public void setInvestorEmail(String investorEmail) {
        this.investorEmail = investorEmail;
    }
}
