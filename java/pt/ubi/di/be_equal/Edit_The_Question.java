package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Edit_The_Question extends AppCompatActivity {

    private EditText question;
    private EditText answer1;
    private EditText answer2;
    private EditText answer3;
    private EditText answer4;
    private Button add_3;
    private Button add_4;
    private int visibility_aux; //0 -> inicical, 1-> add_3 invisinle, answer3 visible, add_4 visible
    //2 -> add_3 invisible, add_4 invisible, ambas as answers visible
    public int position;
    private DataBase oDB;
    public SQLiteDatabase oSQLiteDB;
    public int number_of_answers;
    public ArrayList<String> answers;
    public String question_content;
    public int question_correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__the__question);

        question = findViewById(R.id.question_content);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        add_3 = findViewById(R.id.add_answer3);
        add_4 = findViewById(R.id.add_answer4);
        //Prepara a visibilidade dos botões/textboxes
        answer3.setVisibility(View.INVISIBLE);
        answer4.setVisibility(View.INVISIBLE);
        add_3.setVisibility(View.VISIBLE);
        add_4.setVisibility(View.INVISIBLE);
        answer1.setBackgroundColor(Color.parseColor("#FCFDFD"));
        answer2.setBackgroundColor(Color.parseColor("#FCFDFD"));
        answer3.setBackgroundColor(Color.parseColor("#FCFDFD"));
        answer4.setBackgroundColor(Color.parseColor("#FCFDFD"));
        visibility_aux = 0; //estado inicial da visibilidade dos butões
        position = Integer.parseInt(getIntent().getStringExtra("position_check"));
        oDB = new DataBase(this);
        oSQLiteDB = oDB.getWritableDatabase();
        question_content = "";
        answers = new ArrayList<>();
        number_of_answers = 0;
        //-----------------------------------------
        //Recebe os dados de Login
        Cursor data = oDB.getQuestions();
        //Procura na tabela de questões a questão
        while (data.moveToNext()) {
            if (data.getString(0).equals("" + position)) {
                question_content = data.getString(1);
                question_correct_answer=Integer.parseInt(data.getString(2));
            }
        }
        data = oDB.getAnswers();
        //Procura na tabela de respostas a resposta à questão devolvida anteriormente
        while (data.moveToNext()) {
            if (data.getString(1).equals("" + position)) {
                answers.add(data.getString(2));
            }
        }
        if(question_correct_answer>4){
            question_correct_answer=question_correct_answer-4;
        }
        //verifica quais das respostas é a correta e altera o conteúdo do hint dessa resposta
        if(question_correct_answer==1){
            answer1.setHint("Resposta Correta");
            answer1.setBackgroundColor(Color.parseColor("#E7C397"));
            answer2.setBackgroundColor(Color.parseColor("#FCFDFD"));
            answer3.setBackgroundColor(Color.parseColor("#FCFDFD"));
            answer4.setBackgroundColor(Color.parseColor("#FCFDFD"));
        }
        else if(question_correct_answer==2){
            answer2.setHint("Resposta Correta");
            answer2.setBackgroundColor(Color.parseColor("#E7C397"));
            answer1.setBackgroundColor(Color.parseColor("#FCFDFD"));
            answer3.setBackgroundColor(Color.parseColor("#FCFDFD"));
            answer4.setBackgroundColor(Color.parseColor("#FCFDFD"));
        }
        else if(question_correct_answer==3){
            answer3.setHint("Resposta Correta");
            answer3.setBackgroundColor(Color.parseColor("#E7C397"));
            answer1.setBackgroundColor(Color.parseColor("#FCFDFD"));
            answer2.setBackgroundColor(Color.parseColor("#FCFDFD"));
            answer4.setBackgroundColor(Color.parseColor("#FCFDFD"));
        }
        else{
            answer4.setHint("Resposta Correta");
            answer4.setBackgroundColor(Color.parseColor("#E7C397"));
            answer1.setBackgroundColor(Color.parseColor("#FCFDFD"));
            answer2.setBackgroundColor(Color.parseColor("#FCFDFD"));
            answer3.setBackgroundColor(Color.parseColor("#FCFDFD"));
        }
        number_of_answers = answers.size();
        question.setText(question_content); //Apresenta o conteúdo da questão
        //Se só houver 2 respostas
        if (answers.get(2).length() == 0 && answers.get(3).length() == 0) {
            answer1.setVisibility(View.VISIBLE);
            answer2.setVisibility(View.VISIBLE);
            answer3.setVisibility(View.INVISIBLE);
            answer4.setVisibility(View.INVISIBLE);
            add_3.setVisibility(View.VISIBLE);
            add_4.setVisibility(View.VISIBLE);
            answer1.setText(answers.get(0));
            answer2.setText(answers.get(1));
         //Se houver 3 respostas
        } else if (answers.get(2).length() != 0 && answers.get(3).length() == 0) {
            answer1.setVisibility(View.VISIBLE);
            answer2.setVisibility(View.VISIBLE);
            answer3.setVisibility(View.VISIBLE);
            answer4.setVisibility(View.INVISIBLE);
            add_3.setVisibility(View.INVISIBLE);
            add_4.setVisibility(View.VISIBLE);
            answer1.setText(answers.get(0));
            answer2.setText(answers.get(1));
            answer3.setText(answers.get(2));
         //Se houver 4 respostas
        } else {
            answer1.setVisibility(View.VISIBLE);
            answer2.setVisibility(View.VISIBLE);
            answer3.setVisibility(View.VISIBLE);
            answer4.setVisibility(View.VISIBLE);
            add_3.setVisibility(View.INVISIBLE);
            add_4.setVisibility(View.INVISIBLE);
            answer1.setText(answers.get(0));
            answer2.setText(answers.get(1));
            answer3.setText(answers.get(2));
            answer4.setText(answers.get(3));
        }

    }

    //Quando se pretende adicionar mais caixas de texto (respostas)
    public void addAnswer(View v) {

            if (visibility_aux == 0) {
                add_3.setVisibility(View.INVISIBLE); //adeus butão add3
                answer3.setVisibility(View.VISIBLE); //olá caixa de texto add3
                add_4.setVisibility(View.VISIBLE); //olá caixa de texto add4
                visibility_aux = 1; //alterar o valor da variável auxiliar
            }
            //quando se pretende adicionar a 4a questão
            else {
                add_4.setVisibility(View.INVISIBLE); //adeus add4
                answer4.setVisibility(View.VISIBLE); //olá answer4
            }
    }


    //Quando é pressionado o botão submeter..
    public void EditQuestion(View v) {
        oSQLiteDB = oDB.getWritableDatabase();
        //Se a questão estiver sem nada
        if (question.getText().length() == 0) {
            //toast de erro
            Toast.makeText(getApplicationContext(), "Não deixe a questão em branco!", Toast.LENGTH_SHORT).show();
        }
        //Se as duas primeiras edittext estiverem vazias
        else if (answer1.getText().length() == 0 || answer2.getText().length() == 0) {
            //Toas de erro, deve haver pelo menos 2 respostas
            Toast.makeText(getApplicationContext(), "A questão deve ter pelo menos duas respostas!", Toast.LENGTH_SHORT).show();
        }
        //Se estiver tudo em ordem...
        else {
            int first_id=oDB.getFirstQuestionAnswers(answers.get(0),position); //Obter o id da primeira resposta para a questão a editar
            //Editar a questão e pelo menos duas respostas
            oDB.editQuestions(question.getText().toString(),position);
            oDB.editAnswers(answer1.getText().toString(), first_id,position);
            oDB.editAnswers(answer2.getText().toString(), (first_id+1),position);
            //Se a questão tiver 3 respostas
            if (answer3.getText().length() != 0) {
                    oDB.editAnswers(answer3.getText().toString(), (first_id+2),position);; //adicionar a 3a resposta
                //Se a questão tiver 4 respostas
                if (answer4.getText().length() != 0) {
                    oDB.editAnswers(answer4.getText().toString(), (first_id+3),position);; //adicionar a 3a resposta
                }
                //Se a 4a questão estiver em branco
                else{
                    oDB.editAnswers("", (first_id+3),position);; //adicionar a 3a resposta
                }
            }
            //Se a 3a resposta estiver em branco...
            else{
                //Mas a 4a resposta está escrita, passamos o conteúdo da primeira para a 3a
                if (answer4.getText().length() != 0) {
                    oDB.editAnswers(answer4.getText().toString(), (first_id+2),position);; //adicionar a 3a resposta
                    oDB.editAnswers("", (first_id+3),position);; //adicionar a 3a resposta
                }
                //Se a 4a questão estiver em branco
                else{
                    oDB.editAnswers("", (first_id+2),position);; //adicionar a 3a resposta
                    oDB.editAnswers("", (first_id+3),position);; //adicionar a 3a resposta
                }
            }
            Toast.makeText(getApplicationContext(), "Questão Alterada Com Sucesso", Toast.LENGTH_SHORT).show();
            //Close database
            oSQLiteDB.close();
            oDB.close();
            //retornar uma mensagem à atividade EditQuestions para que esta possa atualizar a lista
            Intent iResult = new Intent();
            setResult(RESULT_OK,iResult);
            super.finish();
        }
    }
}