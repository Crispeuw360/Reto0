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
     public boolean createStatement(Statement statement, String session){
         return dao.createStatement(statement, session);
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
     public boolean CheckSession(String session){
         return dao.CheckSession(session);
     }
}
