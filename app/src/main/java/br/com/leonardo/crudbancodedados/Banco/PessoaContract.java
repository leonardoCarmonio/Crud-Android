package br.com.leonardo.crudbancodedados.Banco;

import android.provider.BaseColumns;

/**
 * Created by Leonardo on 04/09/2016.
 */
public interface PessoaContract extends BaseColumns {

    String TABLE_NAME = "Pessoa";
    String NOME = "nome";
    String EMAIL = "email";
    String SEXO = "sexo";
    String CPF = "cpf";
    String ENDERECO = "endereco";

    String CREATE_TABLE_SQL = " CREATE TABLE " + TABLE_NAME +
            " (" + _ID      + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + NOME     + " TEXT NOT NULL, "
                 + EMAIL    + " TEXT NOT NULL, "
                 + SEXO     + " TEXT NOT NULL, "
                 + CPF      + " TEXT NOT NULL, "
                 + ENDERECO + " TEXT NOT NULL );";
}
