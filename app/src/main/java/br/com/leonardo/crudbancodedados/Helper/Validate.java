package br.com.leonardo.crudbancodedados.Helper;

import android.widget.EditText;

/**
 * Created by Leonardo on 10/09/2016.
 */
public class Validate {

    private static final String REQUIRED_MSG = "obrigatório";
    private static final String NUMBER_POSITIVE = "Campo deve ser maior que zero";

    public static boolean hasText(EditText editText){
        String campo = editText.getText().toString().trim();
        editText.setError(null);
        if (campo.length() == 0){
            editText.setError(REQUIRED_MSG);
            return false;
        }
        return true;
    }
}

