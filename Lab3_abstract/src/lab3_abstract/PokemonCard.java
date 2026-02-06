/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3_abstract;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author ALISSONRAQUELMARTINE
 */
public class PokemonCard extends Card {
    private final String resourcePath;

    public PokemonCard(String id, String resourcePath) {
        super(id);
        this.resourcePath = resourcePath;
    }

    @Override
    public ImageIcon getFaceIcon(int size) {
        try {
            ImageIcon raw = new ImageIcon(getClass().getResource(resourcePath));
            Image scaled = raw.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            // Si el recurso no existe o falla, no revienta el programa
            return null;
        }
    }
}