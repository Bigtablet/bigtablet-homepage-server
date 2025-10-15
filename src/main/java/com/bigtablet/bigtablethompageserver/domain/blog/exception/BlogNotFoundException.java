package com.bigtablet.bigtablethompageserver.domain.blog.exception;

import com.bigtablet.bigtablethompageserver.domain.blog.exception.error.BlogError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class BlogNotFoundException extends BusinessException {

    public static final BlogNotFoundException EXCEPTION = new BlogNotFoundException();

    private BlogNotFoundException(){
        super(BlogError.BLOG_NOT_FOUND);
    }

}