package es.ediae.master.programacion.gestionusuario.exception;

import es.ediae.master.programacion.gestionusuario.constant.GeneralConstant;

public class UsuarioNoValidoException extends GeneralException {

    public UsuarioNoValidoException() {
        super(GeneralConstant.USUARIO_NO_VALIDO_ERROR_CODE, GeneralConstant.USUARIO_NO_VALIDO_ERROR_MESSAGE);
    }

}
