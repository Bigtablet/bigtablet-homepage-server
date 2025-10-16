package com.bigtablet.bigtablethompageserver.global.infra.gcp.exception;

import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.error.FileError;

public class FileNotFoundException extends BusinessException {

    public static final FileNotFoundException EXCEPTION = new FileNotFoundException();

    private FileNotFoundException(){
        super(FileError.FILE_NOT_FOUND);
    }

}
