package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Carta {

    private SimpleIntegerProperty ID_Carta;
    private SimpleStringProperty contenuto;
    private SimpleStringProperty tipologia;
    private SimpleIntegerProperty ID_Mazzo;

    public Carta(int ID_Carta, String contenuto, String tipologia, int ID_Mazzo) {
        this.ID_Carta = new SimpleIntegerProperty(ID_Carta);
        this.contenuto = new SimpleStringProperty(contenuto);
        this.tipologia = new SimpleStringProperty(tipologia);
        this.ID_Mazzo = new SimpleIntegerProperty(ID_Mazzo);
    }

    public void setID_Carta(int ID_Carta) {
        this.ID_Carta.set(ID_Carta);
    }

    public void setContenuto(String contenuto) {
        this.contenuto.set(contenuto);
    }

    public void setTipologia(String tipologia) {
        this.tipologia.set(tipologia);
    }

    public void setID_Mazzo(int ID_Mazzo) {
        this.ID_Mazzo.set(ID_Mazzo);
    }

    public int getID_Carta() {
        return ID_Carta.get();
    }

    public String getContenuto() {
        return contenuto.get();
    }

    public String getTipologia() {
        return tipologia.get();
    }

    public int getID_Mazzo() {
        return ID_Mazzo.get();
    }
}
