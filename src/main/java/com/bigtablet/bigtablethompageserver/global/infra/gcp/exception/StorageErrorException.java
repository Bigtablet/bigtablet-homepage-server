package com.bigtablet.bigtablethompageserver.global.infra.gcp.exception;

import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.error.FileError;

public class StorageErrorException extends BusinessException {

    public static final StorageErrorException EXCEPTION = new StorageErrorException();

    private StorageErrorException(){
        super(FileError.STORAGE_ERROR);
    }

}
