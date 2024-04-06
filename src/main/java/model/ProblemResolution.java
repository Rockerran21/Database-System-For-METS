package model;

import java.util.Date;

public class ProblemResolution {
    private int resolutionId;
    private int problemId;
    private Date dateResolved;
    private String resolutionDetail;

    // Constructors
    public ProblemResolution(int resolutionId, int problemId, Date dateResolved, String resolutionDetail) {
        this.resolutionId = resolutionId;
        this.problemId = problemId;
        this.dateResolved = dateResolved;
        this.resolutionDetail = resolutionDetail;
    }

    public ProblemResolution(int problemId, Date dateResolved, String resolutionDetail) {
        this(0, problemId, dateResolved, resolutionDetail);
    }

    // Getters
    public int getResolutionId() { return resolutionId; }
    public int getProblemId() { return problemId; }
    public Date getDateResolved() { return dateResolved; }
    public String getResolutionDetail() { return resolutionDetail; }

    // Setters
    public void setResolutionId(int resolutionId) { this.resolutionId = resolutionId; }
    public void setProblemId(int problemId) { this.problemId = problemId; }
    public void setDateResolved(Date dateResolved) { this.dateResolved = dateResolved; }
    public void setResolutionDetail(String resolutionDetail) { this.resolutionDetail = resolutionDetail; }
}
