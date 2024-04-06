package model;

import java.util.Date;

public class Problem {
    private int problemId;
    private String description;
    private String status;
    private Date dateReported;
    private int callerId;
    private int equipmentId;
    private int specialistId;

    // Constructors
    public Problem(int problemId, String description, String status, Date dateReported, int callerId, int equipmentId, int specialistId) {
        this.problemId = problemId;
        this.description = description;
        this.status = status;
        this.dateReported = dateReported;
        this.callerId = callerId;
        this.equipmentId = equipmentId;
        this.specialistId = specialistId;
    }

    public Problem(String description, String status, Date dateReported, int callerId, int equipmentId, int specialistId) {
        this(0, description, status, dateReported, callerId, equipmentId, specialistId);
    }

    // Getters
    public int getProblemId() { return problemId; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Date getDateReported() { return dateReported; }
    public int getCallerId() { return callerId; }
    public int getEquipmentId() { return equipmentId; }
    public int getSpecialistId() { return specialistId; }

    // Setters
    public void setProblemId(int problemId) { this.problemId = problemId; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
    public void setDateReported(Date dateReported) { this.dateReported = dateReported; }
    public void setCallerId(int callerId) { this.callerId = callerId; }
    public void setEquipmentId(int equipmentId) { this.equipmentId = equipmentId; }
    public void setSpecialistId(int specialistId) { this.specialistId = specialistId; }
}
