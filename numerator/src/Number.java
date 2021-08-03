public class Number {
    private String name;
    private int value;
    private int delta;
    private Operation operation;

    public Number(String numeratorString) {
        int start = 2;
        int end = numeratorString.length() - 1;
        int operationPos = 0;


        if (numeratorString.contains("+")){
            operationPos = numeratorString.indexOf("+");
            this.name = numeratorString.substring(start, operationPos);
            this.value = 0;
            this.operation = Operation.INCREASE;
            this.delta = Integer.parseInt(numeratorString.substring(operationPos + 1, end));
        } else if (numeratorString.contains("-")){
            operationPos = numeratorString.indexOf("-");
            this.name = numeratorString.substring(start, operationPos);
            this.value = 0;
            this.operation = Operation.DECREASE;
            this.delta = Integer.parseInt(numeratorString.substring(operationPos + 1, end));
        } else if (numeratorString.contains("=")){
            operationPos = numeratorString.indexOf("=");
            this.name = numeratorString.substring(start, operationPos);
            this.value = Integer.parseInt(numeratorString.substring(operationPos + 1, end));
            this.operation = Operation.SET;
            this.delta = Integer.parseInt(numeratorString.substring(operationPos + 1, end));
        } else {
            this.name = numeratorString.substring(start, end);
            this.value = 0;
            this.operation = Operation.EMPTY;
            this.delta = 0;
        }


    }

    public Number(String name, int value){
        this(name, value, Operation.EMPTY, 0);
    }

    public Number(String name, Operation operation, int delta){
        this(name, 0, operation, delta);
    }

    public Number(String name, int value, Operation operation, int delta){
        this.name = name;
        this.value = value;
        this.operation = operation;
        this.delta = delta;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getDelta() {
        return delta;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void execute(){
        switch (operation){
            case DECREASE -> value = value - delta;
            case INCREASE -> value = value + delta;
            case SET -> value = delta;
        }
    }

    @Override
    public String toString() {
        String result = "";
        switch (operation){
            case DECREASE, INCREASE, SET -> result = "${" + name + operation.toString() + delta + "}";
            case EMPTY -> result = "${" + name + "}";
        }
        return result;
    }
}
