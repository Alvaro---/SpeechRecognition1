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
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onInit(int status) {

    }
}
