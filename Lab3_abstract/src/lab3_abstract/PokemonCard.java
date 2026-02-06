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
import java.awt.Image;
import javax.swing.ImageIcon;

public class PokemonCard extends Card {

    private final String resourcePath;

    public PokemonCard(String id, String resourcePath) {
        super(id);
        this.resourcePath = resourcePath;
        this.revealed = false;
    }

    @Override
    public ImageIcon getFaceIcon(int size) {
        try {
            
            java.net.URL imgURL = getClass().getResource(resourcePath);
            if (imgURL == null) return null;

            
            ImageIcon raw = new ImageIcon(imgURL);

            
            Image scaled = raw.getImage()
                    .getScaledInstance(size, size, Image.SCALE_SMOOTH);

            return new ImageIcon(scaled);

        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + resourcePath);
            return null;
        }
    }
}
