package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TheReport extends AppCompatActivity {

    DataBase oDB;
    SQLiteDatabase oSQLiteDB;
    public String username;
    public ArrayList<String> questions;
    public ArrayList<String> questions_id;
    public ArrayList<String> user_answers;
    public ArrayList<String> correct_answers;
    public ArrayList<String> user_answers_id;
    public ArrayList<String> correct_answers_id;
    public ListView oLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_report);
        oLV = findViewById(R.id.listview);
        oDB = new DataBase(this);
        oSQLiteDB = oDB.getWritableDatabase();
        questions = new ArrayList<>();
        questions_id = new ArrayList<>();
        user_answers = new ArrayList<>();
        user_answers_id = new ArrayList<>();
        correct_answers = new ArrayList<>();
        correct_answers_id = new ArrayList<>();
        Cursor data = oDB.getHistory();
        username = getIntent().getStringExtra("username");
        //Obter no histórico as questões e respostas dado um utilizador
        while(data.moveToNext()){
            if(data.getString(1).equals(username)) {
                questions.add(data.getString(2));
                questions_id.add(data.getString(4));
                user_answers.add(data.getString(3));
                user_answers_id.add(data.getString(5));
            }
        }
        int i=0;
        data = oDB.getQuestions();
        //Obter o id respostas corretas para as questões acima
        while(data.moveToNext()){
            if(data.getString(0).equals(questions_id.get(i))) {
                correct_answers_id.add(data.getString(2));
                i++;
            }
        }

        data = oDB.getAnswers();


        data.moveToFirst();
        data.moveToPrevious();
        int size_of_correct_answers=correct_answers_id.size();
        i=0;
        //Dado o id das respostas corretas, percorrer a tabela de respostas e obter os deus conteúdos
        while(data.moveToNext()){

            if(i==size_of_correct_answers){
                break;
            }
            else {
                if (data.getString(0).equals(correct_answers_id.get(i))) {
                    correct_answers.add(data.getString(2));
                    i++;
                }
            }
        }
        TheActualReport();


    }

    public void TheActualReport(){
        ArrayList<String> List = new ArrayList<>();
        String questao;
        String resposta_user;
        String resposta_correta;
        String report="";
        report = report+"Relatório de "+username+":\n\n";
        //Preparar uma string para relatar cada questão
        for (int i=0;i<questions_id.size();i++){

            questao = questions.get(i);
            resposta_user = user_answers.get(i);
            resposta_correta = correct_answers.get(i);

            report = report +
                    "Q: "+questao+"\n A sua resposta a esta questão foi: " +
                    resposta_user + "\n";
            if(resposta_user.equals(resposta_correta)){
                report = report + "A sua resposta vai de encontro com o que se acredita ser a melhor abordagem neste situação\n";
            }
            else{
                report = report + "Neste caso, a melhor abordagem para esta situação seria: \n" + resposta_correta +"\n -------------------------------------------------------------------------- \n" + "\n";
            }
        }
        List.add(report);
        oLV.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, List));

    }
}
