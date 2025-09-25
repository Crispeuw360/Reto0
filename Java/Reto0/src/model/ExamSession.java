/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.util.Date;

/**
 *
 * @author pablo
 */
public class ExamSession {
    private String session;
    private String description;
    private Date date;
    private String course; 
    private int statementId;
    
    public ExamSession(){
        this.session="";
        this.description="";
        this.date=null;
        this.course="";
        this.statementId=0;
    }
    
    public ExamSession(String session,String description,Date date,String course){
        this.session=session;
        this.description=description;
        this.date=date;
        this.course=course;
        this.statementId=0;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getStatementId() {
        return statementId;
    }

    public void setStatementId(int statementId) {
        this.statementId = statementId;
    }
    @Override
    public String toString() {
        return "ExamSession{" + "session=" + session + ", description=" + description + ", date=" + date + ", course=" + course + '}';
    }

    
    
}