/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author pablo
 */
public class ExamSession {
    private String statement;
    private String description;
    private Date date;
    private String course; 
    
    public ExamSession(){
        this.statement="";
        this.description="";
        this.date=null;
        this.course="";
    }
    
    public ExamSession(String statement,String description,Date date,String course){
        this.statement=statement;
        this.description=description;
        this.date=date;
        this.course=course;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
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

    @Override
    public String toString() {
        return "ExamSession{" + "statement=" + statement + ", description=" + description + ", date=" + date + ", course=" + course + '}';
    }

    
    
}
