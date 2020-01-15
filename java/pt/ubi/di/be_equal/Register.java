package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private CheckBox oCBadmin;
    private EditText admin_code;
    private EditText password;
    private EditText username;
    private int admin_checked;
    private DataBase oDB;
    public SQLiteDatabase oSQLiteDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        admin_code = findViewById(R.id.admin_code);
        admin_code.setVisibility(View.INVISIBLE); //manter a caixa de texto do código admin invisivel
        oCBadmin = findViewById(R.id.admin);
        //variável auxiliar para verificar se a checkbox foi marcada
        admin_checked = 0;
        //Database
        oDB = new DataBase(getApplicationContext());
    }

    //Sempre que há uma alteração no estado da checkbox..
    public void adminOn(View v) {
        //Se foi checked
        if (oCBadmin.isChecked()) {
            admin_code.setVisibility(View.VISIBLE); //colocar o edittext visivel
            admin_checked = 1; //alterar o valor da variável auxiliar
        }
        //Se foi unchecked
        if (!oCBadmin.isChecked()) {
            admin_code.setVisibility(View.INVISIBLE); //colocar o edittext invisivel
            admin_checked = 0; //alterar o valor da variável auxiliar
        }
    }

    //Ao pressionar o botão registar
    public void doneRegist(View v) {
        Context context = getApplicationContext();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        //Database
        oSQLiteDB = oDB.getWritableDatabase();
        //Se for um admin..
        if (admin_checked == 1) {
            //Se o edit text do código admin estiver vazio..
            if (username.getText().length() == 0 || password.getText().length() == 0) {
                Toast.makeText(context, "É necessário um username e uma password", Toast.LENGTH_SHORT).show();
            }
            else if(username.getText().length()>=17 || password.getText().length()>=17){
                Toast.makeText(context, "O número de caracteres do username/password não podem ultrapassar os 17 caracteres!", Toast.LENGTH_SHORT).show();
            }
            else {
                if (admin_code.getText().length() == 0) {
                    Toast.makeText(context, "É necessário um código de administrador!", Toast.LENGTH_SHORT).show();
                }
                //Se o edit text do código admin tiver o código errado
                else if (!admin_code.getText().toString().equals("admin")) {
                    Toast.makeText(context, "Código de Administrador Errado!", Toast.LENGTH_SHORT).show();
                }
                //Caso o código admin esteja correto
                else {
                    //Inserir na base de dados
                    boolean insertLogin = oDB.addLogin(username.getText().toString(), password.getText().toString(), 1); //tipo1
                    //Se foi inserido com sucesso, apresentar
                    if (insertLogin) {
                        //Apresenta um toast de sucesso
                        Toast.makeText(context, "Registo efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                        //Fecha a Database
                        oSQLiteDB.close();
                        oDB.close();
                        //Termina a atividade
                        super.finish();
                    }
                    //Se falhou na inserção
                    else {
                        //Apresenta um toast de erro
                        Toast.makeText(context, "Erro ao efetuar o registo!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        //Se não for um admin..
        else {
            //Insere na BD
            if (username.getText().length() == 0 || password.getText().length() == 0) {
                Toast.makeText(context, "É necessário um username e uma password!", Toast.LENGTH_SHORT).show();
            }
            else if(username.getText().length()>=17 || password.getText().length()>=17){
                Toast.makeText(context, "O número de caracteres do username/password não podem ultrapassar os 17 caracteres!", Toast.LENGTH_SHORT).show();
            }
            else {
                boolean insertLogin = oDB.addLogin(username.getText().toString(), password.getText().toString(), 0); //tipo 0
                //Se inseriu com sucesso
                if (insertLogin) {
                    //Apresenta um toast de sucesso
                    Toast.makeText(context, "Registo efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    //Fecha a Database
                    oSQLiteDB.close();
                    oDB.close();
                    //Termina a atividade
                    super.finish();
                    //Se falhou na inserção
                } else {
                    //Apresenta um toast de erro
                    Toast.makeText(context, "Erro ao efetuar o registo!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

