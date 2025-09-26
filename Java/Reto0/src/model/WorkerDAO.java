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
    
    public boolean createStatement(Statement statement);
    
    public ExamSession getSessionFromStatement(int statementid);
    
    public Statement getStatementById(int id);
    
    public boolean CheckSession(String session);

    public boolean CheckTeachingUnit(int id);

    public boolean CheckStatement(int id);


    /**
     * Retrieves all clients from the database.
     *
     * @return Map of all clients keyed by username
     */
    public Map<String, ExamSession> consultAllSessions();
    
    public Map<String, TeachingUnit> getAllTeachingUnits();

    public Map<String, Statement> getAllStatement();
    
    public Map<Integer, Statement> getStatementByUnit(int Unitid);
    
    
}
