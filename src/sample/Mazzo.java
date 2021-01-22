package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Mazzo {

    private SimpleIntegerProperty ID_Mazzo;
    private SimpleStringProperty nome;
    private SimpleIntegerProperty noCarte;
    private SimpleBooleanProperty giocabile;
    private SimpleIntegerProperty black;
    private SimpleIntegerProperty white;

    public Mazzo(int ID_Mazzo, String nome, int noCarte, boolean giocabile, int black, int white) {
        this.ID_Mazzo = new SimpleIntegerProperty(ID_Mazzo);
        this.nome = new SimpleStringProperty(nome);
        this.noCarte = new SimpleIntegerProperty(noCarte);
        this.giocabile = new SimpleBooleanProperty(giocabile);
        this.black = new SimpleIntegerProperty(black);
        this.white = new SimpleIntegerProperty(white);
    }

    public void setBlack(int black) {
        this.black.set(black);
    }

    public void setWhite(int white) {
        this.white.set(white);
    }

    public int getBlack() {
        return black.get();
    }

    public int getWhite() {
        return white.get();
    }

    public int getID_Mazzo() {
        return ID_Mazzo.get();
    }

    public String getNome() {
        return nome.get();
    }

    public int getNoCarte() {
        return noCarte.get();
    }

    public boolean isGiocabile() {
        return giocabile.get();
    }

    public void setID_Mazzo(int ID_Mazzo) {
        this.ID_Mazzo.set(ID_Mazzo);
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public void setNoCarte(int noCarte) {
        this.noCarte.set(noCarte);
    }

    public void setGiocabile(boolean giocabile) {
        this.giocabile.set(giocabile);
    }
}
