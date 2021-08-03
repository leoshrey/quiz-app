package com.leoshrey.testquiz;

public class SubjectModel {
    private String subjectId, subjectName, subjectImage;


    public SubjectModel(String subjectId, String subjectName, String subjectImage) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectImage = subjectImage;
    }

    public SubjectModel(){

    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectImage() {
        return subjectImage;
    }

    public void setSubjectImage(String subjectImage) {
        this.subjectImage = subjectImage;
    }
}
