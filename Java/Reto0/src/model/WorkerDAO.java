/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.time.LocalDate;
import java.util.Map;

/**
 * Interface for managing workers, dealerships, clients, and models.
 */
public interface WorkerDAO {

    public boolean createSession(ExamSession session);
    
    public boolean createUnit(TeachingUnit unit);
    
    public boolean createStatement(Statement statement, String session);
    
    public ExamSession getSessionFromStatement(int statementid);
    
    public Statement getStatementById(int id);
}
