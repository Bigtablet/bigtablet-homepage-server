package com.bigtablet.bigtablethompageserver.domain.news.exception;

import com.bigtablet.bigtablethompageserver.domain.news.exception.error.NewsError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class NewsIsEmptyException extends BusinessException {

    public static final NewsIsEmptyException EXCEPTION = new NewsIsEmptyException();

    private NewsIsEmptyException() {
        super(NewsError.NEWS_IS_EMPTY);
    }

}
