package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button register;
    private Button more;
    protected DataBase oDB;
    private SQLiteDatabase oSQLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.Login);
        register = findViewById(R.id.Register);
        more = findViewById(R.id.more);
        //Database
        oDB = new DataBase(this);
        oSQLiteDB = oDB.getWritableDatabase();
        //----//
        //oDB.onUpgrade(oSQLiteDB,1,1);
        oDB.prepararBasedeDados();
        //Close databases
        oSQLiteDB.close();
        oDB.close();
    }

    //Função que trata das funcionalidades do botões
    public void selectButton(View v){

        Intent iselectButton;

        //Verificar qual dos botões foi clicado
        switch(v.getId()){
            //Se o botão login recebeu um clique, ir para a atividade login
            case R.id.Login:
                iselectButton=new Intent(this,Login.class);
                break;
            //Se o botão register recebeu um clique, ir para a atividade register
            case R.id.Register:
                iselectButton=new Intent(this,Register.class);
                break;
            //default, manter na mesma atividade
            case R.id.more:
                iselectButton=new Intent(this,More.class);
                break;
            //default, manter na mesma atividade
            default:
                iselectButton=new Intent(this,MainActivity.class);
                break;
        }
        //Começar nova atividade
        startActivity(iselectButton);
    }
}