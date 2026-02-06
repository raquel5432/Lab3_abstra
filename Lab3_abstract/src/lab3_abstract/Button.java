/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3_abstract;

import javax.swing.JButton;

/**
 *
 * @author ALISSONRAQUELMARTINE
 */


public class Button extends JButton {
    private final int index;

    public Button(int index) {
        this.index = index;
        setFocusable(false);
    }

    public int getIndex() { return index; }
}