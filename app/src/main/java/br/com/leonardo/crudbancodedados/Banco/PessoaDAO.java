package br.com.leonardo.crudbancodedados.Banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.leonardo.crudbancodedados.Model.Pessoa;

/**
 * Created by Leonardo on 04/09/2016.
 */
public class PessoaDAO {

    private SQLiteDatabase db;

    public PessoaDAO(Context context){
        BancoDeDados bancoAux = new BancoDeDados(context);
        this.db = bancoAux.getWritableDatabase();
    }

    public long inserir(Pessoa pessoa){
        long lastId;
        ContentValues chave = new ContentValues();
        chave.put(PessoaContract.NOME, pessoa.getNome());
        chave.put(PessoaContract.EMAIL, pessoa.getEmail());
        chave.put(PessoaContract.CPF,pessoa.getCpf());
        chave.put(PessoaContract.ENDERECO, pessoa.getEndereco());
        chave.put(PessoaContract.SEXO, pessoa.getSexo());
        lastId = this.db.insert(PessoaContract.TABLE_NAME,null,chave);
        return lastId;
    }

    public void atualizar(Pessoa pessoa){
        ContentValues chave = new ContentValues();
        chave.put(PessoaContract.NOME, pessoa.getNome());
        chave.put(PessoaContract.EMAIL, pessoa.getEmail());
        chave.put(PessoaContract.CPF,pessoa.getCpf());
        chave.put(PessoaContract.ENDERECO, pessoa.getEndereco());
        chave.put(PessoaContract.SEXO, pessoa.getSexo());
        this.db.update(

                PessoaContract.TABLE_NAME,
                chave,
                PessoaContract._ID + "= ?",
                new String[]{""+pessoa.getId()}
        );
    }

    public void deletar(Pessoa pessoa){
        this.db.delete(
          PessoaContract.TABLE_NAME,
          PessoaContract._ID + " = ? ",
                new String[]{""+pessoa.getId()}
        );
    }

    public List<Pessoa> buscar() {
        List<Pessoa> listPessoa = new ArrayList<Pessoa>();

        String[] colunas = new String[]{
                PessoaContract._ID,
                PessoaContract.NOME,
                PessoaContract.EMAIL,
                PessoaContract.SEXO,
                PessoaContract.ENDERECO,
                PessoaContract.CPF
        };

        Cursor cursor = this.db.query(
                //Nome da tabela
                PessoaContract.TABLE_NAME,
                //Colunas que serão recuperadas
                colunas,
                //Clausula WHERE
                null,
                // Valores da clausula WHERE
                null,
                // Clausula GROUP BY
                null,
                // Clausula HAVING
                null,
                //Clausula ORDER BY
                PessoaContract.NOME + " ASC "
        );

        if(cursor.getCount() > 0){
            //Move para o primeiro resultado
            cursor.moveToFirst();
            do{

                Pessoa pessoa = new Pessoa();
                pessoa.setId(cursor.getLong(0));
                pessoa.setNome(cursor.getString(1));
                pessoa.setEmail(cursor.getString(2));
                pessoa.setSexo(cursor.getString(3));
                pessoa.setEndereco(cursor.getString(4));
                pessoa.setCpf(cursor.getString(5));

                listPessoa.add(pessoa);

            }while(cursor.moveToNext());
        }

        return  listPessoa;

    }
}
