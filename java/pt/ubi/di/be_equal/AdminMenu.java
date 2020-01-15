package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    //Quando um dos botões é pressionado..
    public void selectButton(View v){
        Intent iselectButton;
        //Switch para realizar uma ação consoante o id do botão pressionado
        switch(v.getId()){ //Verificar qual dos botões foi clicado
            //Se o botão adicionar questão recebeu um clique, ir para a atividade addquestion
            case R.id.add_question:
                iselectButton=new Intent(this,AddQuestion.class);
                break;
            //Se o botão editar questão recebeu um clique, ir para a atividade editquestions
            case R.id.edit_question:
                iselectButton=new Intent(this,EditQuestions.class);
                break;
            //Se o botão apagar questao recebeu um clique, ir para a atividade eraseQuestions
            case R.id.erase_question:
                iselectButton=new Intent(this,EraseQuestion.class);
                break;
            //default, manter na mesma atividade
            default:
                iselectButton=new Intent(this,AdminMenu.class);
                break;
        }
        //Ir para a nova atividade
        startActivity(iselectButton);
    }
}
