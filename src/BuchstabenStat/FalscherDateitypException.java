package BuchstabenStat;

public class FalscherDateitypException extends Exception{
    public FalscherDateitypException(String dateiname) {
        super(dateiname + " hat eine nicht kompatible Endung.");
    }
}
