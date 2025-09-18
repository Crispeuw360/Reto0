/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    // SQL queries
    final String SQLGETMODELS = "SELECT * FROM model WHERE id_car_dealer = ?";
    final String SQLGETMODEL = "SELECT * FROM model WHERE name_model = ?";

    final String SQLGETWORKERS = " SELECT * FROM worker";
    final String SQLGETWORKER = "SELECT * FROM worker WHERE user_ = ?";
    final String SQLGETCOWORKERS = " SELECT * FROM worker WHERE id_car_dealer = ?";

    final String SQLGETDEALER = " SELECT * FROM car_dealership WHERE id_car_dealer = ?";
    final String SQLGETDEALS = "SELECT * FROM car_dealership";

    final String SQLDELETEMODEL = "DELETE FROM model WHERE name_model = ?";
    final String SQLDELETEWORKER = "DELETE FROM worker WHERE user_ = ?";
    final String SQLMODIFYWORKER = "UPDATE worker SET password_ = ?, admin_ = ?, id_car_dealer = ? WHERE user_ = ?";

    final String SQLINSERTWORKER = "INSERT INTO worker (admin_, user_, password_, id_car_dealer) VALUES (?, ?, ?, ?)";

    // kev
    final String SQLMODELS = "SELECT * FROM model WHERE ID_CAR_DEALER = ?";
    final String SQLCLIENTS = "SELECT * FROM client_";
    final String SQLSTOCK = "SELECT STOCK FROM model WHERE NAME_MODEL = ? AND ID_CAR_DEALER = ? ";
    final String SQLCALL = "{ CALL REGISTER_PURCHASE(?, ?, ?, ?, ?) }";

    // igor
    final String SQLINSERTCLIENT = "INSERT INTO client_ VALUES ( ?,?,?,?)";
    final String SQLMODIFICARMODEL = "UPDATE MODEL SET MARK = ?, STOCK = ?, PRICE = ? WHERE NAME_MODEL = ? AND ID_CAR_DEALER = ?";

    // pablo
    final String SQLINSERTMODEL = "INSERT INTO model VALUES (?,?,?,?,?)";
    final String SQLLOGIN = "SELECT * FROM worker WHERE user_ = ? AND password_ = ?";
    final String SQLGETDEALERBYNAME = " SELECT * FROM car_dealership WHERE name_ = ?";

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
     * Retrieves all workers from the database.
     *
     * @return Map of all workers keyed by username
     */
    public Map<String, ExamSession> getSessions() {
        ResultSet rs = null;
        ExamSession examSession;
        Map<String, ExamSession> workers = new TreeMap<>();

        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLGETWORKERS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                examSession = new ExamSession();
                examSession.setStatement(rs.getString("admin_"));
                examSession.setCourse(rs.getString("user_"));
                examSession.setDescription(rs.getString("password_"));
                examSession.setDate(rs.getDate("id_car_dealer"));
                workers.put(examSession.getCourse(), examSession);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workers;
    }


    /**
     * Retrieves all units for a specific dealership.
     *
     * @param cardealer The dealership whose units to retrieve
     * @return Map of units keyed by unit name
     */
    public Map<String, TeachingUnit> getUnits(TeachingUnit cardealer) {
        ResultSet rs = null;
        TeachingUnit unit;
        Map<String, TeachingUnit> units = new TreeMap<>();

        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLGETMODELS);
            stmt.setInt(1, cardealer.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                unit = new TeachingUnit();
                unit.setId(rs.getInt("id_car_dealer"));
                unit.setTitle(rs.getString("mark"));
                unit.setAcronym(rs.getString("name_model"));
                unit.setEvaluation(rs.getString("price"));
                unit.setDescription(rs.getString("stock"));
                units.put(unit.getTitle(), unit);
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error de SQL");
            e.printStackTrace();
        }
        return units;
    }


    /**
     * Retrieves a examSession by username.
     *
     * @param worker The username to search for
     * @return The Worker object if found, null otherwise
     */
   /* public Session getSession(String worker) {
        Worker foundStatement = null;
        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLGETWORKER);
            stmt.setString(1, worker);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                boolean admin = resultado.getBoolean("admin_");
                String userName = resultado.getString("user_");
                String password = resultado.getString("password_");
                int idCarDealer = resultado.getInt("id_car_dealer");

                foundStatement = new Worker(userName, password, admin, idCarDealer);
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }

        return foundStatement;
    }*/

    /**
     * Retrieves a unit by name.
     *
     * @param unitName The name of the unit to retrieve
     * @return The Model object if found, null otherwise
     */
    public TeachingUnit getUnit(String unitName) {
        TeachingUnit foundUnit = null;
        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLGETMODEL);
            stmt.setString(1, unitName);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id");
                String acronym = resultado.getString("acronym");
                String title = resultado.getString("title");
                String evaluation= resultado.getString("evaluation");
                String description = resultado.getString("description");

                foundUnit = new TeachingUnit(id, acronym, title, evaluation, description);
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener el modelo: " + e.getMessage());
        }

        return foundUnit;
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
            stmt = con.prepareStatement(SQLINSERTWORKER);
            stmt.setString(1, session.getStatement());
            stmt.setString(2, session.getDescription());
            stmt.setDate(3, (Date) session.getDate());
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
            stmt = con.prepareStatement(SQLINSERTMODEL);
            stmt.setString(1, unit.getTitle());
            stmt.setString(2, unit.getAcronym());
            stmt.setString(3, unit.getEvaluation());
            stmt.setInt(4, unit.getId());
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
    
    public boolean createStatement(Statement statement) {
        boolean creado = false;
        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLINSERTMODEL);
            stmt.setInt(1, statement.getId());
            stmt.setString(2, statement.getLevel().toString());
            stmt.setString(3, statement.getDescription());
            stmt.setString(4, statement.getRuta());

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
    /*@Override
    public Map<String, Statement> consultStatementsByUnit() {
        ResultSet rs = null;
        Client client;
        Map<String, Client> clientsList = new TreeMap<>();

        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLCLIENTS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                client = new Client();
                client.setDni(rs.getString("dni"));
                client.setEmail(rs.getString("email"));
                client.setPassword_((rs.getString("password_")));
                client.setUser_((rs.getString("user_")));
                clientsList.put(client.getUser_(), client);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error de SQL");
            e.printStackTrace();
        }
        return clientsList;
    }*/
    

    
}
