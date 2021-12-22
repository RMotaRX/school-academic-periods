package com.educacional.schoolacademicperiods.domain.model.impl;

import com.educacional.schoolacademicperiods.domain.model.PageDomain;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PageDomainImpl<T> implements PageDomain<T> {

    private final List<T> content;
    private final long total;
    private final long totalPages;

    @Override
    public List<T> getContent() {
        return this.content;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public long getTotalPages() {
        return this.totalPages;
    }
}
