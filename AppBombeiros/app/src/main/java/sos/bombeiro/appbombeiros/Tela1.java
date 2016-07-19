package sos.bombeiro.appbombeiros;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import dao.CadastroDAO;


public class Tela1 extends ActionBarActivity {

    private EditText ednome, ednsc, edcpf, edmae;
    private Button btconcluir;
    private CadastroDAO conecte;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela1);

        final EditText campo_cpf = (EditText) findViewById(R.id.editcpf);
        //utilização de máscaras na aplicação no campo cpf
        campo_cpf.addTextChangedListener(Mask.insert("###.###.###-##", campo_cpf));

        final EditText campo_data_nascimento = (EditText) findViewById(R.id.editnsc);
        //utilização de mascaras na aplicação na data de nascimento
        campo_data_nascimento.addTextChangedListener(Mask.insert("##/##/####", campo_data_nascimento));



        //recupera campos de texto
        ednome = (EditText) findViewById(R.id.editNome);
        ednsc = (EditText) findViewById(R.id.editnsc);
        edcpf = (EditText) findViewById(R.id.editcpf);
        edmae = (EditText) findViewById(R.id.editmae);
        //criaçao do banco atraves do objeto
        conecte = new CadastroDAO(this);


        //metodo OnClick para funcionamento do botao
        btconcluir = (Button) findViewById(R.id.concluir);
        btconcluir.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Variaveis tipo String para validação por equals
                String nome = ednome.getText().toString();
                String nsc = ednsc.getText().toString();
                String cpf = edcpf .getText().toString();
                String mae = edmae.getText().toString();
                //variaveis tipo boolean para comparação e validação dos valores
                boolean cpf_valido = Validator.validateCPF(campo_cpf.getText().toString());
                boolean validar = true;
                //condições para validação
                if(nome == null || nome.equals("")){
                    validar = false;
                    ednome.setError(getString(R.string.error_nome));
                }
                if(nsc == null || nsc.equals("")){
                    validar = false;
                    ednsc.setError(getString(R.string.error_nsc));
                }
                if(cpf == null || cpf.equals("")){
                    validar = false;
                    edcpf.setError(getString(R.string.error_cpf1));
                }
                if(!cpf_valido){
                    edcpf.setError(getString(R.string.error_cpf2));
                    edcpf.setFocusable(true);
                    edcpf.requestFocus();
                }
                if(mae == null || mae.equals("")){
                    validar = false;
                    edmae.setError(getString(R.string.error_mae));
                }
                if(validar && cpf_valido){
                    if( conecte.logar(nome, nsc, cpf, mae)){
                        Intent trocatela = new
                        Intent(Tela1.this, Tela2.class);
                        Tela1.this.startActivity(trocatela);
                        Tela1.this.finish();
                    }
                }
            }
        });
    }

}