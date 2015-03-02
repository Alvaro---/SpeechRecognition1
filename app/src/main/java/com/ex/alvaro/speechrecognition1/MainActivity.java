package com.ex.alvaro.speechrecognition1;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;



public class MainActivity extends Activity implements OnClickListener, TextToSpeech.OnInitListener {

    private static final int VR_REQUEST=999;
    private ListView wordList;
    private final String LONG_TAG="SpeechReapeatActivity";
    //TTS
    private int MY_DATA_CHECK_CODE=0;
    private TextToSpeech repeatTTS;

    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton=(Button)findViewById(R.id.btnHablar);
        wordList=(ListView)findViewById(R.id.word_list);

        PackageManager packageManager= getPackageManager();
        List<ResolveInfo> intActivities = packageManager.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (intActivities.size()!=0){
            boton.setOnClickListener(this);
            Intent checkTTSIntent=new Intent();
            checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        }else{
            boton.setEnabled(false);
            Toast.makeText(this, "aaa... Oops.....Speech not supported", Toast.LENGTH_LONG).show();
        }

        wordList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView wordView=(TextView)view;
                String wordChosen=(String) wordView.getText();
                Log.v("LOG_TAG...", "Escogio: "+wordChosen);
                Toast.makeText(MainActivity.this, "Dijo: "+wordChosen,Toast.LENGTH_SHORT).show();
                repeatTTS.speak("Dijiste: "+wordChosen, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnHablar){
            listenToSpeech();
        }
    }

    private void listenToSpeech(){
        Intent listenIntent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        listenIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
        listenIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a word");
        listenIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        listenIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,10);
        startActivityForResult(listenIntent, VR_REQUEST);
    }

    @Override
    public void onInit(int status) {
        if (status==TextToSpeech.SUCCESS){
            Locale loc = new Locale ("spa", "ESP");
            repeatTTS.setLanguage(loc);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==VR_REQUEST && resultCode == RESULT_OK){
            ArrayList<String> suggestedWords =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            wordList.setAdapter(new ArrayAdapter<String>(this, R.layout.word, suggestedWords));;
        }

        if(requestCode==MY_DATA_CHECK_CODE){
            if (resultCode==TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                repeatTTS=new TextToSpeech(this,this);
            }else{
                Intent installTTSIntent=new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
}
}
