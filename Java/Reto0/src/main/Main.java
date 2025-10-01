/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.sql.Date;

import controller.Controller;
import model.TeachingUnit;
import model.ExamSession;
import model.Statement;
import model.Level;
import utilidades.Utilidades;

/**
 *
 * @author pablo
 * 
 */
public class Main {
    public static int menuM() {
        System.out.println(
                " __________\n|          |\n|   menu   |\n|__________|\n\n0.-Exit\n1.-Create a teaching unit\n2.-Create an exam session\n3.-Create an exam statement\n4.-Consult the exam statements\n5.-Consult in which sessions a specific statement has been used\n6.-View the text document associated with a statement");
        int menu = Utilidades.leerInt(0, 7);
        return menu;
    }

    public static void main(String[] args) {
        Controller Lcontroler = new Controller();
        int menu = 0;
        do {
            menu = menuM();
            switch (menu) {
                case 0:
                    System.out.println("Bye!");
                    break;
                case 1:
                    CreateUnit(Lcontroler);
                    break;
                case 2:
                    CreateSession(Lcontroler);
                    break;
                case 3:
                    CreateStatement(Lcontroler);
                    break;
                case 4:
                    getStatementByUnit(Lcontroler);
                    break;
                case 5:
                    consultSessionsByStatement(Lcontroler);
                    break;
                case 6:
                    viewTextDocumentFromStatement(Lcontroler);
                    break;
            }
        } while (menu != 0);
    }

    private static void CreateUnit(Controller Lcontroler) {
        int id;
        String acronym, title, evaluation, desc;
        boolean unitValid = true;
        char again = 'Y';
        do {
            do {
                System.out.println("Input an ID for the unit");
                id = Utilidades.leerInt();
                if (!Lcontroler.CheckTeachingUnit(id)) {
                    unitValid = false;
                } else {
                    System.err.println("Unit already exists. Please try again.");
                }
            } while (unitValid);
            System.out.println("Input an acronym for the unit");
            acronym = Utilidades.introducirCadena();
            System.out.println("Input a title for the unit");
            title = Utilidades.introducirCadena();
            System.out.println("Input an evaluation for the unit");
            evaluation = Utilidades.introducirCadena();
            System.out.println("Input a description for the unit");
            desc = Utilidades.introducirCadena();

            TeachingUnit tu = new TeachingUnit(id, acronym, title, evaluation, desc);

            if (Lcontroler.createUnit(tu)) {
                System.out.println("Unit created Succesfully");
            } else {
                System.err.println("An unexpected ERROR ocurred while creating this unit");
            }
            System.out.println("Do you want to create aothe unit?(Y/N)");
            again = Utilidades.leerChar('Y', 'N');
        } while (again == 'Y');
    }

    private static void CreateSession(Controller Lcontroler) {
        String eSession, description, course;
        Date date;
        char again = 'Y';
        int statementId = 0;
        boolean statementValid = false;
        boolean sessionExists = true;
        do {
            do {
                System.out.println("Input an exam session");
                eSession = Utilidades.introducirCadena();
                if (!Lcontroler.CheckSession(eSession)) {
                    sessionExists = false;
                } else {
                    System.err.println("Session already exists. Please try again.");
                }
            } while (sessionExists);
            System.out.println("Input a description");
            description = Utilidades.introducirCadena();
            System.out.println("Input a course");
            course = Utilidades.introducirCadena();
            System.out.println("Input a date (yyyy/MM/dd)");
            LocalDate localDate = Utilidades.leerFechaAMD();
            System.out.println("Input a statement ID");
            getAllStatement(Lcontroler);
            do {
                statementId = Utilidades.leerInt();
                if (Lcontroler.CheckStatement(statementId)) {
                    statementValid = true;
                } else {
                    System.err.println("Statement not found. Please try again.");
                }
            } while (!statementValid);

            // Convertir LocalDate a Date
            date = Date.valueOf(localDate);

            ExamSession session = new ExamSession(eSession, description, date, course, statementId);

            if (Lcontroler.createSession(session)) {
                System.out.println("Session created Successfully");
            } else {
                System.err.println("An unexpected ERROR occurred while creating this session");
            }
            System.out.println("Do you want to create another session?(Y/N)");
            again = Utilidades.leerChar('Y', 'N');
        } while (again == 'Y');
    }

    private static void CreateStatement(Controller Lcontroler) {
        int id = 0;
        int teachingUnitId = 0;
        String description, path;
        Level level = null;
        boolean availability;
        boolean levelValid = false;
        boolean statementExists = true;
        char again = 'Y';
        do {
            do {
                System.out.println("Input an ID for the statement");
                id = Utilidades.leerInt();
                if (!Lcontroler.CheckStatement(id)) {
                    statementExists = false;
                } else {
                    System.err.println("Statement already exists. Please try again.");
                }
            } while (statementExists);
            System.out.println("Input a description for the statement");
            description = Utilidades.introducirCadena();
            do {
                System.out.println("Input a level for the statement (ALTA, MEDIA, BAJA)");
                if (Utilidades.introducirCadena().toUpperCase().equals("ALTA")) {
                    level = Level.ALTA;
                    levelValid = true;
                } else if (Utilidades.introducirCadena().toUpperCase().equals("MEDIA")) {
                    level = Level.MEDIA;
                    levelValid = true;
                } else if (Utilidades.introducirCadena().toUpperCase().equals("BAJA")) {
                    level = Level.BAJA;
                    levelValid = true;
                } else {
                    System.err.println("Invalid level. Please try again.");
                }
            } while (!levelValid);
            System.out.println("Input availability for the statement (Y/N)");
            availability = Utilidades.leerChar('Y', 'N') == 'Y';
            System.out.println("Input a path for the statement");
            path = Utilidades.introducirCadena();

            Statement statement = new Statement(id, description, level, availability, path);

            if (Lcontroler.createStatement(statement)) {
                System.out.println("Statement created Successfully");
            } else {
                System.err.println("An unexpected ERROR occurred while creating this statement");
            }
            do {
                System.out.println("Input a teaching unit ID for the statement");
                getAllTeachingUnits(Lcontroler);
                teachingUnitId = Utilidades.leerInt();
                Lcontroler.addStatementToTeachingUnit(statement.getId(), teachingUnitId);
            } while (!Lcontroler.CheckTeachingUnit(teachingUnitId));

            System.out.println("Do you want to create another statement?(Y/N)");
            again = Utilidades.leerChar('Y', 'N');
        } while (again == 'Y');
    }

    private static void consultAllSessions(Controller Lcontroler) {
        Map<String, ExamSession> sessions = Lcontroler.consultAllSessions();
        for (ExamSession session : sessions.values()) {
            System.out.println(session);
        }
    }

    private static void getAllTeachingUnits(Controller Lcontroler) {
        Map<String, TeachingUnit> units = Lcontroler.getAllTeachingUnits();
        for (TeachingUnit unit : units.values()) {
            System.out.println(unit);
        }
    }

    private static void getAllStatement(Controller Lcontroler) {
        Map<String, Statement> statements = Lcontroler.getAllStatement();
        for (Statement statement : statements.values()) {
            System.out.println(statement);
        }
    }

    private static void getStatementByUnit(Controller Lcontroler){
        boolean datosValidos=true;
        System.out.println("Consult Statement by id: ");
        
        Map<String, TeachingUnit>units = Lcontroler.getAllTeachingUnits();
        if (units.isEmpty()){
            System.out.println("No hay unidades disponibles");
            datosValidos=false;
        }
        
        TreeMap <Integer, TeachingUnit> byId = new TreeMap<>();
        for (TeachingUnit u : units.values()) {
            byId.put(u.getId(), u);
        }
        if (datosValidos){
            System.out.println("Units: ");
            if (datosValidos) {
                System.out.println("Unidades disponibles");
                byId.forEach((id, u) ->
                    System.out.println(" " + id + " : " + u.getAcronym() + " | " + u.getTitle()));
            }
        }
        int unitId = -1;
        if (datosValidos) {
            try {
                System.out.print("\nIntroduce el ID de la unidad: ");
                unitId = Utilidades.leerInt();
            } catch (NumberFormatException e) {
                System.out.println("Debes introducir un número entero.");
                datosValidos = false;
            }
        }

        // 3) Validar que la unidad existe
        if (datosValidos && !Lcontroler.CheckTeachingUnit(unitId)) {
            System.out.println("La unidad " + unitId + " no existe.");
            datosValidos = false;
        }
        
        if (datosValidos){
            Map<Integer, Statement > statements =Lcontroler.getStatementByUnit(unitId);
            if(statements.isEmpty()){
                System.out.println("No hay ningun enunciado que pertenezca a la unidad: "+unitId);
            }
            else {
            System.out.println("\nEnunciados de la unidad " + unitId );
            for (Statement s : statements.values()) {
                System.out.printf(" - [%d] %s (Nivel: %s, Disponible: %s, Path: %s)%n",
                        s.getId(),
                        s.getDescription(),
                        s.getLevel(),
                        s.isAvailability(),
                        s.getRuta());
            }
        }
        }
    }

    private static void consultSessionsByStatement(Controller Lcontroler) {
        boolean datosValidos = true;
        int statementId = -1;
        System.out.println("Consult sessions by statement ID: ");
        
        // Mostrar todos los statements disponibles
        Map<String, Statement> statements = Lcontroler.getAllStatement();
        if (statements.isEmpty()) {
            System.out.println("No hay statements disponibles");
            datosValidos = false;
        }

        TreeMap<Integer, Statement> byId = new TreeMap<>();
        for (Statement s : statements.values()) {
            byId.put(s.getId(), s);
        }

        if (datosValidos) {
            System.out.println("Statements disponibles:");
            for (Map.Entry<Integer, Statement> entry : byId.entrySet()) {
                int id = entry.getKey();
                Statement statement = entry.getValue();
                System.out.println(" " + id + " : " + statement.getDescription() + " (Nivel: " + statement.getLevel() + ")");
            }
        }

        
        if (datosValidos) {
            do {
                System.out.print("\nIntroduce el ID del statement: ");
                statementId = Utilidades.leerInt();
                if (!Lcontroler.CheckStatement(statementId)) {
                    System.out.println("El statement " + statementId + " no existe. Por favor, introduce un ID válido.");
                    statementId = -1; // Reset para seguir en el bucle
                }
            } while (statementId == -1);
        }

        if (datosValidos) {
            Map<String, ExamSession> sessions = Lcontroler.getSessionsFromStatement(statementId);
            if (sessions.isEmpty()) {
                System.out.println("No hay sesiones que utilicen el statement: " + statementId);
            } else {
                System.out.println("\nSesiones que utilizan el statement " + statementId + ":");
                for (ExamSession session : sessions.values()) {
                    System.out.printf(" - [%s] %s | Curso: %s | Fecha: %s%n",
                            session.getSession(),
                            session.getDescription(),
                            session.getCourse(),
                            session.getDate());
                }
            }
        }
    }

    private static void viewTextDocumentFromStatement(Controller Lcontroler) {
        boolean datosValidos = true;
        int statementId = -1;
        System.out.println("Consult sessions by statement ID: ");
        
        // Mostrar todos los statements disponibles
        Map<String, Statement> statements = Lcontroler.getAllStatement();
        if (statements.isEmpty()) {
            System.out.println("No hay statements disponibles");
            datosValidos = false;
        }

        TreeMap<Integer, Statement> byId = new TreeMap<>();
        for (Statement s : statements.values()) {
            byId.put(s.getId(), s);
        }

        if (datosValidos) {
            System.out.println("Statements disponibles:");
            for (Map.Entry<Integer, Statement> entry : byId.entrySet()) {
                int id = entry.getKey();
                Statement statement = entry.getValue();
                System.out.println(" " + id + " : " + statement.getDescription() + " (Nivel: " + statement.getLevel() + ")");
            }
        }

        
        if (datosValidos) {
            do {
                System.out.print("\nIntroduce el ID del statement: ");
                statementId = Utilidades.leerInt();
                if (!Lcontroler.CheckStatement(statementId)) {
                    System.out.println("El statement " + statementId + " no existe. Por favor, introduce un ID válido.");
                    statementId = -1; // Reset para seguir en el bucle
                }
            } while (statementId == -1);
        }

        if (datosValidos) {
            String ruta = Lcontroler.viewTextDocumentFromStatement(statementId);
            if (ruta == null) {
                System.out.println("No se encontró el statement con ID: " + statementId);
            } else {
                System.out.println("\nRuta del documento: " + ruta);
            }
        }
    }
}
