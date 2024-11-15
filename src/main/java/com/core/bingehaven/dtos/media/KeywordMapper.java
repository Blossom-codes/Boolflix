package com.core.bingehaven.dtos.media;

import java.util.ArrayList;
import java.util.List;

public class KeywordMapper {

    // Map to KeywordDto
    public static KeywordDto mapToKeywordDto(KeywordResponse response) {
        if (response == null || response.getData() == null || response.getData().getTitle() == null) {
            return null;
        }

        List<String> keywordsList = new ArrayList<>();
        List<KeywordResponse.KeywordItemCategory> keywordCategories = response.getData().getTitle().getKeywordItemCategories();

        for (KeywordResponse.KeywordItemCategory category : keywordCategories) {
            for (KeywordResponse.Edge edge : category.getKeywords().getEdges()) {
                String keywordText = edge.getNode().getKeyword().getText().getText();
                keywordsList.add(keywordText);
            }
        }

        return new KeywordDto(keywordsList);
    }
}

