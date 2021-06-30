package com.example.e_crowd;

public class LegalDocs {
    String CompanyName,PdfUrl;

    public LegalDocs(String companyName, String pdfUrl) {
        CompanyName = companyName;
        PdfUrl = pdfUrl;
    }

    public LegalDocs() {
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getPdfUrl() {
        return PdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        PdfUrl = pdfUrl;
    }
}
