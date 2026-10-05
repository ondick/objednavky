public class Zakaznik {

    private String jmeno;
    private String email;
    private String produkt;
    private double cena;
    private int pocetKusu;

    public Zakaznik(String jmeno, String email, String produkt, double cena, int pocetKusu) {
        this.jmeno = jmeno;
        this.email = email;
        this.produkt = produkt;
        this.cena = cena;
        this.pocetKusu = pocetKusu;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getEmail() {
        return email;
    }

    public String getProdukt() {
        return produkt;
    }

    public double getCena() {
        return cena;
    }

    public int getPocetKusu() {
        return pocetKusu;
    }

    /**
     * Vrátí celkovou cenu objednávky (cena za kus * počet kusů)
     */
    public double getCelkovaCena() {
        return cena * pocetKusu;
    }

    @Override
    public String toString() {
        return "Zakaznik{" +
                "jmeno='" + jmeno + '\'' +
                ", email='" + email + '\'' +
                ", produkt='" + produkt + '\'' +
                ", cena=" + String.format("%.2f", cena) + " Kč" +
                ", pocetKusu=" + pocetKusu +
                '}';
    }
}