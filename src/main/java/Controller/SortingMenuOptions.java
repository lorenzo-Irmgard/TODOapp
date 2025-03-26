package Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum SortingMenuOptions {
    SORT_BY_STATUS(1),
    SORT_BY_DEADLINE(2);

    private final int optionInNumberFormat;
    @Getter
    private final static List<String> possibleOptions = Arrays.asList("1", "2");
}
