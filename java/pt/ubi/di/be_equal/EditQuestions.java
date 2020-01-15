package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EditQuestions extends AppCompatActivity {

    ListView oLV;
    DataBase oDB;
    SQLiteDatabase oSQLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_questions);
        oDB = new DataBase(this);
        refresh(); //atualizar a lista
        //Para permitir interagir com os items da lista, inserir um listener
        oLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent (EditQuestions.this,Edit_The_Question.class);
                String str = oLV.getItemAtPosition(position).toString();
                String [] str_array;
                str_array=str.split(" - ");
                intent.putExtra("position_check",""+str_array[0]); //envia o id do elemento para a próxima ativity
                startActivityForResult(intent,12);
            }
        });

        //Fecha as bases de dados
        oSQLiteDB.close();
        oDB.close();
    }

    //Função para atualizar a lista
    public void refresh(){
        oLV = findViewById(R.id.listview);
        ArrayList<String> QuestionList = new ArrayList<>();
        oSQLiteDB = oDB.getWritableDatabase();
        Cursor data = oDB.getQuestions();
        //Obter todas as questões, e concatenar o seu id com o nome, de modo a obter algo do tipo "1-questao1"
        while(data.moveToNext()){
            QuestionList.add((data.getString(0).concat(" - ")).concat(data.getString(1))); //Adicionar à lista de questões
        }
        //Preparar o conteúdo da lista
        oLV.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, QuestionList));
        //----//
    }

    //Ao acabar a edição de uma questão
    public void onActivityResult(int iReqCode, int iResultCode, Intent iResult){
        if((iReqCode==12) && (iResultCode == RESULT_OK)){
            refresh(); //chamar a função que atualiza a lista
        }
    }
}
