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
    private Date dateResolved;
    private String resolutionDetail;

    public ProblemDetail(int problemId, String description, String status, Date dateReported, int callerId, int equipmentId, int specialistId, Date dateResolved, String resolutionDetail) {
        this.problemId = problemId;
        this.description = description;
        this.status = status;
        this.dateReported = dateReported;
        this.callerId = callerId;
        this.equipmentId = equipmentId;
        this.specialistId = specialistId;
        this.dateResolved = dateResolved;
        this.resolutionDetail = resolutionDetail;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }

    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(int specialistId) {
        this.specialistId = specialistId;
    }

    public Date getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(Date dateResolved) {
        this.dateResolved = dateResolved;
    }

    public String getResolutionDetail() {
        return resolutionDetail;
    }

    public void setResolutionDetail(String resolutionDetail) {
        this.resolutionDetail = resolutionDetail;
    }

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
                ", dateResolved=" + dateResolved +
                ", resolutionDetail='" + resolutionDetail + '\'' +
                '}';
    }
}
