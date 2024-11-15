package com.core.bingehaven.dtos.media;

import lombok.Data;

import java.util.List;

@Data
public class KeywordResponse {


    private ResponseData data;

    @Data
    public static class ResponseData {
        private Title title;
    }

    @Data
    public static class Title {
        private String id;
        private TitleType titleType;
        private List<KeywordItemCategory> keywordItemCategories;
    }

    @Data
    public static class TitleType {
        private String id;
    }

    @Data
    public static class KeywordItemCategory {
        private ItemCategory itemCategory;
        private Keywords keywords;
    }

    @Data
    public static class ItemCategory {
        private String id;
        private String itemCategoryId;
        private String text;
    }

    @Data
    public static class Keywords {
        private String __typename;
        private int total;
        private List<Edge> edges;
    }

    @Data
    public static class Edge {
        private Node node;
    }

    @Data
    public static class Node {
        private Keyword keyword;
    }

    @Data
    public static class Keyword {
        private String id;
        private Text text;
        private Category category;
    }

    @Data
    public static class Text {
        private String text;
    }

    @Data
    public static class Category {
        private String id;
    }

}
