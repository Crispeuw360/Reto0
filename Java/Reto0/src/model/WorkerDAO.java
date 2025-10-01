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
    
    public Map<String, ExamSession> getSessionsFromStatement(int statementid);
    
    public Statement getStatementById(int id);

    public Map<Integer, Statement> getStatementByUnit(int unitId);
    
    public boolean CheckSession(String session);

    public boolean CheckTeachingUnit(int id);

    public boolean CheckStatement(int id);

    public boolean addStatementToTeachingUnit(int statementId, int teachingUnitId);

    public String viewTextDocumentFromStatement(int statementId);

    /**
     * Retrieves all clients from the database.
     *
     * @return Map of all clients keyed by username
     */
    public Map<String, ExamSession> consultAllSessions();
    
    public Map<String, TeachingUnit> getAllTeachingUnits();

    public Map<String, Statement> getAllStatement();
    
    
}
