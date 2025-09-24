/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.sql.Connection;
import java.sql.Date;
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
    final String SQLINSERTSESSION = "INSERT INTO sessionE  (Esession, descripcion, Edate, course) VALUES  (?,?,?,?);";
    final String SQLINSERTSTATEMENT = "INSERT INTO statement  (id,description,Dlevel,available,path,Esession) VALUES  (?,?,?,?,?,?);";
    final String SQLINSERTUNIT_STATEMENT = "INSERT INTO unit_statement(idU,idS)   VALUES  (?,?);";
    
    final String SQLGETALLSESSIONS = "SELECT * FROM sessionE;";
    final String SQLVIEWSTATEMENTBYID = "SELECT * FROM statement WHERE id IN(SELECT idS FROM unit_statement WHERE idU IN(SELECT id FROM unit WHERE id=?));";;
    final String SQLGETSESSIONFROMSTATEMENT = "SELECT Esession FROM statement WHERE id=?;";


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


    /**
     * Retrieves a examSession by username.
     *
     * @param statementid The username to search for
     * @return The Worker object if found, null otherwise
     */
    @Override
    public ExamSession getSessionFromStatement(int statementid) {
        ExamSession foundSession = null;
        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLGETSESSIONFROMSTATEMENT);
            stmt.setInt(1, statementid);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                 
                
                String session = resultado.getString("Esession");
                String description = resultado.getString("descripcion");
                Date date = resultado.getDate("Edate");
                String course = resultado.getString("course");

                foundSession = new ExamSession(session, description, date, course);
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }

        return foundSession;
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

    /**
     * Creates a new examSession in the database.
     *
     * @param session The examSession to create
     * @return true if creation was successful, false otherwise
     */
    
    public boolean createSession(ExamSession session) {
        boolean ok = false;
        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLINSERTSESSION);
            stmt.setString(1, session.getSession());
            stmt.setString(2, session.getDescription());
            stmt.setDate(3, new java.sql.Date(session.getDate().getTime()));
            stmt.setString(4, session.getCourse());

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
    
    public boolean createStatement(Statement statement, String session) {
        boolean creado = false;
        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLINSERTSTATEMENT);
            stmt.setInt(1, statement.getId());
            stmt.setString(2, statement.getDescription());
            stmt.setString(3, statement.getLevel().toString());
            stmt.setBoolean(4, statement.isAvailability());
            stmt.setString(5, statement.getRuta());
            stmt.setString(6, session);

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

    /**
     * Authenticates a examSession's credentials.
     *
     * @param statement The examSession to authenticate
     * @return The authenticated Worker object if successful, null otherwise
     */
   /* public Statement consultStatement(Statement statement) {
        Statement foundStatement = null;
        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLLOGIN);
            stmt.setString(1, statement.getUser());
            stmt.setString(2, statement.getPassword());
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                boolean esAdmin = resultado.getBoolean("ADMIN_");
                String usuario = resultado.getString("USER_");
                String contraseña = resultado.getString("PASSWORD_");
                int idCarDealer = resultado.getInt("ID_CAR_DEALER");

                foundStatement = new Worker(usuario, contraseña, esAdmin, idCarDealer);
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }
        return foundStatement;
    }*/

    /**
     * Retrieves all clients from the database.
     *
     * @return Map of all clients keyed by username
     */
    @Override
    public Map<String, ExamSession> consultAllSessions() {
        ResultSet rs = null;
        ExamSession session;
        Map<String, ExamSession> sessionsList = new TreeMap<>();

        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLGETALLSESSIONS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                session= new ExamSession();
                session.setCourse(rs.getString("course"));

                session.setDate(rs.getDate("Edate"));
                session.setDescription(rs.getString("descripcion"));
                session.setSession(rs.getString("Esession"));
                sessionsList.put(session.getSession(), session);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error de SQL");
            e.printStackTrace();
        }
        return sessionsList;
    }
    

    
}
