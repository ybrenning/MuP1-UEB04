package BuchstabenStat;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class BuchstabenStat {
    private Map<Character, Integer> buchstabenHaeufigkeiten;
    private long buchstabenAnzahl;

    public BuchstabenStat() {
        buchstabenHaeufigkeiten = new HashMap<>();
        this.buchstabenAnzahl = 0;
    }

    private void pruefeDateitype(String dateiname) throws FalscherDateitypException {
        String[] parts = dateiname.split("\\.");
        String filetype = parts[1].toLowerCase();
        if (! filetype.equals("txt")) {
            throw new FalscherDateitypException(dateiname);
        }
    }

    private void verarbeiteZeichen(char zeichen) throws UngueltigesZeichenException {
        if (Character.isLetter(zeichen)) {
            zeichen = Character.toUpperCase(zeichen);
            buchstabenAnzahl++;
            int count = buchstabenHaeufigkeiten.getOrDefault(zeichen, 0);
            buchstabenHaeufigkeiten.put(zeichen, count + 1);
        } else {
            if (! Character.isDefined(zeichen)) {
                throw new UngueltigesZeichenException();
            }
        }
    }

    private void verarbeiteZeile(String zeile) throws UngueltigesZeichenException {
        for (int i = 0; i < zeile.length(); i++) {
            verarbeiteZeichen(zeile.charAt(i));
        }
    }

    public void analysiere(String dateiname) throws FalscherDateitypException, UngueltigesZeichenException {
        pruefeDateitype(dateiname);

        File file = new File(dateiname);
        String line; int currentLine = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            while ((line = br.readLine()) != null) {
                currentLine++;
                verarbeiteZeile(line);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Datei konnte nicht gefunden werden");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Ein-/Ausgabefehler");
            System.exit(1);
        } catch (UngueltigesZeichenException e) {
            throw new UngueltigesZeichenException(dateiname, currentLine);
        }
    }

    public void zeigeTop10() {
        System.out.println("Buchstaben-Top 10:");

        List<Map.Entry<Character, Integer>> list = new ArrayList<>(buchstabenHaeufigkeiten.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        int i = 10;
        for (Map.Entry<Character, Integer> entry : list) {
            if (i == 1) break; i--;
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public String statistik() {
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(buchstabenHaeufigkeiten.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        String statistiken = "";
        for (Map.Entry<Character, Integer> entry : list) {
            String thisStat = String.format("%.2f", ((double) entry.getValue() / (double) buchstabenAnzahl));
            statistiken = statistiken + entry.getKey() + ": " + thisStat + "\n";
        }

        return statistiken;
    }

    public void schreibeStatistik(String dateiname) {
        File file = new File(dateiname);
        if (! file.exists()) {
            try {
                file.createNewFile();

            } catch (IOException e) {
                System.err.println("Ein-/Ausgabefehler");
                System.exit(1);
            }
        }

        try {
            Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dateiname), StandardCharsets.UTF_8));
            w.write(statistik());
            w.close();

        } catch (IOException e) {
            System.err.println("Ein-/Ausgabefehler");
            System.exit(1);
        }
    }
}
