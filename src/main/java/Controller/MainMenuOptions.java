package Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum MainMenuOptions {
    LIST_ALL_TASKS(1),
    ADD(2),
    DELETE(3),
    EDIT(4),
    FILTER(5),
    SORT(6),
    EXIT(7);

    private final int numberFormat;
    @Getter
    private final static List<String> possibleOptions = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
}
