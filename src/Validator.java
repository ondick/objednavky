import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private Pattern pName = Pattern.compile("^[A-ZÁ-Ž][a-zá-ž]+ [A-ZÁ-Ž][a-zá-ž]+$");
    private Pattern pEmail = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private Pattern pPrice = Pattern.compile("^(0|[1-9]\\d*),\\d{2}$");
    private Pattern pKusy = Pattern.compile("^\\d+$");

    public boolean validujRadek(String radek, List<String> chyby, List<Zakaznik> platniObjednavky) {
        // limit -1 zajistí, že nepřehlédne prázdné hodnoty na konci řádku
        String[] casti = radek.split(";", -1);

        if (casti.length != 5) {
            chyby.add("neplatný počet údajů na řádku");
            return false;
        }

        String jmenoPrijmeni = casti[0].trim();
        String email = casti[1].trim();
        String produkt = casti[2].trim();
        String price = casti[3].trim();
        String pocetKusu = casti[4].trim();

        boolean ok = true;

        Matcher mJmeno = pName.matcher(jmenoPrijmeni);
        if (!mJmeno.matches()) {
            chyby.add("neplatné jméno");
            ok = false;
        }

        Matcher mEmail = pEmail.matcher(email);
        if (!mEmail.matches()) {
            chyby.add("neplatny email");
            ok = false;
        }

        // Přesná kontrola povolených produktů
        if (!produkt.equals("Herní myš") &&
                !produkt.equals("Klávesnice") &&
                !produkt.equals("Monitor") &&
                !produkt.equals("Grafická karta")) {
            chyby.add("neplatny produkt");
            ok = false;
        }

        Matcher mPrice = pPrice.matcher(price);
        if (!mPrice.matches()) {
            chyby.add("neplatny price");
            ok = false;
        }

        Matcher mKusy = pKusy.matcher(pocetKusu);
        if (!mKusy.matches()) {
            chyby.add("neplatny kusy");
            ok = false;
        } else if (Integer.parseInt(pocetKusu) < 1 || Integer.parseInt(pocetKusu) > 10) {
            chyby.add("neplatny pocet kusu");
            ok = false;
        }

        // Pokud je řádek zcela v pořádku, vytvoříme objekt Zakaznik a přidáme ho do seznamu
        if (ok) {
            double cenaCislo = Double.parseDouble(price.replace(',', '.'));
            int kusyCislo = Integer.parseInt(pocetKusu);
            platniObjednavky.add(new Zakaznik(jmenoPrijmeni, email, produkt, cenaCislo, kusyCislo));
        }

        return ok;
    }
}