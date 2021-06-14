package ua.knu.csc;

import java.util.Map;
import java.util.HashMap;

public enum Operation {
    UNDEFINED_OPERATION(-1),

    GET_ALL_CUSTOMERS_IN_ALPHABET_ORDER(0),
    GET_CUSTOMERS_WITH_CARD_NUMBER_IN_RANGE(1);

    private static final Map<Integer, Operation> operations = new HashMap<>();
    static {
        for (Operation operation : Operation.values()) {
            operations.put(operation.code, operation);
        }
    }

    private final int code;

    Operation(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Operation getOperation(int code) {
        Operation operation = operations.get(code);
        if (operation == null) {
            return Operation.UNDEFINED_OPERATION;
        } else {
            return operation;
        }
    }
}
