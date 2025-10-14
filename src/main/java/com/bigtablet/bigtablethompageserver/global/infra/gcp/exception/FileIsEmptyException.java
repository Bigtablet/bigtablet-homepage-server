package com.bigtablet.bigtablethompageserver.global.infra.gcp.exception;

import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.error.FileError;

public class FileIsEmptyException extends BusinessException {

    public static final FileIsEmptyException EXCEPTION = new FileIsEmptyException();

    private FileIsEmptyException(){
        super(FileError.FILE_IS_EMPTY);
    }

}
