package Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum TaskEditingMenuOptions {
    EDIT_NAME(1),
    EDIT_STATUS(2),
    EDIT_DESCRIPTION(3),
    EDIT_DEADLINE(4),
    EDIT_ALL_FIELDS(5),
    EXIT_EDITING_TASK(6);

    private final int optionInNumberFormat;
    @Getter
    private final static List<String> possibleOptions = Arrays.asList("1", "2", "3", "4", "5", "6");
}