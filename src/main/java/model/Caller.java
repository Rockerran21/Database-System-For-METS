package model;

public class Caller {
    private int callerId;
    private String name;
    private String department;
    private String contactInfo;
    private String address;
    private int accountNumber;

    // Constructor for creating a new Caller object without an ID (for insertion)
    public Caller(String name, String department, String contactInfo, String address, int accountNumber) {
        this.name = name;
        this.department = department;
        this.contactInfo = contactInfo;
        this.address = address;
        this.accountNumber = accountNumber;
    }

    // Constructor including ID (for retrieval)
    public Caller(int callerId, String name, String department, String contactInfo, String address, int accountNumber) {
        this(name, department, contactInfo, address, accountNumber);
        this.callerId = callerId;
    }

    // Getters
    public int getCallerId() {
        return callerId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    // Setters
    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

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
