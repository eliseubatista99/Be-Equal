package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class StartQuestions extends AppCompatActivity {

    private Button question;
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    private Button submit;
    private DataBase oDB;
    public SQLiteDatabase oSQLiteDB;
    public int number_of_questions;
    public ArrayList<String> answers;
    public ArrayList<String> respective_answers;
    public String question_content;
    public int which_question;
    Cursor data;
    public boolean userChosedAnOption;
    public boolean userChosed1;
    public boolean userChosed2;
    public boolean userChosed3;
    public boolean userChosed4;
    public String username;
    ArrayList<String> user_questions;
    ArrayList<String> user_answers;
    ArrayList<Integer> user_questions_id;
    ArrayList<Integer> user_answers_id;
    public int question_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_questions);
        user_questions = new ArrayList<>();
        user_answers = new ArrayList<>();
        user_questions_id = new ArrayList<>();
        user_answers_id = new ArrayList<>();
        username=getIntent().getStringExtra("username");
        oDB = new DataBase(this);
        oSQLiteDB = oDB.getWritableDatabase();
        Cursor data = oDB.getHistory();
        //Se já existir um histórico daquele utilizador, remover todas as suas entradas
        if (data.getCount()!=0){
            while(data.moveToNext()){
                if(data.getString(1).equals(username)){
                    oDB.eraseHistory(username);
                    break;
                }
            }
        }

        data = oDB.getQuestions();
        // Para obter o número de questões
        number_of_questions=0;
        //Obter o número de questões
        while (data.moveToNext()) {
            number_of_questions++;
        }
        //Se só houver uma questão, trocar o texto do buttão "next" por "Terminar Questionário"
        if(number_of_questions==1){
            submit.setText("Terminar Questionário");
        }
        //data.moveToFirst();
        which_question=1;
        oDB.close();
        oSQLiteDB.close();
        prepare(which_question);
    }

    public void prepare(int which_question) {
        question = findViewById(R.id.question_content);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        submit = findViewById(R.id.submit);
        //Prepara a visibilidade dos botões/textboxes
        answer3.setVisibility(View.INVISIBLE);
        answer4.setVisibility(View.INVISIBLE);
        answers = new ArrayList<>();
        answers.add("0");
        //question.setText(question_content);
        respective_answers = new ArrayList<>();
        respective_answers.add("");
        answers.clear();
        respective_answers.clear();
        oDB = new DataBase(this);
        oSQLiteDB = oDB.getWritableDatabase();
        data = oDB.getQuestions();
        //Obter o conteúdo e id de uma respetiva questão
        while (data.moveToNext()) {
            if (data.getString(0).equals("" + which_question)) {
                question.setText(data.getString(1));
                question_content=data.getString(1);
                question_id=Integer.parseInt(data.getString(0));
            }
        }
        data = oDB.getAnswers();
        //Obter as respostas à questão procurada anteriormente
        while (data.moveToNext()) {
            if (data.getString(1).equals("" + question_id)) {
                answers.add(data.getString((2)));
                respective_answers.add(data.getString(0));
            }
        }
        //Se só houver 2 questões
        if (answers.get(2).length() == 0 && answers.get(3).length() == 0) {
            answer1.setVisibility(View.VISIBLE);
            answer2.setVisibility(View.VISIBLE);
            answer3.setVisibility(View.INVISIBLE);
            answer4.setVisibility(View.INVISIBLE);
            answer1.setText(answers.get(0));
            answer2.setText(answers.get(1));
        //Se houver 3 questões
        } else if (answers.get(2).length() != 0 && answers.get(3).length() == 0) {
            answer1.setVisibility(View.VISIBLE);
            answer2.setVisibility(View.VISIBLE);
            answer3.setVisibility(View.VISIBLE);
            answer4.setVisibility(View.INVISIBLE);
            answer1.setText(answers.get(0));
            answer2.setText(answers.get(1));
            answer3.setText(answers.get(2));
        //Se houver 4 questões
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

        //Quando é pressionado o botão seguinte..
        public void next (View v){
            //Se o utilizador não escolheu nenhuma opção
            if(!userChosedAnOption){
                Toast.makeText(this,"Deve escolher uma opção!",Toast.LENGTH_SHORT).show();
            }
            //Se o utilizador escolheu uma opção
            else {
                user_questions.add(question_content);
                user_questions_id.add(question_id);
                //Se o utilizador escolheu a primeira
                if(userChosed1){
                    user_answers.add(answer1.getText().toString());
                    user_answers_id.add(Integer.parseInt(respective_answers.get(0)));
                }
                //Se o utilizador escolheu a segunda
                else if (userChosed2){
                    user_answers.add(answer2.getText().toString());
                    user_answers_id.add(Integer.parseInt(respective_answers.get(1)));
                }
                //Se o utilizador escolheu a terceira
                else if (userChosed3){
                    user_answers.add(answer3.getText().toString());

                    user_answers_id.add(Integer.parseInt(respective_answers.get(2)));
                }
                //Se o utilizador escolheu a quarta opção
                else{
                    user_answers.add(answer4.getText().toString());
                    user_answers_id.add(Integer.parseInt(respective_answers.get(3)));
                }
                //Repor os booleans e as cores
                userChosed1 = false;
                userChosed2 = false;
                userChosed3 = false;
                userChosed4 = false;
                userChosedAnOption = false;
                answer1.setBackground(getDrawable(R.drawable.round_button));
                answer2.setBackground(getDrawable(R.drawable.round_button));
                answer3.setBackground(getDrawable(R.drawable.round_button));
                answer4.setBackground(getDrawable(R.drawable.round_button));
                //Se chegou ao fim do questionário
                if (which_question == number_of_questions) {
                    //Close database
                    oSQLiteDB.close();
                    oDB.close();
                    Intent iResult = new Intent();
                    setResult(RESULT_OK, iResult);
                    oDB.addHistory(username,user_questions,user_answers,user_questions_id,user_answers_id);
                    super.finish();
                //Se não chegou ao fim do questionário
                } else {
                    //Se é a última questão
                    if (which_question == number_of_questions - 1) {
                        submit.setText("Terminar Questionário");
                    }
                    which_question = which_question + 1;
                    prepare(which_question);
                }
            }
        }

        //Função para processar o botão selecionado
        public void selectButton(View v){
            //Consoante a resposta escolhida, alterar os valores dos booleans e as cores
            switch(v.getId()) {

                case R.id.answer1:
                    userChosed1 = true;
                    userChosed2 = false;
                    userChosed3 = false;
                    userChosed4 = false;
                    userChosedAnOption = true;
                    answer1.setBackground(getDrawable(R.drawable.round_button_darker));
                    answer2.setBackground(getDrawable(R.drawable.round_button));
                    answer3.setBackground(getDrawable(R.drawable.round_button));
                    answer4.setBackground(getDrawable(R.drawable.round_button));
                    break;
                case R.id.answer2:
                    userChosed1 = false;
                    userChosed2 = true;
                    userChosed3 = false;
                    userChosed4 = false;
                    userChosedAnOption = true;
                    answer2.setBackground(getDrawable(R.drawable.round_button_darker));
                    answer1.setBackground(getDrawable(R.drawable.round_button));
                    answer3.setBackground(getDrawable(R.drawable.round_button));
                    answer4.setBackground(getDrawable(R.drawable.round_button));
                    break;
                case R.id.answer3:
                    userChosed1 = false;
                    userChosed2 = false;
                    userChosed3 = true;
                    userChosed4 = false;
                    userChosedAnOption = true;
                    answer3.setBackground(getDrawable(R.drawable.round_button_darker));
                    answer1.setBackground(getDrawable(R.drawable.round_button));
                    answer2.setBackground(getDrawable(R.drawable.round_button));
                    answer4.setBackground(getDrawable(R.drawable.round_button));
                    break;
                case R.id.answer4:
                    userChosed1 = false;
                    userChosed2 = false;
                    userChosed3 = false;
                    userChosed4 = true;
                    userChosedAnOption = true;
                    answer4.setBackground(getDrawable(R.drawable.round_button_darker));
                    answer1.setBackground(getDrawable(R.drawable.round_button));
                    answer2.setBackground(getDrawable(R.drawable.round_button));
                    answer3.setBackground(getDrawable(R.drawable.round_button));
                    break;
                default:
                    userChosed1 = false;
                    userChosed2 = false;
                    userChosed3 = false;
                    userChosed4 = false;
                    userChosedAnOption = false;
                    break;
            }
        }
}