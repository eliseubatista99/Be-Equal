package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Erase_The_Question extends AppCompatActivity {

    private Button question;
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    public int position;
    private DataBase oDB;
    public SQLiteDatabase oSQLiteDB;
    public int number_of_answers;
    public ArrayList<String> answers;
    public String question_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erase__the__question);

        question = findViewById(R.id.question_content);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        //Prepara a visibilidade dos botões/textboxes
        answer3.setVisibility(View.INVISIBLE);
        answer4.setVisibility(View.INVISIBLE);
        position = Integer.parseInt(getIntent().getStringExtra("position_check"));
        oDB = new DataBase(this);
        oSQLiteDB = oDB.getWritableDatabase();
        question_content = "";
        answers = new ArrayList<>();
        number_of_answers = 0;
        //-----------------------------------------
        //Procura uma determinada questão na tabela de questões
        Cursor data = oDB.getQuestions();
        while (data.moveToNext()) {
            if (data.getString(0).equals("" + position)) {
                question_content = data.getString(1);
            }
        }
        data = oDB.getAnswers();
        //Procura uma determinada resposta na tabela de respostas, de acordo com a quesão obtida anteriormente
        while (data.moveToNext()) {
            if (data.getString(1).equals("" + position)) {
                answers.add(data.getString(2));
            }
        }
        number_of_answers = answers.size();
        question.setText(question_content); //Apresenta o conteúdo da questão
        //Se só houver 2 respostas
        if (answers.get(2).length() == 0 && answers.get(3).length() == 0) {
            answer1.setVisibility(View.VISIBLE);
            answer2.setVisibility(View.VISIBLE);
            answer3.setVisibility(View.INVISIBLE);
            answer4.setVisibility(View.INVISIBLE);
            answer1.setText(answers.get(0));
            answer2.setText(answers.get(1));
        //Se houver 3 respostas
        } else if (answers.get(2).length() != 0 && answers.get(3).length() == 0) {
            answer1.setVisibility(View.VISIBLE);
            answer2.setVisibility(View.VISIBLE);
            answer3.setVisibility(View.VISIBLE);
            answer4.setVisibility(View.INVISIBLE);
            answer1.setText(answers.get(0));
            answer2.setText(answers.get(1));
            answer3.setText(answers.get(2));
        //Se houver 4 respostas
        } else {
            answer1.setVisibility(View.VISIBLE);
            answer2.setVisibility(View.VISIBLE);
            answer3.setVisibility(View.VISIBLE);
            answer4.setVisibility(View.VISIBLE);
            answer1.setText(answers.get(0));
            answer2.setText(answers.get(1));
            answer3.setText(answers.get(2));
            answer4.setText(answers.get(3));
        }

    }

    //Quando é pressionado o botão eliminar..
    public void EraseQuestion(View v) {
        int id;
        oSQLiteDB = oDB.getWritableDatabase();
        int first_id = oDB.getFirstQuestionAnswers(answers.get(0), position); //Obter o id da primeira resposta para a questão a editar
        //Eliminar as respostas, seguidas da questão
        oDB.eraseAnswers(answer1.getText().toString(), first_id, position);
        oDB.eraseAnswers(answer2.getText().toString(), (first_id + 1), position);
        oDB.eraseAnswers(answer1.getText().toString(), (first_id + 2), position);
        oDB.eraseAnswers(answer2.getText().toString(), (first_id + 3), position);
        oDB.eraseQuestions(question.getText().toString(), position);
        //Close database
        oSQLiteDB.close();
        oDB.close();
        //retornar uma mensagem à atividade EditQuestions para que esta possa atualizar a lista
        Intent iResult = new Intent();
        setResult(RESULT_OK, iResult);
        Toast.makeText(getApplicationContext(), "Questão Eliminada Com Sucesso", Toast.LENGTH_SHORT).show();
        super.finish();
        }
}
