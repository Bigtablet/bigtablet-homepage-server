package com.bigtablet.bigtablethompageserver.global.infra.gcp.exception;

import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.error.FileError;

public class FileWrongTypeException extends BusinessException {

    public static final FileWrongTypeException EXCEPTION = new FileWrongTypeException();

    private FileWrongTypeException(){
        super(FileError.FILE_WRONG_TYPE);
    }

}