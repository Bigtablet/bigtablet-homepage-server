package com.bigtablet.bigtablethompageserver.domain.news.exception;

import com.bigtablet.bigtablethompageserver.domain.news.exception.error.NewsError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class NewsNotFoundException extends BusinessException {

    public static final NewsNotFoundException EXCEPTION = new NewsNotFoundException();

    private NewsNotFoundException() {
        super(NewsError.NEWS_NOT_FOUND);
    }

}
