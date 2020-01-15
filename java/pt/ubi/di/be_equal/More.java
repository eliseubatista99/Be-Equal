package pt.ubi.di.be_equal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class More extends AppCompatActivity {

    public ListView oLV;
    public ArrayList<String> List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        List = new ArrayList<>();
        oLV = findViewById(R.id.listview);
        //prepara o texto a ser apresentado
        String text_more="\n\t\t\t\t\t\t\t\t\tAcerca da Aplicação\n\nRegisto:\n\n\tAquando o registo de um utilizador, há duas caixas de texto, uma para o username e outra para a password " +
                ", estas devem ser obrigatoriamente preenchidas tendo um limite máximo de 17 caracteres. Se o registo for efetuado deste modo, o username registado será um 'User'" +
                ".\n\tSe pretender registar um administrador, deve marcar a checkbox 'Administrador', e aparecerá mais uma caixa de texto pedindo um admin code. O admin code " +
                "foi definido por predefinição como sendo 'admin', se registado deste modo, o username será um 'Admin'.\n\nFunções de Administrador:\n\n" +
                "Adicionar Questão:\n\n\tSe escolhida a opção de adicionar questão, serão apresentadas 3 caixas de texto, a primeira (e maior) servirá para colocar a questão " +
                "(sendo que esta não pode estar vazia), as outras duas caixas servem para as respostas, e estas devem estar também preenchidas (Uma questão deve ter no mínimo duas respostas). " +
                "É possivel adicionar mais caixas de texto pressionado o bottão '+' (máximo de 4).\n\tA resposta correta é gerada 'aleatóriamente', e tem um hint e uma cor a indicar qual delas é.\n\nEditar Questão:\n\n\tO conceito é parecido ao de 'Adicionar Aplicação'," +
                "A caixa da questão não pode estar em branco, as duas primeiras respostas devem estar preenchidas, e as outras duas respostas são opcionais. \n\nFunções do Utilizador: \n\nComeçar questionário:\n\n" +
                "\tÉ apresentado um questionário ao utilizador, este deve sempre selecionar uma resposta para cada uma das perguntas. Ao pressionar o botão 'Terminar Questionário'" +
                ", as respostas dadas pelo utilizador são guardadas e o questionário termina.\n\nGerar Relatório:\n\n\tAo escolher a opção 'Gerar Relatório', é mostrado o histórico de" +
                " respostas do utilizador, e um botão 'Ver Relatório'. Ao pressionar esse botão 'Ver Relatório' é mostrado um pequeno relatório dizendo se a resposta do utilizador está ou não de" +
                " acordo com a que seria mais correta éticamente.\n\n\n\tEliseu Batista, a39525\n ";
        List.add(text_more);
        oLV.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, List));
    }

}
