package wolox.training.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Helper {

    public static PageRequest buildPaginationAndSortingRequest(Integer pageSize, Integer pageNumber, String orderBy, String sort) {

        final Integer DEFAULT_PAGE_SIZE = 3;
        final Integer DEFAULT_PAGE_NUMBER = 1;
        final String DEFAULT_ORDER_BY = "id";
        final Sort.Direction DEFAULT_SORT = Sort.Direction.ASC;

        pageSize = (pageSize == null) ? DEFAULT_PAGE_SIZE : pageSize;
        pageNumber = (pageNumber == null) ? DEFAULT_PAGE_NUMBER : pageNumber;
        orderBy = (orderBy == null) ? DEFAULT_ORDER_BY : orderBy;
        Sort.Direction sortDirection =  (sort == null) ? DEFAULT_SORT :
                (sort.toLowerCase().equals("desc")) ? Sort.Direction.DESC : DEFAULT_SORT;

        PageRequest request = new PageRequest(pageNumber - 1, pageSize, sortDirection, orderBy);
        return request;
    }
}
