/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import java.time.LocalDate;
import java.util.Map;
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
    public static int menuM(){
        System.out.println(" __________\n|          |\n|   menu   |\n|__________|\n\n0.-Exit\n1.-Create a teaching unit\n2.-Create an exam session\n3.-Create an exam statement\n4.-Consult the exam statements\n5.-Consult in which sessions a specific statement has been used\n6.-View the text document associated with a statement");
        int menu=Utilidades.leerInt(0, 7);
        return menu;
    }
    public static void main(String[] args) {
        Controller Lcontroler= new Controller();
        int menu = 0;
        do {
            menu=menuM();
            switch (menu){
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
                    
                    break;
                case 5:
                    
                    break;
                case 6:
                    
                    break;
            }
        }while (menu != 0);
    }

    private static void CreateUnit(Controller Lcontroler) {
        int id;
        String acronym, title, evaluation, desc;
        char again='Y';
        do{
        System.out.println("Input an ID for the unit");
        id=Utilidades.leerInt();
        System.out.println("Input an acronym for the unit");
        acronym=Utilidades.introducirCadena();
        System.out.println("Input a title for the unit");
        title=Utilidades.introducirCadena();
        System.out.println("Input an evaluation for the unit");
        evaluation=Utilidades.introducirCadena();
        System.out.println("Input a description for the unit");
        desc=Utilidades.introducirCadena();
        
        TeachingUnit tu =new TeachingUnit(id,acronym,title,evaluation,desc);
        
        if (Lcontroler.createUnit(tu)){
            System.out.println("Unit created Succesfully");
        }else{
            System.err.println("An unexpected ERROR ocurred while creating this unit");
        }
            System.out.println("Do you want to create aothe unit?(Y/N)");
            again=Utilidades.leerChar('Y', 'N');
        }while(again=='Y');
    }

    private static void CreateSession(Controller Lcontroler) {
        String eSession,description,course;
        Date date;
        char again='Y';
        int statementId = 0;
        boolean statementValid = false;
        boolean sessionValid = false;
        do{
            do {
                System.out.println("Input an exam session");
                eSession=Utilidades.introducirCadena();
                sessionValid = CheckSession(Lcontroler, eSession);
            } while (!sessionValid);
        System.out.println("Input a description");
        description=Utilidades.introducirCadena();
        System.out.println("Input a course");
        course=Utilidades.introducirCadena();
        System.out.println("Input a date (yyyy/MM/dd)");
        LocalDate localDate = Utilidades.leerFechaAMD();
        System.out.println("Input a statement ID");
        getAllStatement(Lcontroler, statementId);
        do{
        statementId = Utilidades.leerInt();
        if (Lcontroler.CheckStatement(statementId)){
            statementValid = true;
        }else{
            System.err.println("Statement not found. Please try again.");
        }
        }while (!statementValid);             

        // Convertir LocalDate a Date
        date = Date.valueOf(localDate);

        ExamSession session = new ExamSession(eSession, description, date, course);

        if (Lcontroler.createSession(session)){
            System.out.println("Session created Successfully");
        }else{
            System.err.println("An unexpected ERROR occurred while creating this session");
        }
            System.out.println("Do you want to create another session?(Y/N)");
            again=Utilidades.leerChar('Y', 'N');
        }while(again=='Y');
    }
    
    private static void CreateStatement(Controller Lcontroler) {
        int id = 0;
        String description,path;
        Level level;
        boolean availability;
        boolean statementValid = false;
        char again='Y';
        do{
            do{
                System.out.println("Input an ID for the statement");
                id=Utilidades.leerInt();
                statementValid = CheckStatement(Lcontroler, id);
            }while (!statementValid);
            System.out.println("Input a description for the statement");
            description=Utilidades.introducirCadena();
            System.out.println("Input a level for the statement (ALTA, MEDIA, BAJA)");
            level=Level.valueOf(Utilidades.introducirCadena().toUpperCase());
            System.out.println("Input availability for the statement (Y/N)");
            availability=Utilidades.leerChar('Y', 'N') == 'Y';
            System.out.println("Input a path for the statement");
            path=Utilidades.introducirCadena();
                
            Statement statement = new Statement(id, description, level, availability, path);
            
            if (Lcontroler.createStatement(statement)){
                System.out.println("Statement created Successfully");
            }else{
                System.err.println("An unexpected ERROR occurred while creating this statement");
            }
            System.out.println("Do you want to create another statement?(Y/N)");
            again=Utilidades.leerChar('Y', 'N');
        }while(again=='Y');
    }

    private static void consultAllSessions(Controller Lcontroler) {
        Map<String, ExamSession> sessions = Lcontroler.consultAllSessions();
        for (ExamSession session : sessions.values()) {
            System.out.println(session);
        }
    }
    private static void getAllTeachingUnits(Controller Lcontroler, int id) {
        Map<String, TeachingUnit> units = Lcontroler.getAllTeachingUnits(id);
        for (TeachingUnit unit : units.values()) {
            System.out.println(unit);
        }
    }

    private static void getAllStatement(Controller Lcontroler, int id) {
        Map<String, Statement> statements = Lcontroler.getAllStatement(id);
        for (Statement statement : statements.values()) {
            System.out.println(statement);
        }
    }
    private static boolean CheckTeachingUnit(Controller Lcontroler, int id) {
        return Lcontroler.CheckTeachingUnit(id);
    }
    private static boolean CheckStatement(Controller Lcontroler, int id) {
        return Lcontroler.CheckStatement(id);
    }
    private static boolean CheckSession(Controller Lcontroler, String session) {
        return Lcontroler.CheckSession(session);
    }

}