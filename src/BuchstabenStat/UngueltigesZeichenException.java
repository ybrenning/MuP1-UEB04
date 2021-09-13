package BuchstabenStat;

public class UngueltigesZeichenException extends Exception{
    public UngueltigesZeichenException() {
        super("Ungueltiges Zeichen");
    }

    public UngueltigesZeichenException(String dateiname, int zeilennummer) {
        super("Ungueeltiges Zeichen in " + dateiname + " Zeile " + zeilennummer);
    }
}
