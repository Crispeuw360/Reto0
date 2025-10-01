/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * Implementation of the WorkerDAO interface that provides database access
 * operations using JDBC.
 * 
 * <p>This class handles all database interactions for the application,
 including CRUD operations for workers, units, clients, and dealerships.</p>
 *
 * @author All
 * @version 1.0
 */
public class ImplementsBD implements WorkerDAO {
    // Atributos
    private Connection con;
    private PreparedStatement stmt;

    // Los siguientes atributos se utilizan para recoger los valores del fich de
    // configuraci n
    private ResourceBundle configFile;
    private String driverBD;
    private String urlBD;
    private String userBD;
    private String passwordBD;

    final String SQLINSERTUNIT = "INSERT INTO unit (id, acronim, title, evaluation, description) VALUES(?,?,?,?,?);";
    final String SQLINSERTSESSION = "INSERT INTO sessionE  (Esession, descripcion, Edate, course,id_statement) VALUES  (?,?,?,?,?);";
    final String SQLINSERTSTATEMENT = "INSERT INTO statement  (id,description,Dlevel,available,path) VALUES  (?,?,?,?,?);";
    final String SQLINSERTUNIT_STATEMENT = "INSERT INTO Unit_statement(idU,idS)   VALUES  (?,?);";
    
    final String SQLVIEWSTATEMENTBYID = "SELECT * FROM statement WHERE id IN(SELECT idS FROM unit_statement WHERE idU IN(SELECT id FROM unit WHERE id=?));";;
    final String SQLGETSESSIONFROMSTATEMENT = "SELECT * FROM sessionE WHERE id_statement = ?;";
    final String SQLCHECKSESSION = "SELECT Esession FROM sessionE WHERE Esession=?;";
    final String SQLCHECKTEACHINGUNIT = "SELECT id FROM unit WHERE id=?;";
    final String SQLCHECKSTATEMENT = "SELECT id FROM statement WHERE id=?;";

    final String SQLGETALLUNITS = "SELECT * FROM unit;";
    final String SQLGETALLSESSIONS = "SELECT * FROM sessionE;";
    final String SQLGETALLSTATEMENTS = "SELECT * FROM statement ;";
    final String SQLGETSTATEMENTS_BY_UNIT = "SELECT * FROM statement " + "WHERE id IN (SELECT idS FROM Unit_Statement WHERE idU = ?);";
    

    /**
     * Constructs a new ImplementsBD instance and loads database configuration.
     */
    public ImplementsBD() {
        this.configFile = ResourceBundle.getBundle("model.configClase");
        this.driverBD = this.configFile.getString("Driver");
        this.urlBD = this.configFile.getString("Conn");
        this.userBD = this.configFile.getString("DBUser");
        this.passwordBD = this.configFile.getString("DBPass");
    }

    /**
     * Opens a connection to the database using configured parameters.
     */
    private void openConnection() {
        try {
            con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
        } catch (SQLException e) {
            System.out.println("Error al intentar abrir la BD");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Map<String, ExamSession> getSessionsFromStatement(int statementid) {
        ResultSet rs = null;
        Map<String, ExamSession> sessionsList = new TreeMap<>();
        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLGETSESSIONFROMSTATEMENT);
            stmt.setInt(1, statementid);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ExamSession session = new ExamSession();
                session.setSession(rs.getString("Esession"));
                session.setDescription(rs.getString("descripcion"));
                session.setDate(rs.getDate("Edate"));
                session.setCourse(rs.getString("course"));
                session.setStatementId(rs.getInt("id_statement"));

                sessionsList.put(session.getSession(), session);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener sesiones: " + e.getMessage());
        }

        return sessionsList;
    }

    /**
     * Retrieves a unit by name.
     *
     * @param id
      
    * @return The Model object if found, null otherwise
     */
    public Statement getStatementById(int id) {
        Statement foundStatement = null;
        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLVIEWSTATEMENTBYID);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idS = resultado.getInt("id");
                String description = resultado.getString("acronym");
                String levelStr = resultado.getString("Dlevel");
                boolean avaliable = resultado.getBoolean("avaliable");
                String path = resultado.getString("path");
                Level dLevel = Level.valueOf(levelStr);
                
                foundStatement = new Statement(idS, description, dLevel, avaliable, path);
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener el modelo: " + e.getMessage());
        }

        return foundStatement;
    }

    public Map<Integer, Statement> getStatementByUnit(int unitId) {
        ResultSet rs = null;
        Map<Integer, Statement> statementList = new TreeMap<>();
        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLGETSTATEMENTS_BY_UNIT);
            stmt.setInt(1, unitId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Statement st = new Statement();
                st.setId(rs.getInt("id"));
                st.setDescription(rs.getString("description"));
                st.setLevel(Level.valueOf(rs.getString("Dlevel")));
                st.setAvailability(rs.getBoolean("available"));
                st.setRuta(rs.getString("path"));
                // si tu modelo Statement guarda la sesión:
                // st.setSession(rs.getString("Esession"));

                statementList.put(st.getId(), st);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error de SQL en getStatementByUnit: " + e.getMessage());
            e.printStackTrace();
        }
        return statementList;
    }

    

    /**
     * Creates a new examSession in the database.
     *
     * @param session The examSession to create
     * @return true if creation was successful, false otherwise
     */
    @Override
    public boolean createSession(ExamSession session) {
        boolean ok = false;
        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLINSERTSESSION);
            stmt.setString(1, session.getSession());
            stmt.setString(2, session.getDescription());
            stmt.setDate(3, new java.sql.Date(session.getDate().getTime()));
            stmt.setString(4, session.getCourse());
            stmt.setInt(5, session.getStatementId());

            if (stmt.executeUpdate() > 0) {
                ok = true;
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }
        return ok;
    }

    /**
     * Creates a new unit in the database.
     *
     * @param unit The unit to create
     * @return true if creation was successful, false otherwise
     */
    @Override
    public boolean createUnit(TeachingUnit unit) {
        boolean creado = false;
        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLINSERTUNIT);
            stmt.setInt(1, unit.getId());
            stmt.setString(2, unit.getAcronym());
            stmt.setString(3, unit.getTitle());
            stmt.setString(4, unit.getEvaluation());
            stmt.setString(5, unit.getDescription());

            if (stmt.executeUpdate() > 0) {
                creado = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al insertar el modelo: " + e.getMessage());
        }
        return creado;
    }
    
    @Override
    public boolean createStatement(Statement statement) {
        boolean creado = false;
        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLINSERTSTATEMENT);
            stmt.setInt(1, statement.getId());
            stmt.setString(2, statement.getDescription());
            stmt.setString(3, statement.getLevel().toString());
            stmt.setBoolean(4, statement.isAvailability());
            stmt.setString(5, statement.getRuta());

            if (stmt.executeUpdate() > 0) {
                creado = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al insertar el modelo: " + e.getMessage());
        }
        return creado;
    }
    
    @Override
    public boolean CheckSession(String session) {
        boolean creado = false;
        this.openConnection();

        try {
           stmt = con.prepareStatement(SQLCHECKSESSION);
           stmt.setString(1, session);
           ResultSet resultado = stmt.executeQuery();
           if (resultado.next()) {
                creado = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }
        return creado;
    }

    @Override
    public boolean CheckTeachingUnit(int id) {
        boolean creado = false;
        this.openConnection();

        try {
           stmt = con.prepareStatement(SQLCHECKTEACHINGUNIT);
           stmt.setInt(1, id);
           ResultSet resultado = stmt.executeQuery();
           if (resultado.next()) {
                creado = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }
        return creado;
    }

    @Override
    public boolean CheckStatement(int id) {
        boolean creado = false;
        ResultSet resultado;
        this.openConnection();

        try {
           stmt = con.prepareStatement(SQLCHECKSTATEMENT);
           stmt.setInt(1, id);
           resultado = stmt.executeQuery();
           if (resultado.next()) {
                creado = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }
        return creado;
    }
    
    public Map<String, TeachingUnit> getAllTeachingUnits() {
        ResultSet rs = null;
        TeachingUnit unit;
        Map<String, TeachingUnit> unitsList = new TreeMap<>();

        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLGETALLUNITS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                unit= new TeachingUnit();
                unit.setId(rs.getInt("id"));
                unit.setAcronym(rs.getString("acronim"));
                unit.setTitle(rs.getString("title"));
                unit.setEvaluation(rs.getString("evaluation"));
                unit.setDescription(rs.getString("description"));
                unitsList.put(unit.getAcronym(), unit);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error de SQL");
            e.printStackTrace();
        }
        return unitsList;
    }
    
    public Map<String, Statement> getAllStatement() {
        ResultSet rs = null;
        Statement statement;
        Map<String, Statement> statementList = new TreeMap<>();

        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLGETALLSTATEMENTS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                statement= new Statement();
                statement.setId(rs.getInt("id"));
                statement.setDescription(rs.getString("description"));
                statement.setLevel (Level.valueOf(rs.getString("Dlevel")));
                statement.setAvailability(rs.getBoolean("available"));
                statement.setRuta(rs.getString("path"));
                statementList.put(statement.getDescription(), statement);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error de SQL");
            e.printStackTrace();
        }
        return statementList;
    }
    
    public boolean addStatementToTeachingUnit(int statementId, int teachingUnitId){
        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLINSERTUNIT_STATEMENT);
            stmt.setInt(1, teachingUnitId);
            stmt.setInt(2, statementId);
            if (stmt.executeUpdate() > 0) {
                return true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al insertar el modelo: " + e.getMessage());
        }
        return false;
    }
    
    public String viewTextDocumentFromStatement(int statementId){
        Statement foundStatement = null;
        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLVIEWSTATEMENTBYID);
            stmt.setInt(1, statementId);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int idS = resultado.getInt("id");
                String description = resultado.getString("description");
                String levelStr = resultado.getString("Dlevel");
                boolean avaliable = resultado.getBoolean("available");
                String path = resultado.getString("path");
                Level dLevel = Level.valueOf(levelStr);
                
                foundStatement = new Statement(idS, description, dLevel, avaliable, path);
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener el modelo: " + e.getMessage());
        }

        return foundStatement.getRuta();
    }
    
}
