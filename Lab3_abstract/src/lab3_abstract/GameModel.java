/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3_abstract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author ALISSONRAQUELMARTINE
 */


public abstract class GameModel implements Acciones, Turnos {

    private final Player p1;
    private final Player p2;
    private Player current;

    private final List<Card> deck;

    public GameModel(String player1, String player2) {
        this.p1 = new Player(player1);
        this.p2 = new Player(player2);
        this.current = p1;

        this.deck = buildDeck6x6();
        Collections.shuffle(this.deck);
    }

    public void startGame() {
        current = p1;
    }

    public void endGame() {
        
    }

    public Player getCurrentPlayer() { return current; }

    public Player getOtherPlayer() { return (current == p1) ? p2 : p1; }

    public void switchTurn() { current = getOtherPlayer(); }

    public Player getP1() { return p1; }
    public Player getP2() { return p2; }

    public List<Card> getDeck() { return deck; }

    public boolean isMatch(Card a, Card b) {
        return a != null && b != null && a.getId().equals(b.getId());
    }

    public boolean isGameOver() {
        for (Card c : deck) {
            if (!c.isRevealed()) return false;
        }
        return true;
    }

    
    private List<Card> buildDeck6x6() {
        List<Card> deckLocal = new ArrayList<>();

        try {
            List<String[]> pokemons = new ArrayList<>();

            
            pokemons.add(new String[]{"Bulbasaur",  "/Images/Bulbasor.png"});
            pokemons.add(new String[]{"Arbok",      "/Images/arckbot.png"});
            pokemons.add(new String[]{"Charmander","/Images/charmander.png"});
            pokemons.add(new String[]{"Clefairy",   "/Images/clefair.png"});
            pokemons.add(new String[]{"Dewgong",    "/Images/dewgong.png"});
            pokemons.add(new String[]{"Ditto",      "/Images/ditto.png"});
            pokemons.add(new String[]{"Gengar",     "/Images/gengar.png"});
            pokemons.add(new String[]{"Hypno",      "/Images/hypno.png"});
            pokemons.add(new String[]{"Koffing",    "/Images/koffing.png"});
            pokemons.add(new String[]{"Marowak",    "/Images/marowak.png"});
            pokemons.add(new String[]{"Ninetales",  "/Images/ninetale.png"});
            pokemons.add(new String[]{"Pikachu",    "/Images/pikachu.png"});

            
            for (String[] p : pokemons) {
                addPair(deckLocal, p[0], p[1]);
            }

            Collections.shuffle(pokemons);
            for (int i = 0; i < 6; i++) {
                addPair(deckLocal, pokemons.get(i)[0], pokemons.get(i)[1]);
            }

            Collections.shuffle(deckLocal);

        } catch (Exception e) {
            System.out.println("Error en la creación del mazo: " + e.getMessage());
        }

        return deckLocal;
    }

    private void addPair(List<Card> deck, String id, String path) {
        deck.add(new PokemonCard(id, path));
        deck.add(new PokemonCard(id, path));
    }
}
