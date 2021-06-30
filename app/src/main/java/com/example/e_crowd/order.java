package com.example.e_crowd;

class order {
    private String Camt,CompanyDesc,CompanyName,FundTarget,Futuredate,IFSCCode,Interest,InvestorAccountNo,InvestorEmail,InvestorName,InvestorPhone,Price,RegisteredBy,Total,date,imageUrl,quantity,tenure,time;

    public order(String camt, String companyDesc, String companyName, String fundTarget, String futuredate, String IFSCCode, String interest, String investorAccountNo, String investorEmail, String investorName, String investorPhone, String price, String registeredBy, String total, String date, String imageUrl, String quantity, String tenure, String time) {
        Camt = camt;
        CompanyDesc = companyDesc;
        CompanyName = companyName;
        FundTarget = fundTarget;
        Futuredate = futuredate;
        this.IFSCCode = IFSCCode;
        Interest = interest;
        InvestorAccountNo = investorAccountNo;
        InvestorEmail = investorEmail;
        InvestorName = investorName;
        InvestorPhone = investorPhone;
        Price = price;
        RegisteredBy = registeredBy;
        Total = total;
        this.date = date;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.tenure = tenure;
        this.time = time;
    }

    public order() {
    }

    public String getCamt() {
        return Camt;
    }

    public void setCamt(String camt) {
        Camt = camt;
    }

    public String getCompanyDesc() {
        return CompanyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        CompanyDesc = companyDesc;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getFundTarget() {
        return FundTarget;
    }

    public void setFundTarget(String fundTarget) {
        FundTarget = fundTarget;
    }

    public String getFuturedate() {
        return Futuredate;
    }

    public void setFuturedate(String futuredate) {
        Futuredate = futuredate;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public void setIFSCCode(String IFSCCode) {
        this.IFSCCode = IFSCCode;
    }

    public String getInterest() {
        return Interest;
    }

    public void setInterest(String interest) {
        Interest = interest;
    }

    public String getInvestorAccountNo() {
        return InvestorAccountNo;
    }

    public void setInvestorAccountNo(String investorAccountNo) {
        InvestorAccountNo = investorAccountNo;
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

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
