package cn.mxlog.sscloud.base;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by F.Du on 2017/9/8.
 */
public class Pager implements Pageable {
    private int pageNumber = 0;
    private int pageSize = 10;
    private int offset;

    private Sort sort;

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getOffset() {
        return this.pageNumber * this.pageSize;

    }

    @Override
    public Sort getSort() {
        if (sort == null) {
            return new Sort(Sort.Direction.DESC, "id");
        }
        return sort;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    public boolean hasPrevious() {
        return this.pageNumber > 0;
    }


    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber - 1;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
