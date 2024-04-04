package model;

public class Caller {
    private int callerId; // Managed by the database
    private String name;
    private String department;
    private String contactInfo;
    private String address;
    private int accountNumber;

    public Caller(String name, String department, String contactInfo, String address, int accountNumber) {
        this.name = name;
        this.department = department;
        this.contactInfo = contactInfo;
        this.address = address;
        this.accountNumber = accountNumber;
    }

    // Getters and Setters

    public int getCallerId() { return callerId; }
    public void setCallerId(int callerId) { this.callerId = callerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public int getAccountNumber() { return accountNumber; }
    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }

    @Override
    public String toString() {
        return "Caller{" +
                "callerId=" + callerId +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", address='" + address + '\'' +
                ", accountNumber=" + accountNumber +
                '}';
    }
}
