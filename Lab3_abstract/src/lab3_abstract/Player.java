/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3_abstract;

/**
 *
 * @author USER
 */
public class Player {
    private final String nombre;
    private int aciertos;
    
    public Player(String nombre){
        this.nombre=nombre;
        this.aciertos=0;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public int getAciertos(){
        return aciertos;
    }
    
    public void sumarAciertos(){
        aciertos++;
    }
    
}
