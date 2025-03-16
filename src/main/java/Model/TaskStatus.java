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

    private final int optionInNumberFormat;
    @Getter
    private final static List<String> possibleOptions = Arrays.asList("1", "2", "3");

}
