/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
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
        
        int menu = 0;
        do {
            menu=menuM();
            switch (menu){
                case 0:
                    System.out.println("Bye!");
                    break;
                case 1:
                    
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
}
