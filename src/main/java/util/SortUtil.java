package util;

import org.springframework.data.domain.Sort;

public class SortUtil {

    public static Sort sortByAsc(String field) {
        return Sort.by(Sort.Direction.ASC, field);
    }

    public static Sort sortByDesc(String field) {
        return Sort.by(Sort.Direction.DESC, field);
    }

}
