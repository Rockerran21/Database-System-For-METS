package model;

import java.util.Date;

public class ProblemDetail {
    private int problemId;
    private String description;
    private String status;
    private Date dateReported;
    private int callerId;
    private int equipmentId;
    private int specialistId;
    private String resolutionDetails;


    // Constructor
    public ProblemDetail(int problemId, String description, String status, Date dateReported,
                         int callerId, int equipmentId, int specialistId, String resolutionDetails) {
        this.problemId = problemId;
        this.description = description;
        this.status = status;
        this.dateReported = dateReported;
        this.callerId = callerId;
        this.equipmentId = equipmentId;
        this.specialistId = specialistId;
        this.resolutionDetails = resolutionDetails;
    }

    // Getters
    public int getProblemId() {
        return problemId;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public int getCallerId() {
        return callerId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public int getSpecialistId() {
        return specialistId;
    }

    public String getResolutionDetails() {
        return resolutionDetails;
    }

    // Setters
    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setSpecialistId(int specialistId) {
        this.specialistId = specialistId;
    }

    public void setResolutionDetails(String resolutionDetails) {
        this.resolutionDetails = resolutionDetails;
    }

    // toString method
    @Override
    public String toString() {
        return "ProblemDetail{" +
                "problemId=" + problemId +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", dateReported=" + dateReported +
                ", callerId=" + callerId +
                ", equipmentId=" + equipmentId +
                ", specialistId=" + specialistId +
                ", resolutionDetails='" + resolutionDetails + '\'' +
                '}';
    }
}

