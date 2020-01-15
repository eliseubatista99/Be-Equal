package pt.ubi.di.be_equal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    //Construtor da database
    public DataBase(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    //Definição de Strings para executar os comandos SQL

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BeEqual.db";

    protected static final String TABLE_LOGIN = "Logins";
    protected static final String COLUMN_1_1 = "name";
    protected static final String COLUMN_1_2 = "password";
    protected static final String COLUMN_1_3 = "admin";

    protected static final String TABLE_QUESTIONS = "Questions";
    protected static final String COLUMN_2_1 = "id";
    protected static final String COLUMN_2_2 = "question";
    protected static final String COLUMN_2_3= "correct_answer";

    protected static final String TABLE_ANSWERS = "Answers";
    protected static final String COLUMN_3_1 = "id";
    protected static final String COLUMN_3_2 = "question";
    protected static final String COLUMN_3_3 = "answer";

    protected static final String TABLE_HISTORY = "History";
    protected static final String COLUMN_4_1 = "id";
    protected static final String COLUMN_4_2 = "username";
    protected static final String COLUMN_4_3 = "question";
    protected static final String COLUMN_4_4 = "answer";
    protected static final String COLUMN_4_5 = "question_id";
    protected static final String COLUMN_4_6 = "answer_id";

    //CREATE TABLES

    private static final String LOGIN_TABLE_CREATE = "CREATE TABLE " + TABLE_LOGIN + "("
            + COLUMN_1_1 + " VARCHAR(18) PRIMARY KEY,"
            + COLUMN_1_2 + " VARCHAR(18),"
            + COLUMN_1_3 + " INTEGER);";

    private static final String QUESTIONS_TABLE_CREATE = "CREATE TABLE " + TABLE_QUESTIONS+ "("
            + COLUMN_2_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_2_2 + " TEXT,"
            + COLUMN_2_3 + " INTEGER);";

    private static final String ANSWERS_TABLE_CREATE = "CREATE TABLE " + TABLE_ANSWERS+ "("
            + COLUMN_3_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_3_2 + " INTEGER,"
            + COLUMN_3_3 + " TEXT, FOREIGN KEY(" + COLUMN_3_2 + ") REFERENCES "
            +  TABLE_QUESTIONS + "(" + COLUMN_2_1 + "));";

    private static final String HISTORY_TABLE_CREATE = "CREATE TABLE " + TABLE_HISTORY+ "("
            + COLUMN_4_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_4_2 + " VARCHAR(30), "
            + COLUMN_4_3 + " TEXT, "
            + COLUMN_4_4 + " TEXT, "
            + COLUMN_4_5 + " INTEGER, "
            + COLUMN_4_6 + " INTEGER, FOREIGN KEY ("
            + COLUMN_4_2 + ") REFERENCES " + TABLE_LOGIN + "(" + COLUMN_1_1 + "), FOREIGN KEY ("
            + COLUMN_4_5 + ") REFERENCES " + TABLE_QUESTIONS + "(" + COLUMN_2_1 + "), FOREIGN KEY ("
            + COLUMN_4_6 + ") REFERENCES " + TABLE_ANSWERS + "(" + COLUMN_3_1 + "));";



    //DROP TABLES

    private static final String DROP_LOGIN = "DROP TABLE IF EXISTS "+TABLE_LOGIN+";";
    private static final String DROP_QUESTIONS = "DROP TABLE IF EXISTS "+TABLE_QUESTIONS+";";
    private static final String DROP_ANSWERS = "DROP TABLE IF EXISTS "+TABLE_ANSWERS+";";
    private static final String DROP_HISTORY = "DROP TABLE IF EXISTS "+TABLE_HISTORY+";";


    //Método OnCreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LOGIN_TABLE_CREATE);
        db.execSQL(QUESTIONS_TABLE_CREATE);
        db.execSQL(ANSWERS_TABLE_CREATE);
        db.execSQL(HISTORY_TABLE_CREATE);
    }

    //Método onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_LOGIN);
        db.execSQL(LOGIN_TABLE_CREATE);
        db.execSQL(DROP_ANSWERS);
        db.execSQL(ANSWERS_TABLE_CREATE);
        db.execSQL(DROP_QUESTIONS);
        db.execSQL(QUESTIONS_TABLE_CREATE);
        db.execSQL(DROP_HISTORY);
        db.execSQL(HISTORY_TABLE_CREATE);
    }

    //Método para adicionar um login
    public boolean addLogin(String name, String password, int tipo ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenvalues = new ContentValues();
        //preparar o conteúdo a inserir
        contenvalues.put(COLUMN_1_1,name);
        contenvalues.put(COLUMN_1_2,password);
        contenvalues.put(COLUMN_1_3,tipo);
        //---------------------------------
        long result = db.insert(TABLE_LOGIN,null,contenvalues); //insere na base de dados
        //Se falhou a inserir
        if(result==-1){
            db.close();
            return false;
        //Se inseriu com sucesso
        }else{
            db.close();
            return true;
        }
    }

    //Método para adicionar questões
    public boolean addQuestions(String question,int correct){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenvalues = new ContentValues();
        //prepara o conteúdo a inserir
        contenvalues.put(COLUMN_2_2,question);
        contenvalues.put(COLUMN_2_3,correct);
        //-----------------------------
        long result = db.insert(TABLE_QUESTIONS,null,contenvalues); //insere na database
        //Se falhou a inserir
        if(result==-1){
            db.close();
            return false;
        //Se inserir com sucesso
        }else{
            db.close();
            return true;
        }
    }

    //Método para editar questões
    public boolean editQuestions(String question, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenvalues = new ContentValues();
        //prepara o conteúdo a atualizar
        contenvalues.put(COLUMN_2_2,question);
        //-----------------------------
        long result = db.update(TABLE_QUESTIONS,contenvalues, "id = " + id,null); //update na database
        //Se falhou a editar
        if(result==-1){
            db.close();
            return false;
            //Se editou com sucesso
        }else{
            db.close();
            return true;
        }
    }

    //Método para apagar questões
    public boolean eraseQuestions(String question, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        //-----------------------------
        long result = db.delete(TABLE_QUESTIONS,"id = " + id,null); //apaga da database
        //Se falhou a apagar
        if(result==-1){
            db.close();
            return false;
            //Se apagou com sucesso
        }else{
            db.close();
            return true;
        }
    }

    //Método para adicionar respostas
    public boolean addAnswers(String answer, int question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenvalues = new ContentValues();
        //prepara o conteúdo a inserir
        contenvalues.put(COLUMN_3_2,question);
        contenvalues.put(COLUMN_3_3,answer);
        //------------------------------------
        long result = db.insert(TABLE_ANSWERS,null,contenvalues); //insere na base de dados
        //Se falhou a inserir
        if(result==-1){
            db.close();
            return false;
        //Se inseriu com sucesso
        }else{
            db.close();
            return true;
        }
    }

    //Método para editar respostas
    public boolean editAnswers(String answer, int id, int question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenvalues = new ContentValues();
        //prepara o conteúdo a atualizar
        contenvalues.put(COLUMN_3_2,question);
        contenvalues.put(COLUMN_3_3,answer);
        //-----------------------------
        long result = db.update(TABLE_ANSWERS,contenvalues, "id = " + id,null); //update na database
        //Se falhou a editar
        if(result==-1){
            db.close();
            return false;
            //Se editou com sucesso
        }else{
            db.close();
            return true;
        }
    }

    //Método para apagar respostas
    public boolean eraseAnswers(String answer, int id, int question){
        SQLiteDatabase db = this.getWritableDatabase();
        //-----------------------------
        long result = db.delete(TABLE_ANSWERS, "id = " + id,null); //apaga da database
        //Se falhou a apagar
        if(result==-1){
            db.close();
            return false;
            //Se apagou com sucesso
        }else{
            db.close();
            return true;
        }
    }

    //Método para apagar do Histórico
    public boolean eraseHistory(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = getHistory();
        //Valor predefinido para o result
        long result=-1;
        //Percorrer a tabela de history
        while(data.moveToNext()) {
            if (data.getString(1).equals(username)) {
                result = db.delete(TABLE_HISTORY, COLUMN_4_1 + " = " + data.getString(0),null); //apaga da database
            }
        }
        //Se falhou a apagar
        if(result==-1){
            db.close();
            return false;
            //Se apagou com sucesso
        }else{
            db.close();
            return true;
        }
    }

    //Método para adicionar ao Histórico
    public boolean addHistory(String username, ArrayList<String> questions, ArrayList<String> answers,ArrayList<Integer> questions_id, ArrayList<Integer> answers_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenvalues = new ContentValues();
        long result;
        //prepara o conteúdo a inserir
        int number = questions.size();
        for(int i=0;i<number;i++){
            contenvalues.put(COLUMN_4_2,username);
            contenvalues.put(COLUMN_4_3,questions.get(i));
            contenvalues.put(COLUMN_4_4,answers.get(i));
            contenvalues.put(COLUMN_4_5,questions_id.get(i));
            contenvalues.put(COLUMN_4_6,answers_id.get(i));
            result = db.insert(TABLE_HISTORY,null,contenvalues); //insere na database
            //Se falhou a inserir
            if(result==-1) {
                db.close();
                return false;
            }
        }

        //-----------------------------
            db.close();
            return true;
    }

    //Método para retornar o ID da primeira resposta de uma determinada questão
    public int getFirstQuestionAnswers(String answer, int question){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = getAnswers();
        int tmp_id=0;
        //Percorrer a tabela de answers
        while(data.moveToNext()){
            //Se a answer for resposta da questão pretendida
            if(data.getString(1).equals(""+question)){
                    tmp_id=Integer.parseInt(data.getString(0));
                    return tmp_id;

            }
        }
        return -1;
        //------------------------------------
    }

    //Método para retornar um cursor com os dados da tabela Login
    public Cursor getLogins(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_LOGIN;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    //Método para retornar um cursor com os dados da tabela Questions
    public Cursor getQuestions(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTIONS;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    //Método para retornar um cursor com os dados da tabela Answers
    public Cursor getAnswers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ANSWERS;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    //Método para retornar um cursor com os dados da tabela History
    public Cursor getHistory(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HISTORY;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    //Método para retornar o último ID da tabela Logins
    public int getLastQuestionID(){
        int id=0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTIONS;
        Cursor data = db.rawQuery(query,null);
        while(data.moveToNext()){
            id=data.getInt(0);
        }
        return id;
    }

    //Método para retornar o último ID da tabela answers
    public int getLastAnswerID(){
        int id=0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ANSWERS;
        Cursor data = db.rawQuery(query,null);
        while(data.moveToNext()){
            id=data.getInt(0);
        }
        return id;
    }

    //Método para inserir alguns valores por defeito na base de dados
    public void prepararBasedeDados() {
        long result;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = getLogins();
        int cont = 0;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals("admin")) {
                cont = cont + 1;
            }
        }
        if (cont == 0) {
            //Insere um admin
            ContentValues admin1 = new ContentValues();

            admin1.put(COLUMN_1_1, "admin");
            admin1.put(COLUMN_1_2, "admin");
            admin1.put(COLUMN_1_3, 1);
            //---------------------------------
            db.insert(TABLE_LOGIN, null, admin1);

            //Insere um user
            ContentValues user1 = new ContentValues();

            user1.put(COLUMN_1_1, "user");
            user1.put(COLUMN_1_2, "user");
            user1.put(COLUMN_1_3, 0);
            //---------------------------------
            db.insert(TABLE_LOGIN, null, user1);

            //Tratamento da primeira questão--------------------------------------------------------------------
            ContentValues question1 = new ContentValues();
            //prepara o conteúdo a inserir
            question1.put(COLUMN_2_2, "O/A meu/minha namorado/a diz-me diariamente que gosta muito de mim e pediu-me as chaves de acesso ao meu telemóvel.\nSe fosse contigo, achas que devias dar-lhe essa informação? ");
            question1.put(COLUMN_2_3, 3);
            //-----------------------------
            result = db.insert(TABLE_QUESTIONS, null, question1); //insere na database
            //Prepara a primeira resposta
            ContentValues answer1 = new ContentValues();
            //prepara o conteúdo a inserir
            answer1.put(COLUMN_3_2, 1);
            answer1.put(COLUMN_3_3, "Sim, ele diz que gosta de mim e, por isso, os ciúmes justificam que ele queira controlar o que faço com o meu telemóvel.");
            //------------------------------------
            result = db.insert(TABLE_ANSWERS, null, answer1); //insere na base de dados
            //Prepara a segunda resposta
            ContentValues answer2 = new ContentValues();
            //prepara o conteúdo a inserir
            answer2.put(COLUMN_3_2, 1);
            answer2.put(COLUMN_3_3, "Sim, porque a nossa relação é baseada na confiança e sei que ele não vai fazer-me" +
                    "mal.");
            //------------------------------------
            result = db.insert(TABLE_ANSWERS, null, answer2); //insere na base de dados

            ContentValues answer3 = new ContentValues();
            //prepara o conteúdo a inserir
            answer3.put(COLUMN_3_2, 1);
            answer3.put(COLUMN_3_3, "Não dou as chaves de acesso ao meu telemóvel porque são pessoais.");
            //------------------------------------
            result = db.insert(TABLE_ANSWERS, null, answer3); //insere na base de dados

            ContentValues answer4 = new ContentValues();
            //prepara o conteúdo a inserir
            answer4.put(COLUMN_3_2, 1);
            answer4.put(COLUMN_3_3, "Só dou essa informação se ele também me der as chaves de acesso ao seu telemóvel.");
            //------------------------------------
            result = db.insert(TABLE_ANSWERS, null, answer4); //insere na base de dados

            //Tratamento da segunda questão--------------------------------------------------------------------
            ContentValues question2 = new ContentValues();
            //prepara o conteúdo a inserir
            question2.put(COLUMN_2_2, "Uma excelente gestora foi convidada para um cargo de chefia. Por ser uma senhora e " +
                    "devido à pressão atual no sentido da igualdade do género, estão a oferecer-lhe o dobro do " +
                    "salário que dariam a um senhor.\n" +
                    "Acha que a senhora devia aceitar o cargo e o salário? ");
            question2.put(COLUMN_2_3, 7);
            //-----------------------------
            result = db.insert(TABLE_QUESTIONS, null, question2); //insere na database
            //Prepara a primeira resposta
            ContentValues answer5 = new ContentValues();
            //prepara o conteúdo a inserir
            answer5.put(COLUMN_3_2, 2);
            answer5.put(COLUMN_3_3, "Devia rejeitar o cargo e o salário.");
            //------------------------------------
            result = db.insert(TABLE_ANSWERS, null, answer5); //insere na base de dados
            //Prepara a segunda resposta
            ContentValues answer6 = new ContentValues();
            //prepara o conteúdo a inserir
            answer6.put(COLUMN_3_2, 2);
            answer6.put(COLUMN_3_3, "Devia aceitar ambos.");
            //------------------------------------
            result = db.insert(TABLE_ANSWERS, null, answer6); //insere na base de dados

            ContentValues answer7 = new ContentValues();
            //prepara o conteúdo a inserir
            answer7.put(COLUMN_3_2, 2);
            answer7.put(COLUMN_3_3, "Devia aceitar o cargo mas pedir para ter um salário adequado à função.");
            //------------------------------------
            result = db.insert(TABLE_ANSWERS, null, answer7); //insere na base de dados

            ContentValues answer8 = new ContentValues();
            //prepara o conteúdo a inserir
            answer8.put(COLUMN_3_2, 2);
            answer8.put(COLUMN_3_3, "Devia aceitar o cargo mas pedir para ter um salário inferior ao esperado para a função.");
            //------------------------------------
            result = db.insert(TABLE_ANSWERS, null, answer8); //insere na base de dados

        }
    }

}

