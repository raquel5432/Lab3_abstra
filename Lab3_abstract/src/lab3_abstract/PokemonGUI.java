/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3_abstract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

interface AccionesCarta {
    void revelar();
    void ocultar();
}

abstract class Carta extends JButton implements AccionesCarta {
    protected String nombrePokemon;
    protected ImageIcon imagenFrontal;
    protected boolean esParejaEncontrada = false;

    public Carta(String nombre, ImageIcon imagen) {
        this.nombrePokemon = nombre;
        this.imagenFrontal = imagen;
        this.ocultar(); 
    }

    public String getNombre() { return nombrePokemon; }
}

class CartaPokemon extends Carta {
    public CartaPokemon(String nombre, ImageIcon imagen) {
        super(nombre, imagen);
    }

    @Override
    public void revelar() {
        this.setIcon(imagenFrontal);
        this.setEnabled(false); 
    }

    @Override
    public void ocultar() {
        this.setIcon(null);
        this.setText("?");
        this.setEnabled(true);
    }
}
public class PokemonGUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout = new CardLayout();
    
    // Pantalla Inicio
    private JTextField txtP1, txtP2;
    
    // Pantalla Juego
    private JLabel lblTurno, lblPuntos1, lblPuntos2;
    private JPanel boardPanel;
    private ArrayList<CartaPokemon> todasLasCartas = new ArrayList<>();
    
    // Lógica de Juego
    private int puntosP1 = 0, puntosP2 = 0;
    private boolean turnoP1 = true;
    private CartaPokemon primeraSeleccionada, segundaSeleccionada;

    private String[] imagenes = {
        "Bulbasor.png", "Mew.jfif", "arckbot.png", "charmander.png", "clefair.png", 
        "dewgong.png", "ditto.png", "gengar.png", "grimer.png", "guldock.jfif", 
        "hypno.png", "koffing.png", "marowak.png", "ninetale.png", "pikachu.png", 
        "pinsir.png", "poliwhirl.png", "squirtle.jfif"
    };

    public PokemonGUI() {
        setTitle("Pokemon Memory Game - Lab II");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(cardLayout);
        initInicio();
        initJuego();

        add(mainPanel);
        setVisible(true);
    }

    private void initInicio() {
        JPanel p = new JPanel(new GridLayout(5, 1, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(100, 250, 100, 250));
        
        txtP1 = new JTextField();
        txtP2 = new JTextField();
        JButton btn = new JButton("¡INICIAR!");

        p.add(new JLabel("Jugador 1:")); p.add(txtP1);
        p.add(new JLabel("Jugador 2:")); p.add(txtP2);
        p.add(btn);

        btn.addActionListener(e -> {
            if(!txtP1.getText().isEmpty() && !txtP2.getText().isEmpty()) {
                prepararTablero();
                cardLayout.show(mainPanel, "JUEGO");
            }
        });
        mainPanel.add(p, "INICIO");
    }

    private void initJuego() {
        JPanel juego = new JPanel(new BorderLayout());
        JPanel info = new JPanel(new GridLayout(1, 3));
        lblPuntos1 = new JLabel(); lblTurno = new JLabel(); lblPuntos2 = new JLabel();
        info.add(lblPuntos1); info.add(lblTurno); info.add(lblPuntos2);

        boardPanel = new JPanel(new GridLayout(6, 6, 5, 5));
        juego.add(info, BorderLayout.NORTH);
        juego.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(juego, "JUEGO");
    }

    private void prepararTablero() {
        boardPanel.removeAll();
        todasLasCartas.clear();
        lblTurno.setText("Turno: " + txtP1.getText());
        actualizarScore();

        for (String img : imagenes) {
            ImageIcon icon = cargarIcono(img);
            todasLasCartas.add(new CartaPokemon(img, icon));
            todasLasCartas.add(new CartaPokemon(img, icon));
        }
        Collections.shuffle(todasLasCartas);

        for (CartaPokemon c : todasLasCartas) {
            c.addActionListener(new ClickHandler());
            boardPanel.add(c);
        }
    }

    private ImageIcon cargarIcono(String path) {
        try {
            java.net.URL url = getClass().getResource("/Images/" + path);
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) { return null; }
    }

    private void actualizarScore() {
        lblPuntos1.setText(txtP1.getText() + ": " + puntosP1);
        lblPuntos2.setText(txtP2.getText() + ": " + puntosP2);
    }

    private class ClickHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CartaPokemon seleccionada = (CartaPokemon) e.getSource();
            seleccionada.revelar();

            if (primeraSeleccionada == null) {
                primeraSeleccionada = seleccionada;
            } else {
                segundaSeleccionada = seleccionada;
                verificarPareja();
            }
        }
    }

    private void verificarPareja() {
        if (primeraSeleccionada.getNombre().equals(segundaSeleccionada.getNombre())) {
            if (turnoP1) puntosP1++; else puntosP2++;
            primeraSeleccionada = null; segundaSeleccionada = null;
            actualizarScore();
        } else {
            Timer t = new Timer(700, e -> {
                primeraSeleccionada.ocultar();
                segundaSeleccionada.ocultar();
                primeraSeleccionada = null; segundaSeleccionada = null;
                turnoP1 = !turnoP1;
                lblTurno.setText("Turno: " + (turnoP1 ? txtP1.getText() : txtP2.getText()));
            });
            t.setRepeats(false);
            t.start();
        }
    }
}