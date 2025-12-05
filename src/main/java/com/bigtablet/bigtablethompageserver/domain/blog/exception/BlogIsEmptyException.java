package com.bigtablet.bigtablethompageserver.domain.blog.exception;

import com.bigtablet.bigtablethompageserver.domain.blog.exception.error.BlogError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class BlogIsEmptyException extends BusinessException {

    public static final BlogIsEmptyException EXCEPTION = new BlogIsEmptyException();

    private BlogIsEmptyException() {
        super(BlogError.BLOG_IS_EMPTY);
    }

}
