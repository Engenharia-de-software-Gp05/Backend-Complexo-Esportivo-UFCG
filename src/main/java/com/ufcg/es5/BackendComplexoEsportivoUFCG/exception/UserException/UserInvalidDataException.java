package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.UserException;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInvalidArgumentException;

public class UserInvalidDataException extends SaceInvalidArgumentException {
    public UserInvalidDataException() {super("User invalid data.");}
    public UserInvalidDataException(String msg) {super(msg);}
}
