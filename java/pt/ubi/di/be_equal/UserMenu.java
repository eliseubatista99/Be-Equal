package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class UserMenu extends AppCompatActivity {

    DataBase oDB;
    SQLiteDatabase oSQLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
    }

    //Quando um dos botões é pressionado..
    public void selectButton(View v){
        Intent iselectButton;

        //Switch para realizar uma ação consoante o id do botão pressionado
        switch(v.getId()){ //Verificar qual dos botões foi clicado
            //Se o botão iniciar questionário recebeu um clique
            case R.id.start_questions:
                iselectButton=new Intent(this,StartQuestions.class);
                iselectButton.putExtra("username",getIntent().getStringExtra("username"));
                break;
            //Se o botão gerar relatório recebeu um clique
            case R.id.relatorio:
                iselectButton=new Intent(this,GenerateReport.class);
                iselectButton.putExtra("username",getIntent().getStringExtra("username"));
                break;
            default:
                iselectButton=new Intent(this,UserMenu.class);
                break;
        }
        //Ir para a nova atividade
        startActivity(iselectButton);
    }
}
