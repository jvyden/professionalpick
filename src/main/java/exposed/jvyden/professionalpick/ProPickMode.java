package exposed.jvyden.professionalpick;

public enum ProPickMode {
    NORMAL(0, "Normal"),
    SILK(1, "Silk Touch"),
    FORTUNE(2, "Fortune");

    int id;
    String prettyString;
    ProPickMode(int id, String prettyString) {
        this.id = id;
        this.prettyString = prettyString;
    }

    public int getID() {
        return id;
    }

    public String getPrettyString() {
        return prettyString;
    }

    public static ProPickMode valueOf(int id) {
        for (ProPickMode type : values()) {
            if (type.getID() == id) {
                return type;
            }
        }
        return null;
    }
}
