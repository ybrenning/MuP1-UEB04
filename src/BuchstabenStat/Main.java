package BuchstabenStat;

public class Main {
    public static void main(String[] args) {
        BuchstabenStat buchstabenStat = new BuchstabenStat();
        try {
            buchstabenStat.analysiere("Faust.txt");
            buchstabenStat.zeigeTop10();
            buchstabenStat.schreibeStatistik("FaustStat.txt");

        } catch (FalscherDateitypException | UngueltigesZeichenException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}