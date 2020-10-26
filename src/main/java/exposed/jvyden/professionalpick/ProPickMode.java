package exposed.jvyden.professionalpick;

public enum ProPickMode {
    NORMAL(0),
    SILK(1),
    FORTUNE(2);

    int id;
    ProPickMode(int i) {
        id = i;
    }

    public int getID() {
        return id;
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
