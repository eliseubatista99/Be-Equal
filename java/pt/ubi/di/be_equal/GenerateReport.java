package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GenerateReport extends AppCompatActivity {

    ListView oLV;
    DataBase oDB;
    SQLiteDatabase oSQLiteDB;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        username = getIntent().getStringExtra("username");
        oDB = new DataBase(this);
        refresh(); //atualizar a losta
        //Fecha as bases de dados
        oSQLiteDB.close();
        oDB.close();
    }

    //Função para atualizar a lista
    public void refresh(){
        oLV = findViewById(R.id.listview);
        ArrayList<String> QuestionList = new ArrayList<>();
        oSQLiteDB = oDB.getWritableDatabase();
        Cursor data = oDB.getHistory();
        int cont=1;
        //Procura no histórico as entradas de um determinado utilizador
        while(data.moveToNext()){
            if(data.getString(1).equals(username)) {
                QuestionList.add(((""+cont+"").concat(":\nQ: ")).concat(data.getString(2).concat("\nR: ").concat(data.getString(3)))); //Adicionar à lista de questões
                cont++;
            }
        }
        //Preparar o conteúdo da lista
        oLV.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, QuestionList));
        //----//
    }

    public void GenerateReport(View v){
        Intent i = new Intent(this,TheReport.class);
        i.putExtra("username",username);
        startActivity(i);
    }
}
