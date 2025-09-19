/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import controller.Controller;
import model.TeachingUnit;
import utilidades.Utilidades;

/**
 *
 * @author pablo
 * 
 */
public class Main {
    public static int menuM(){
        System.out.println(" __________\n|          |\n|   menu   |\n|__________|\n\n0.-Exit\n1.-Create a teaching unit\n2.-Create an exam session\n3.-Create an exam statement\n4.-Consult the exam statements\n5.-Consult in which sessions a specific statement has been used\n6.-View the text document associated with a statement\n7.-Assign a statement to a session");
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
                    
                    break;
                case 3:
                    
                    break;
                case 4:
                    
                    break;
                case 5:
                    
                    break;
                case 6:
                    
                    break;
                case 7:
                    
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
}
