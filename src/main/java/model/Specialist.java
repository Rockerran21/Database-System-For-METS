package model;

public class Specialist {
    private int specialistId;
    private String name;
    private String expertiseArea;

    public Specialist(String name, String expertiseArea) {
        this.name = name;
        this.expertiseArea = expertiseArea;
    }

    // Getters and Setters

    public int getSpecialistId() { return specialistId; }
    public void setSpecialistId(int specialistId) { this.specialistId = specialistId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getExpertiseArea() { return expertiseArea; }
    public void setExpertiseArea(String expertiseArea) { this.expertiseArea = expertiseArea; }

    @Override
    public String toString() {
        return "Specialist{" +
                "specialistId=" + specialistId +
                ", name='" + name + '\'' +
                ", expertiseArea='" + expertiseArea + '\'' +
                '}';
    }
}
