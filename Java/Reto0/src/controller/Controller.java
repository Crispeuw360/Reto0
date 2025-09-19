/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.TeachingUnit;
import model.WorkerDAO;

/**
 *
 * @author pablo
 */
public class Controller {
    WorkerDAO dao = (WorkerDAO) new model.ImplementsBD();
    
     public boolean createUnit(TeachingUnit unit){
         return dao.createUnit(unit);
     }
}
