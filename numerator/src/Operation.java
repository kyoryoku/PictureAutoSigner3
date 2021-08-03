public enum Operation {
    INCREASE("+"),
    DECREASE("-"),
    SET("="),
    EMPTY("");

    private String text;
    Operation(String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
