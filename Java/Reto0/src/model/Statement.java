/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author pablo
 */
public class Statement {
    private int id;
    private String description;
    private Level level;
    private boolean availability;
    private String ruta;
    
    public Statement(){
        this.id=0;
        this.description="";
        this.level=null;
        this.availability=false;
        this.ruta="";
    }
    public Statement(int id,String description,Level level, boolean availability,String ruta){
        this.id=id;
        this.description=description;
        this.level=level;
        this.availability=availability;
        this.ruta=ruta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return "Statement{" + "id=" + id + ", description=" + description + ", level=" + level + ", availability=" + availability + ", ruta=" + ruta + '}';
    }
    
    
}
