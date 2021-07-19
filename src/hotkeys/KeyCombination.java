package hotkeys;

public class KeyCombination {

    private boolean shift;
    private boolean ctrl;
    private boolean alt;
    private Key key;

    public KeyCombination() {
        this.key = Keys.getUndefinedKey();
        this.shift = false;
        this.ctrl = false;
        this.alt = false;
    }

    public KeyCombination(int code, boolean shift, boolean ctrl, boolean alt) {
        this.key = Keys.getKeyFromCode(code);
        this.shift = shift;
        this.ctrl = ctrl;
        this.alt = alt;
    }

    public KeyCombination(String combination){
        this.key = Keys.getUndefinedKey();
        this.shift = false;
        this.ctrl = false;
        this.alt = false;

        String[] strs = combination.split("\\+");
        for (String s : strs){
            switch (s) {
                case "Ctrl" -> this.ctrl = true;
                case "Shift" -> this.shift = true;
                case "Alt" -> this.alt = true;
                default -> {
                    if (s.startsWith("VC_")){
                        this.key = Keys.getKeyFromLongName(s);
                    } else {
                        this.key = Keys.getKeyFromShortName(s);
                    }
                }
            }
        }
    }

    public String toShortString() {
        String str = key.getShortName();

        if (alt){
            str = "Alt+" + str;
        }

        if (ctrl){
            str = "Ctrl+" + str;
        }

        if (shift){
            str = "Shift+" + str;
        }

        return str;
    }

    public String toLongString() {
        String str = key.getLongName();

        if (alt){
            str = "VK_ALT+" + str;
        }

        if (ctrl){
            str = "VK_CONTROL+" + str;
        }

        if (shift){
            str = "VK_SHIFT+" + str;
        }

        return str;
    }

    public boolean equals(KeyCombination otherCombination){
        return this.toShortString().equals(otherCombination.toShortString()) &
        this.toLongString().equals(otherCombination.toLongString());
    }

    public boolean isDefined(){
        return !this.key.getShortName().equals("UNDEFINED");
    }
}
