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
        if (imagenFrontal != null) {
            this.setIcon(imagenFrontal);
            this.setDisabledIcon(imagenFrontal);
        } else {
            // Si la imagen falla, mostramos el nombre para saber cuál falta
            this.setText("<html><center>" + nombrePokemon + "</center></html>");
        }
        this.setEnabled(false);
    }

    @Override
    public void ocultar() {
        this.setIcon(null);
        this.setDisabledIcon(null);
        this.setText("?");
        this.setEnabled(true);
    }
}

public class PokemonGUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout = new CardLayout();
    private JTextField txtP1, txtP2;
    private JLabel lblTurno, lblPuntos1, lblPuntos2;
    private JPanel boardPanel;
    private ArrayList<CartaPokemon> todasLasCartas = new ArrayList<>();
    private int puntosP1 = 0, puntosP2 = 0;
    private boolean turnoP1 = true;
    private CartaPokemon primeraSeleccionada, segundaSeleccionada;

    // Arreglo con los 18 nombres exactos de tus archivos
    // Arreglo actualizado con los nombres exactos de tu lista de 18 imágenes
    private String[] imagenes = {
        "Bulbasor.png",
        "Mew.jpeg",
        "arckbot.png",
        "charmander.png",
        "clefair.png",
        "dewgong.png",
        "ditto.png",
        "gengar.png",
        "grimer.png",
        "guldock.jpeg",
        "hypno.png",
        "koffing.png",
        "marowak.png",
        "ninetale.png",
        "pikachu.png",
        "pinsir.png",
        "poliwhirl.png",
        "squirtle.jpeg"
    };

    public PokemonGUI() {
        setTitle("Pokemon Memory Game - 6x6");
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
        JPanel p = new JPanel(new GridLayout(6, 1, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(80, 200, 80, 200));
        txtP1 = new JTextField();
        txtP2 = new JTextField();
        JButton btnIniciar = new JButton("INICIAR");
        JButton btnSalirApp = new JButton("SALIR DEL JUEGO");

        p.add(new JLabel("Jugador 1:")); p.add(txtP1);
        p.add(new JLabel("Jugador 2:")); p.add(txtP2);
        p.add(btnIniciar);
        p.add(btnSalirApp);

        btnIniciar.addActionListener(e -> {
            if(!txtP1.getText().isEmpty() && !txtP2.getText().isEmpty()) {
                prepararTablero();
                cardLayout.show(mainPanel, "JUEGO");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa ambos nombres.");
            }
        });
        btnSalirApp.addActionListener(e -> System.exit(0));
        mainPanel.add(p, "INICIO");
    }

    private void initJuego() {
        JPanel juego = new JPanel(new BorderLayout());
        JPanel info = new JPanel(new GridLayout(1, 3));
        info.setPreferredSize(new Dimension(800, 60));
        
        lblPuntos1 = new JLabel("", SwingConstants.CENTER); 
        lblTurno = new JLabel("", SwingConstants.CENTER); 
        lblPuntos2 = new JLabel("", SwingConstants.CENTER);
        
        lblTurno.setFont(new Font("Arial", Font.BOLD, 18));
        
        info.add(lblPuntos1); info.add(lblTurno); info.add(lblPuntos2);
        
        boardPanel = new JPanel(new GridLayout(6, 6, 8, 8));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelInferior = new JPanel(new FlowLayout());
        JButton btnAbandonar = new JButton("ABANDONAR PARTIDA");
        btnAbandonar.setBackground(new Color(200, 50, 50));
        btnAbandonar.setForeground(Color.WHITE);
        btnAbandonar.addActionListener(e -> finalizarPorAbandono());
        panelInferior.add(btnAbandonar);

        juego.add(info, BorderLayout.NORTH);
        juego.add(boardPanel, BorderLayout.CENTER);
        juego.add(panelInferior, BorderLayout.SOUTH);
        mainPanel.add(juego, "JUEGO");
    }

    private void finalizarPorAbandono() {
        String abandonador = turnoP1 ? txtP1.getText() : txtP2.getText();
        String ganador = (turnoP1) ? txtP2.getText() : txtP1.getText();
        
        JOptionPane.showMessageDialog(this, 
            "El jugador " + abandonador + " se ha rendido.\n¡EL GANADOR ES " + ganador.toUpperCase() + "!", 
            "Abandono", JOptionPane.WARNING_MESSAGE);
        
        cardLayout.show(mainPanel, "INICIO");
        limpiarDatos();
    }

    private void limpiarDatos() {
        puntosP1 = 0; puntosP2 = 0;
        txtP1.setText(""); txtP2.setText("");
        primeraSeleccionada = null; segundaSeleccionada = null;
    }

    private void prepararTablero() {
        boardPanel.removeAll();
        todasLasCartas.clear();
        puntosP1 = 0; puntosP2 = 0; turnoP1 = true;
        lblTurno.setText("TURNO: " + txtP1.getText());
        actualizarScore();

        for (String imgPath : imagenes) {
            ImageIcon icon = cargarIcono(imgPath);
            // Agregamos dos cartas por cada imagen para formar el par
            todasLasCartas.add(new CartaPokemon(imgPath, icon));
            todasLasCartas.add(new CartaPokemon(imgPath, icon));
        }

        Collections.shuffle(todasLasCartas);
        for (CartaPokemon c : todasLasCartas) {
            c.addActionListener(new ClickHandler());
            boardPanel.add(c);
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private ImageIcon cargarIcono(String path) {
        try {
            java.net.URL url = getClass().getResource("/Images/" + path);
            if (url == null) return null;
            
            ImageIcon icon = new ImageIcon(url);
            // Tamaño optimizado para cuadrícula de 6x6 en ventana de 800px
            Image img = icon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) { 
            return null; 
        }
    }

    private void actualizarScore() {
        lblPuntos1.setText("<html><center><b>" + txtP1.getText() + "</b><br><font size='5'>" + puntosP1 + "</font></center></html>");
        lblPuntos2.setText("<html><center><b>" + txtP2.getText() + "</b><br><font size='5'>" + puntosP2 + "</font></center></html>");
    }

    private class ClickHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CartaPokemon seleccionada = (CartaPokemon) e.getSource();
            
            // Bloquear clics si ya hay dos cartas volteadas o si se hace clic en la misma
            if (primeraSeleccionada != null && segundaSeleccionada != null) return;
            if (seleccionada == primeraSeleccionada || !seleccionada.getText().equals("?")) return;
            
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
            // Es pareja
            if (turnoP1) puntosP1++; else puntosP2++;
            primeraSeleccionada = null; 
            segundaSeleccionada = null;
            actualizarScore();
            verificarFinJuego();
        } else {
            // No es pareja: esperar un momento y ocultar
            Timer t = new Timer(800, e -> {
                if (primeraSeleccionada != null && segundaSeleccionada != null) {
                    primeraSeleccionada.ocultar();
                    segundaSeleccionada.ocultar();
                    primeraSeleccionada = null; 
                    segundaSeleccionada = null;
                    turnoP1 = !turnoP1;
                    lblTurno.setText("TURNO: " + (turnoP1 ? txtP1.getText() : txtP2.getText()));
                }
            });
            t.setRepeats(false);
            t.start();
        }
    }

    private void verificarFinJuego() {
        if ((puntosP1 + puntosP2) == 18) { 
            String msg;
            if (puntosP1 > puntosP2) msg = "¡GANADOR: " + txtP1.getText() + "!";
            else if (puntosP2 > puntosP1) msg = "¡GANADOR: " + txtP2.getText() + "!";
            else msg = "¡ES UN EMPATE!";
            
            JOptionPane.showMessageDialog(this, "PARTIDA FINALIZADA\n" + msg);
            cardLayout.show(mainPanel, "INICIO");
            limpiarDatos();
        }
    }
}