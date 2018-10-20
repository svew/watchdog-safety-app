package hackisu.fall2019.watchdog;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static android.speech.SpeechRecognizer.createSpeechRecognizer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpeechRecognizer sr = createSpeechRecognizer(getApplicationContext());
        final TextView textView = (TextView)findViewById(R.id.textView);
        sr.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                textView.setText("OnReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                textView.setText("onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float v) {
                textView.setText("onRmsChanged");

            }

            @Override
            public void onBufferReceived(byte[] bytes) {
                textView.setText("onBufferReceived");

            }

            @Override
            public void onEndOfSpeech() {
                textView.setText("onEndOfSpeech");

            }

            @Override
            public void onError(int i) {
                textView.setText("onError: " + i);

            }

            @Override
            public void onResults(Bundle bundle) {
                textView.setText("onResults");

            }

            @Override
            public void onPartialResults(Bundle bundle) {
                textView.setText("onPartialResults");

            }

            @Override
            public void onEvent(int i, Bundle bundle) {
                textView.setText("onEvent");

            }
        });
        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        sr.startListening(recognizerIntent);

            }
}
