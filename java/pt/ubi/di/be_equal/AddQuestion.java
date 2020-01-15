package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class AddQuestion extends AppCompatActivity {

    private EditText question;
    private EditText answer1;
    private EditText answer2;
    private EditText answer3;
    private EditText answer4;
    private Button add_3;
    private Button add_4;
    private int visibility_aux; //0 -> inicical, 1-> add_3 invisinle, answer3 visible, add_4 visible, 2 -> add_3 invisible, add_4 invisible, ambas as answers visible
    private DataBase oDB;
    public SQLiteDatabase oSQLiteDB;
    private int correct_answer_randomizer;
    private int correct_answer;
    private int lastanswerid;
    public Random gera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        oDB = new DataBase(getApplicationContext());
        gera = new Random();
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

        //Gera um número inteiro aleatório de 0 a 100
        correct_answer_randomizer=gera.nextInt(101);
        //Divido em metades, se o número estiver na primeira metade, a resposta correta é a 1
        lastanswerid=oDB.getLastAnswerID();
        if(correct_answer_randomizer<50){
            answer1.setHint("Resposta Correta");
            answer2.setHint("Resposta 2");
            answer1.setBackgroundColor(Color.parseColor("#E7C397"));
            answer2.setBackgroundColor(Color.parseColor("#FCFDFD"));
            correct_answer=lastanswerid+1;
        }
        //Se estiver na segunda metade, a resposta correta é a 2
        else{
            answer1.setHint("Resposta 1");
            answer2.setHint("Resposta Correta");
            answer2.setBackgroundColor(Color.parseColor("#E7C397"));
            answer1.setBackgroundColor(Color.parseColor("#FCFDFD"));
            correct_answer=lastanswerid+2;
        }
        visibility_aux=0; //estado inicial da visibilidade dos butões
        //Database
        oDB.close();
        //----------------
    }

    //Quando se pretende adicionar mais caixas de texto (respostas)
    public void addAnswer(View v){
            //quando se pretende adicionar a 3a questão
            if(visibility_aux==0){
                add_3.setVisibility(View.INVISIBLE); //adeus butão add3
                //Gera um número inteiro aleatório de 0 a 100
                correct_answer_randomizer=gera.nextInt(101);
                //Divido em três partes, se o número gerado estiver no primeiro terço, a resposta correta é a 1
                if(correct_answer_randomizer<34){
                    answer1.setHint("Resposta Correta");
                    answer2.setHint("Resposta 2");
                    answer3.setHint("Resposta 3");
                    answer2.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer3.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer1.setBackgroundColor(Color.parseColor("#E7C397"));
                    correct_answer=lastanswerid+1;
                }
                //Se estiver no segundo terço, a resposta correta é a 2
                else if (correct_answer_randomizer<67){
                    answer1.setHint("Resposta 1");
                    answer2.setHint("Resposta Correta");
                    answer3.setHint("Resposta 3");
                    answer1.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer3.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer2.setBackgroundColor(Color.parseColor("#E7C397"));
                    correct_answer=lastanswerid+2;
                }
                //Se estiver no terceiro terço, a resposta correta é a 3
                else{
                    answer1.setHint("Resposta 1");
                    answer2.setHint("Resposta 2");
                    answer3.setHint("Resposta Correta");
                    answer2.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer1.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer3.setBackgroundColor(Color.parseColor("#E7C397"));
                    correct_answer=lastanswerid+3;
                }
                //Altera as visibilidades
                answer3.setVisibility(View.VISIBLE); //olá caixa de texto add3
                add_4.setVisibility(View.VISIBLE); //olá caixa de texto add4
                visibility_aux=1; //alterar o valor da variável auxiliar
            }
            //quando se pretende adicionar a 4a questão, verificar se algumas das 3 edittext estão em branco
            else {
                add_4.setVisibility(View.INVISIBLE); //adeus add4
                //Gera-se um inteiro de 0 a 100
                correct_answer_randomizer=gera.nextInt(101);
                //Divide-se em quatro partes, se estiver no primeiro quarto, a resposta correta é a 1
                if(correct_answer_randomizer<26){
                    answer1.setHint("Resposta Correta");
                    answer2.setHint("Resposta 2");
                    answer3.setHint("Resposta 3");
                    answer4.setHint("Resposta 4");
                    answer2.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer3.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer4.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer1.setBackgroundColor(Color.parseColor("#E7C397"));
                    correct_answer=lastanswerid+1;

                }
                //Se estiver no segundo quarto, a resposta correta é a 2
                else if (correct_answer_randomizer<51){
                    answer1.setHint("Resposta 1");
                    answer2.setHint("Resposta Correta");
                    answer3.setHint("Resposta 3");
                    answer4.setHint("Resposta 4");
                    answer1.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer3.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer4.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer2.setBackgroundColor(Color.parseColor("#E7C397"));
                    correct_answer=lastanswerid+2;
                }
                //Se estiver no segundo quarto, a resposta correta é a 3
                else if (correct_answer_randomizer<76){
                    answer1.setHint("Resposta 1");
                    answer2.setHint("Resposta 2");
                    answer3.setHint("Resposta Correta");
                    answer4.setHint("Resposta 4");
                    answer1.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer2.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer4.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer3.setBackgroundColor(Color.parseColor("#E7C397"));
                    correct_answer=lastanswerid+3;
                }
                //Se estiver no quarto quarto, a resposta correta é a 4
                else{
                    answer1.setHint("Resposta 1");
                    answer2.setHint("Resposta 2");
                    answer3.setHint("Resposta 3");
                    answer4.setHint("Resposta Correta");
                    answer1.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer2.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer3.setBackgroundColor(Color.parseColor("#FCFDFD"));
                    answer4.setBackgroundColor(Color.parseColor("#E7C397"));
                    correct_answer=lastanswerid+4;

                }
                //Alterações na visibilidade
                answer4.setVisibility(View.VISIBLE); //olá answer4
            }

    }

    //Quando é pressionado o botão submeter..
    public void submitQuestion(View v){
        int id;
        int check3=0, check4=0;
        boolean insertAnswer3=false, insertAnswer4=false;
        oSQLiteDB = oDB.getWritableDatabase();
        //Se a questão estiver sem nada
        if (question.getText().length() == 0) {
            //toast de erro
            Toast.makeText(getApplicationContext(), "Não deixe a questão em branco!", Toast.LENGTH_SHORT).show();
        }
        //Se as duas primeiras edittext estiverem vazias
        else if (answer1.getText().length()==0 || answer2.getText().length()==0){
            //Toas de erro, deve haver pelo menos 2 respostas
            Toast.makeText(getApplicationContext(), "Preencha pelo menos as duas primeiras respostas!", Toast.LENGTH_SHORT).show();
        }
        //Se estiver tudo em ordem...
        else{
            //Ir buscar o id da última questão...
            id=oDB.getLastQuestionID();
            id=id+1; //Incrementá-lo de 1 unidade
            //Adicionar as questões e pelo menos duas respostas
            boolean insertQuestion = oDB.addQuestions(question.getText().toString(),correct_answer);
            id=oDB.getLastQuestionID();
            boolean insertAnswer1 = oDB.addAnswers(answer1.getText().toString(),id);
            boolean insertAnswer2 = oDB.addAnswers(answer2.getText().toString(),id);
            //Se a questão tiver 3 respostas
            if (answer3.getText().length()!=0){
                insertAnswer3 = oDB.addAnswers(answer3.getText().toString(),id); //adicionar a 3a resposta
                check3=1;
                //Caso tenha a 4a resposta
                if (answer4.getText().length()!=0){
                    insertAnswer4 = oDB.addAnswers(answer4.getText().toString(),id); //adicionar a 4a resposta
                    check4=1;
                }
                //Caso não tenha as 4 respostas, adiconar a 4a na mesma, mas em branco  (apesar de ser "incorreto", tornou-me mais fácil a manipulação)
                else{
                    insertAnswer4 = oDB.addAnswers("",id); //adicionar a 4a resposta
                    check4=0;
                }
            }
            //Caso não tenha as 3 respostas...
            else{
                //Mas a 4a resposta estiver com conteúdo, inserir o conteúdo da 4a resposta no lugar da 3a
                if (answer4.getText().length()!=0){
                    insertAnswer3 = oDB.addAnswers(answer4.getText().toString(),id);
                    check3=1;
                    insertAnswer4 = oDB.addAnswers("",id); //adicionar a 4a resposta em branco
                    check4=0;
                }
                //Caso não tenha as 4 respostas...
                else{
                    insertAnswer3 = oDB.addAnswers("",id); //adicionar a 3a resposta em branco
                    check3=0;
                    insertAnswer4 = oDB.addAnswers("",id); //adicionar a 4a resposta em branco
                    check4=0;
                }
            }
            //Verificar se tudo foi inserido com sucesso:
            //Se a questão não foi inserida com sucesso
            if(!insertQuestion){
                //Toast de erro da questão
                Toast.makeText(getApplicationContext(), "Erro ao submeter a questão!", Toast.LENGTH_SHORT).show();
            }
            //Se a questão foi inserida com sucesso...
            else{
                //Se só houver duas respostas
                if(check3==0){
                    //Se alguma das duas respostas não foi inserida com sucesso
                    if(!insertAnswer1 || !insertAnswer2){
                        //Toast erro das respostas
                        Toast.makeText(getApplicationContext(), "Erro ao submeter as respostas!", Toast.LENGTH_SHORT).show();
                    }
                    //Se as duas respostas foram inseridas com sucesso
                    else{
                        //Toast de sucesso
                        Toast.makeText(getApplicationContext(), "Respostas submetidas com sucesso!", Toast.LENGTH_SHORT).show();
                        oSQLiteDB.close();
                        oDB.close();
                        super.finish();
                    }
                }
                //Se houver mais do que duas respostas
                else{
                    //Se não houver uma 4a respostas
                    if(check4==0){
                        //Se alguma das 3 respostas não foi inserida com sucesso
                        if(!insertAnswer1 || !insertAnswer2 || !insertAnswer3){
                            //Toast de erro
                            Toast.makeText(getApplicationContext(), "Erro ao submeter as respostas!", Toast.LENGTH_SHORT).show();
                        }
                        //Se as 3 respostas foram inseridas com sucesso
                        else{
                            Toast.makeText(getApplicationContext(), "Respostas submetidas com sucesso!", Toast.LENGTH_SHORT).show();
                            oSQLiteDB.close();
                            oDB.close();
                            super.finish();
                        }
                    }
                    //Se houver uma 4a respostas
                    else{
                        //Se alguma das 4 respostas não foi inserida com sucesso
                        if(!insertAnswer1 || !insertAnswer2 || !insertAnswer3 || !insertAnswer4){
                            //Toast de erro
                            Toast.makeText(getApplicationContext(), "Erro ao submeter as respostas!", Toast.LENGTH_SHORT).show();
                        }
                        //Se as 4 respostas foram inseridas com sucesso
                        else{
                            //Toast de sucesso
                            Toast.makeText(getApplicationContext(), "Respostas submetidas com sucesso!", Toast.LENGTH_SHORT).show();
                            oSQLiteDB.close();
                            oDB.close();
                            super.finish();
                        }
                    }
                }
            }
        }
    }
}
