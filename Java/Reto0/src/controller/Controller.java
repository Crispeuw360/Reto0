/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.TeachingUnit;
import model.WorkerDAO;
import model.ExamSession;
import model.Statement;
import java.util.Map;

/**
 *
 * @author pablo
 */
public class Controller {
    WorkerDAO dao = (WorkerDAO) new model.ImplementsBD();
    
     public boolean createUnit(TeachingUnit unit){
         return dao.createUnit(unit);
     }
     public boolean createSession(ExamSession session){
         return dao.createSession(session);
     }
     public boolean createStatement(Statement statement){
         return dao.createStatement(statement);
     }
     public ExamSession getSessionFromStatement(int statementid){
         return dao.getSessionFromStatement(statementid);
     }
     public Statement getStatementById(int id){
         return dao.getStatementById(id);
     }
     public Map<String, ExamSession> consultAllSessions(){
         return dao.consultAllSessions();
     }
     public Map<String, TeachingUnit> getAllTeachingUnits(){
        return dao.getAllTeachingUnits();
    }
    public Map<String, Statement> getAllStatement(){
        return dao.getAllStatement();
    }
     public boolean CheckSession(String session){
         return dao.CheckSession(session);
     }
     public boolean CheckTeachingUnit(int id){
         return dao.CheckTeachingUnit(id);
     }
     public boolean CheckStatement(int id){
         return dao.CheckStatement(id);
     }

     public boolean addStatementToTeachingUnit(int statementId, int teachingUnitId){
         return dao.addStatementToTeachingUnit(statementId, teachingUnitId);
     }
     
}
