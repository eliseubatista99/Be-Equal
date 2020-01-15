package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText password;
    private EditText username;
    public SQLiteDatabase oSQLiteDB;
    private DataBase oDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Database
        oDB = new DataBase(getApplicationContext());
    }

    public void onLogin(View v) {
        //Database
        oSQLiteDB = oDB.getReadableDatabase();
        //-----------------------------------
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        //String auxiliar para verificar a palavra-passe
        String tmp_pass="";
        //----------------------------------------------
        //Variável auxiliar para verificar o tipo de utilizador (1-admin, 0 -user)
        int tipo=0;
        //------------------------------------------------------------------------
        //Vai buscar todos os Logins
        Cursor data = oDB.getLogins();
        //--------------------------
        //Variável auxiliar para verificar a existência do utilizador
        int cont = 0;
        //------------------------------------------------------------
        //Ciclo para verificar se o utilizador se encontra na base de dados (tabela de logins)
        //data.moveToFirst();
        while(data.moveToNext()){
            //Se for encontrado um utilizador com o nome inserido...
            if(data.getString(0).equals(username.getText().toString())){
                cont=cont+1; //aumentar o contador
                tmp_pass=data.getString(1); //guardar a palavra-passe
                tipo=data.getInt(2); //guardar o tipo de utilizador
            }
        }
        //-------------------------------------------------------------------------------------
        //Se não for encontrado nenhum utilizador...
        if(cont==0){
            //Apresentar um toast de erro
            Toast.makeText(getApplicationContext(), "Username Inválido!", Toast.LENGTH_SHORT).show();
        }
        //Caso tenha sido encontrado um utilizador
        else{
            //Se a password for correta
            if(tmp_pass.equals(password.getText().toString())){
                //Apresentar um toast de sucesso
                Toast.makeText(getApplicationContext(), "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent iType;
                //Se for um admin...
                if(tipo==1){
                    iType = new Intent( this, AdminMenu.class);
                    oDB.close();
                    //Fechar atividade
                }
                //Se for um utilizador..
                else{
                    iType = new Intent(this,UserMenu.class);
                    iType.putExtra("username",username.getText().toString());
                    oDB.close();
                    //Fechar atividade
                }
                //Começar nova atividade
                startActivity(iType);
                super.finish();
            //Se a password for inválida..
            } else {
                //Apresentar toast de erro
                Toast.makeText(getApplicationContext(), "Palavra-Passe Inválida!", Toast.LENGTH_SHORT).show();
            }
        }
        //------------------------------------------------------------------------------------------
    }
}

