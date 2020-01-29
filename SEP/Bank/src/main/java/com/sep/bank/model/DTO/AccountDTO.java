package com.sep.bank.model.DTO;

public class AccountDTO {

    private String fullName;
    private String email;
    private String bankName;

    public AccountDTO() {
    }

    public AccountDTO(String fullName, String email, String bankName) {
        this.fullName = fullName;
        this.email = email;
        this.bankName = bankName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
