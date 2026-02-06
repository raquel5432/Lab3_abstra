package lab3_abstract;

import javax.swing.SwingUtilities;

public class Lab3_abstract {
    
    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    PokemonGUI gui = new PokemonGUI();
                    gui.setVisible(true);
                    
                } catch (Exception e) {
                    System.out.println("Error al iniciar la aplicación: " + e.getMessage());
                }
            }
        });
    }
}