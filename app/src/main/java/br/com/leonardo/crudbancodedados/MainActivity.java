package br.com.leonardo.crudbancodedados;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import br.com.leonardo.crudbancodedados.Adapter.AdapterPessoa;
import br.com.leonardo.crudbancodedados.Banco.PessoaDAO;
import br.com.leonardo.crudbancodedados.Helper.Validate;
import br.com.leonardo.crudbancodedados.Model.Pessoa;

public class MainActivity extends AppCompatActivity {

    EditText nome;
    EditText email;
    RadioGroup sexo;
    EditText cpf;
    EditText endereco;
    PessoaDAO pessoaDAO;
    AdapterPessoa adapterPessoa;
    ListView listViewPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pessoaDAO = new PessoaDAO(MainActivity.this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obterViewCadastro();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        this.listViewPessoa = (ListView)findViewById(R.id.listView);
        montarItensListView();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    protected void obterViewCadastro() {

        View view = getLayoutInflater().inflate(R.layout.dialog_cadastro, null);

        this.nome = (EditText) view.findViewById(R.id.edtNome);
        this.email = (EditText) view.findViewById(R.id.edtEmail);
        this.sexo = (RadioGroup) view.findViewById(R.id.rgSexo);
        this.cpf = (EditText) view.findViewById(R.id.edtCpf);
        this.endereco = (EditText) view.findViewById(R.id.edtEndereco);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Dados Pessoais");
        builder.setView(view);
        builder.setPositiveButton("Gravar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //persistir os dados
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //cancelar
            }
        });

        final AlertDialog dialog = builder.create();

        dialog.show();

        Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()) {
                    salvar();
                    dialog.dismiss();
                }
            }
        });

    }

    protected void salvar() {
        long lastId;
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(this.nome.getText().toString());
        pessoa.setCpf(this.cpf.getText().toString());
        pessoa.setEndereco(this.endereco.getText().toString());
        pessoa.setEmail(this.email.getText().toString());
        pessoa.setSexo(verificaSexo());

       lastId =  pessoaDAO.inserir(pessoa);
        if(lastId == -1){
            Toast.makeText(MainActivity.this,"Erro ao persistir os dados", Toast.LENGTH_SHORT);
        }else{
            pessoa.setId(lastId);
            adapterPessoa.adicionarItemLista(pessoa);
            adapterPessoa.notifyDataSetChanged();
            apagarFormulario();
            Toast.makeText(MainActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT);
        }
    }

    protected void apagarFormulario(){
        this.nome.setText("");
        this.email.setText("");
        this.endereco.setText("");
        this.cpf.setText("");
    }

    protected String verificaSexo(){

        String sexoSelecionado;
        int idSexo = this.sexo.getCheckedRadioButtonId();

        sexoSelecionado = idSexo == R.id.rbSexoMasculino ? "M" : "F";

        return sexoSelecionado;

    }

    protected boolean valid(){

        boolean valid;

        valid = Validate.hasText(this.nome) ? true : false;
        valid = Validate.hasText(this.email) ? true : false;
        valid = Validate.hasText(this.cpf) ? true : false;
        valid = Validate.hasText(this.endereco) ? true : false;

        return valid;
    }

    private void montarItensListView(){
        List<Pessoa> listaPessoa = pessoaDAO.buscar();
        this.adapterPessoa = new AdapterPessoa(MainActivity.this,listaPessoa);
        listViewPessoa.setAdapter(this.adapterPessoa);
    }

    public void excluirItem(View view){
        final int posicao = this.listViewPessoa.getPositionForView((View)view.getParent());
        final Pessoa pessoa = (Pessoa) listViewPessoa.getItemAtPosition(posicao);

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setMessage("Deseja realmente excluir ? ");
        dialog.setTitle("Excluir");
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pessoaDAO.deletar(pessoa);
                adapterPessoa.excluirItem(posicao);
                adapterPessoa.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"Entidade excluída com sucesso",Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setNegativeButton("Não",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.create().show();
    }

}
