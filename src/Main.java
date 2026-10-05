import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<Zakaznik> platniObjednavky = new ArrayList<>();
        Validator validator = new Validator();

        try {
            File soubor = new File("trenink_objednavky_testovaci_data.txt");
            Scanner scanner = new Scanner(soubor);

            int cisloRadku = 1;
            while (scanner.hasNextLine()) {
                String radek = scanner.nextLine();
                if (radek.trim().isEmpty()) {
                    cisloRadku++;
                    continue;
                }

                List<String> chyby = new ArrayList<>();
                boolean jePlatny = validator.validujRadek(radek, chyby, platniObjednavky);

                if (jePlatny) {
                    System.out.println("Řádek " + cisloRadku + ": validní");
                } else {
                    System.out.println("Řádek " + cisloRadku + ": nevalidní (" + String.join(", ", chyby) + ")");
                }

                cisloRadku++;
            }
            scanner.close();

            double celkovaCenaVsech = 0.0;
            Map<String, Integer> ksPodleProduktu = new HashMap<>();

            for (Zakaznik z : platniObjednavky) {
                celkovaCenaVsech += z.getCelkovaCena();

                ksPodleProduktu.put(
                        z.getProdukt(),
                        ksPodleProduktu.getOrDefault(z.getProdukt(), 0) + z.getPocetKusu()
                );
            }

            System.out.println("\n===========================================");
            System.out.println("Celková cena všech platných objednávek: "
                    + String.format("%.2f Kč", celkovaCenaVsech));
            System.out.println("-------------------------------------------");
            System.out.println("Počet objednaných kusů podle produktů:");
            for (Map.Entry<String, Integer> entry : ksPodleProduktu.entrySet()) {
                System.out.println(" - " + entry.getKey() + ": " + entry.getValue() + " ks");
            }
            System.out.println("===========================================");

        } catch (Exception e) {
            System.out.println("Soubor se nepodařilo načíst.");
        }
    }
}