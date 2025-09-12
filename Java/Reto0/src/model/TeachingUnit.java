/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author pablo
 */
public class TeachingUnit {
    private int id;
    private String acronym;
    private String title;
    private String evaluation;
    private String description;
    
    public TeachingUnit(){
        this.id=0;
        this.acronym="";
        this.title="";
        this.evaluation="";
        this.description="";
    }
    public TeachingUnit(int id,String acronym,String title,String evaluation,String description){
        this.id=id;
        this.acronym=acronym;
        this.title=title;
        this.evaluation=evaluation;
        this.description=description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TeachingUnit{" + "id=" + id + ", acronym=" + acronym + ", title=" + title + ", evaluation=" + evaluation + ", description=" + description + '}';
    }
    
}
