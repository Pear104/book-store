package models.favorite;

import models.SortOrder;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;

public class FavoriteQuery {
    private final SearchParam searchBy;
    private final String keyword;
    private final SortParam sortBy;
    private final SortOrder sortOrder;
    private final int page;
    private final int limit;

    public FavoriteQuery(@Nullable SearchParam searchBy, @Nullable String keyword,
                         @Nullable SortParam sortBy, @Nullable SortOrder sortOrder,
                         int page, int limit) {
        Validate.isTrue(page > 0);
        if (searchBy == null || keyword == null) {
            searchBy = null;
            keyword = null;
        }
        if (sortBy == null || sortOrder == null) {
            sortBy = null;
            sortOrder = null;
        }
        this.searchBy = searchBy;
        this.keyword = keyword;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = page;
        this.limit = Math.max(0, Math.min(50, limit));
    }

    public @Nullable SearchParam getSearchBy() {
        return searchBy;
    }

    public @Nullable String getKeyword() {
        return keyword;
    }

    public @Nullable SortParam getSortBy() {
        return sortBy;
    }

    public @Nullable SortOrder getSortOrder() {
        return sortOrder;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public int getStartId() {
        return (page - 1) * limit;
    }

    public int getEndId() {
        return page * limit - 1;
    }

    public enum SearchParam {

    }

    public enum SortParam {
        CREATED_AT
    }
}
