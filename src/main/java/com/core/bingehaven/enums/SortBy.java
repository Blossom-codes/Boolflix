package com.core.bingehaven.enums;

import lombok.Getter;

@Getter
public enum SortBy {
    RELEASE_DATE("RELEASE_DATE"),
    YEAR("YEAR"),
    RUNTIME("RUNTIME"),
    BOX_OFFICE_GROSS_DOMESTIC("BOX_OFFICE_GROSS_DOMESTIC"),
    USER_RATING_COUNT("USER_RATING_COUNT"),
    USER_RATING("USER_RATING"),
    TITLE_REGIONAL("TITLE_REGIONAL"),
    POPULARITY("POPULARITY");

    private final String sortByField;

    SortBy(String sortByField) {
        this.sortByField = sortByField;
    }

    public String getSortByField() {
        return sortByField;
    }
}
