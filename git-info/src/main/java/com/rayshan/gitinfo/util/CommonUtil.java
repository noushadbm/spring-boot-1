package com.rayshan.gitinfo.util;

import com.rayshan.gitinfo.clients.github.model.RepoDetails;
import com.rayshan.gitinfo.constants.CommonConstants;
import com.rayshan.gitinfo.model.ListGitRepoRequest;
import com.rayshan.gitinfo.validation.OrderEnum;
import com.rayshan.gitinfo.validation.SortByEnum;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class CommonUtil {
    public static List<RepoDetails> sortRepoDetailsList(
            List<RepoDetails> repoDetailsList, ListGitRepoRequest request) {
        boolean isOrderDescend =
                null != request.getOrder() && request.getOrder().equals(OrderEnum.desc.toString());

        // Return default list in case sort not required.
        if (null == request.getSortBy()) return repoDetailsList;

        SortByEnum sortByEnum = SortByEnum.valueOf(request.getSortBy());
        switch (sortByEnum) {
            case NAME:
                sortRepoDetailsListByName(repoDetailsList, isOrderDescend);
                break;
            case CREATED_DATE:
                sortRepoDetailsListByCreateDate(repoDetailsList, isOrderDescend);
                break;
            case MODIFIED_DATE:
                sortRepoDetailsListByByUpdateDate(repoDetailsList, isOrderDescend);
                break;
        }

        return repoDetailsList;
    }

    /**
     * Sort based on repo uodated_at timestamp
     *
     * @param repoDetailsList
     * @param isOrderDescend
     */
    private static void sortRepoDetailsListByByUpdateDate(
            List<RepoDetails> repoDetailsList, boolean isOrderDescend) {

        Comparator<RepoDetails> comparator =
                (details1, details2) -> {
                    LocalDateTime date1 = parseToTimestamp(details1.getUpdatedAt());
                    LocalDateTime date2 = parseToTimestamp(details2.getUpdatedAt());
                    return date1.compareTo(date2);
                };

        sortUsingComparator(repoDetailsList, isOrderDescend, comparator);
    }

    /**
     * Sort based on repo created_at timestamp
     *
     * @param repoDetailsList
     * @param isOrderDescend
     */
    private static void sortRepoDetailsListByCreateDate(
            List<RepoDetails> repoDetailsList, boolean isOrderDescend) {

        Comparator<RepoDetails> comparator =
                (details1, details2) -> {
                    LocalDateTime date1 = parseToTimestamp(details1.getCreatedAt());
                    LocalDateTime date2 = parseToTimestamp(details2.getCreatedAt());
                    return date1.compareTo(date2);
                };

        sortUsingComparator(repoDetailsList, isOrderDescend, comparator);
    }

    /**
     * Sort based on given comparator
     *
     * @param repoDetailsList
     * @param isOrderDescend
     * @param comparator
     */
    private static void sortUsingComparator(
            List<RepoDetails> repoDetailsList,
            boolean isOrderDescend,
            Comparator<RepoDetails> comparator) {
        if (isOrderDescend) {
            repoDetailsList.sort(comparator.reversed());
        } else {
            repoDetailsList.sort(comparator);
        }
    }

    /**
     * Sort based on repo name.
     *
     * @param repoDetailsList
     * @param isOrderDescend
     * @return
     */
    public static void sortRepoDetailsListByName(
            List<RepoDetails> repoDetailsList, boolean isOrderDescend) {
        Comparator<RepoDetails> comparator = Comparator.comparing(RepoDetails::getName);

        sortUsingComparator(repoDetailsList, isOrderDescend, comparator);
    }

    private static LocalDateTime parseToTimestamp(String timestampAsString) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(CommonConstants.GITHUB_API_TIMESTAMP_FORMAT);
        return LocalDateTime.from(formatter.parse(timestampAsString));
    }
}
