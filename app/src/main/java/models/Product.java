package models;

/**
 * Created by Dajana on 12.11.2017.
 */

public class Product {
    private int id;
    private String nazwa;
    private int ilosc;
    private int cena;
    private int kupiono;
    public Product(String nazwa, int kupiono){
        this.nazwa = nazwa;
        this.kupiono=kupiono;
    }

    public Product(int id, String nazwa,int ilosc, int cena, int kupiono){
        this.id = id;
        this.nazwa = nazwa;
        this.ilosc = ilosc;
        this.cena = cena;
        this.kupiono=kupiono;
    }
    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getCena() {
        return (double)cena/100;
    }

    public void setCena(double cena) {
        this.cena = (int)(cena*100);
    }

    public double getKupiono() {

        return  this.kupiono;
    }

    public void setKupiono(int kupiono) {
        this.kupiono = kupiono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
