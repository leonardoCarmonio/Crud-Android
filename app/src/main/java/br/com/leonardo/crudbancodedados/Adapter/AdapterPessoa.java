package br.com.leonardo.crudbancodedados.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import br.com.leonardo.crudbancodedados.MainActivity;
import br.com.leonardo.crudbancodedados.Model.Pessoa;
import br.com.leonardo.crudbancodedados.R;

/**
 * Created by Leonardo on 10/09/2016.
 */
public class AdapterPessoa extends BaseAdapter {

    private MainActivity context;
    private List<Pessoa> listPessoa;

    public AdapterPessoa(MainActivity context, List<Pessoa> listPessoa){

        this.context = context;
        this.listPessoa = listPessoa;
    }

    @Override
    public int getCount() {

       return listPessoa != null ? listPessoa.size() : 0;
    }

    @Override
    public Object getItem(int position) {

        return listPessoa.get(position);
    }

    @Override
    public long getItemId(int position) {

        return listPessoa.get(position).getId();
    }

    public void excluirItem(int position){
        this.listPessoa.remove(position);
    }

    public void adicionarItemLista(Pessoa pessoa){
        this.listPessoa.add(pessoa);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(this.context).inflate(R.layout.adapter_lista,parent,false);

        TextView nome = (TextView)view.findViewById(R.id.txtNome);
        TextView email = (TextView)view.findViewById(R.id.txtEmail);
        TextView sexo = (TextView)view.findViewById(R.id.txtSexo);
        TextView cpf = (TextView)view.findViewById(R.id.txtCpf);
        TextView endereco =  (TextView)view.findViewById(R.id.txtEndereco);
        ImageButton btnExcluir = (ImageButton)view.findViewById(R.id.btnExcluir);

        Pessoa pessoa  = listPessoa.get(position);

        nome.setText("Nome: "+pessoa.getNome());
        email.setText("Email: "+pessoa.getEmail());
        sexo.setText("Sexo: "+ (pessoa.getSexo() == "F" ? "Feminino" : "Masculino" ));
        cpf.setText("CPF: "+pessoa.getCpf());
        endereco.setText("Endereço: "+pessoa.getEndereco());

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.excluirItem(v);
            }
        });

        return view;
    }
}
