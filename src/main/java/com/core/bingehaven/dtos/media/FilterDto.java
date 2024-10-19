package com.core.bingehaven.dtos.media;

import com.core.bingehaven.enums.BnhGenres;
import com.core.bingehaven.enums.BnhKeywords;
import com.core.bingehaven.enums.SortBy;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {
    private String numberPerPage;
    private String country;
    private final String language = "en-" + country;
    private FilterBody body;

    @Data
    @NonNull
    private static class FilterBody {
        private int first;
        private String after;
        private boolean includeReleaseDates;
        private Sort sort;
        private List<BnhGenres> allGenresId;
        private Languages anyPrimaryLanguages;
        private Countries anyPrimaryCountries;
        private List<BnhKeywords> allKeywords;
        private AggregateRatingRange aggregateRatingRange;
        private RatingsCountRange ratingsCountRange;
    }

    @Data
    private static class Sort {
        private SortBy sortBy;
        private SortOrder sortOrder;
    }
    @Getter
    private enum SortOrder {
        ASC("ASC"),
        DESC("DESC");
        private final String order;

        SortOrder(String order) {
            this.order = order;
        }

        public String getSortOrder() {
            return order;
        }
    }

    @Data
    private static class Languages {
        private List<String> lang;
    }
    @Data
    private static class Countries {
        private List<String> country;
    }
    @Data
    private static class AggregateRatingRange {
        private int min;
    }
    @Data
    private static class RatingsCountRange {
        private int min;
    }
}
