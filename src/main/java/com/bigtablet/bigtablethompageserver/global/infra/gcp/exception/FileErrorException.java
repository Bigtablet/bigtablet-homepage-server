package com.bigtablet.bigtablethompageserver.global.infra.gcp.exception;

import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.error.FileError;

public class FileErrorException extends BusinessException {

    public static final FileErrorException EXCEPTION = new FileErrorException();

    private FileErrorException(){
        super(FileError.FILE_ERROR);
    }

}
