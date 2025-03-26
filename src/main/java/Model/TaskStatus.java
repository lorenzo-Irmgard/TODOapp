package Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum TaskStatus {
    TODO(1),
    IN_PROGRESS(2),
    DONE(3);

    private final int numberFormat;
    @Getter
    private final static List<String> possibleOptions = Arrays.asList("1", "2", "3");

    public static TaskStatus convertFromNumberToStatus(int optionInNumberFormat) {
        if (optionInNumberFormat == TODO.numberFormat) return TODO;
        if (optionInNumberFormat == IN_PROGRESS.numberFormat) return IN_PROGRESS;
        return DONE;
    }
}
