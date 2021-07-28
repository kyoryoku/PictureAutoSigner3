package hotkeys;

public class Key {

    private int code;
    private String shortName;
    private String longName;

    public Key(String longName, int code, String shortName) {
        this.code = code;
        this.shortName = shortName;
        this.longName = longName;
    }

    public int getCode() {
        return code;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }
}
