/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3_abstract;

import javax.swing.ImageIcon;

/**
 *
 * @author ALISSONRAQUELMARTINE
 */
public abstract class Card {
    protected boolean revealed = false;
    protected final String id;

    protected Card(String id) {
        this.id = id;
    }

    public String getId() { return id; }
    public boolean isRevealed() { return revealed; }

    public void reveal() { revealed = true; }
    public void hide() { revealed = false; }

   
    public abstract ImageIcon getFaceIcon(int size);
}