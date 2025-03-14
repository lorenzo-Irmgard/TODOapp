package Controller;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum MenuOptions {
    LIST(1),
    ADD(2),
    DELETE(3),
    EDIT(4),
    FILTER(5),
    SORT(6),
    EXIT(7);
    private final int optionInNumberFormat;
    private final static List<String> possibleOptions = Arrays.asList("1", "2", "3", "4", "5", "6", "7");

    MenuOptions(int optionInNumberFormat) {
        this.optionInNumberFormat = optionInNumberFormat;
    }

    static List<String> getPossibleOptions() {
        return possibleOptions;
    }
}

