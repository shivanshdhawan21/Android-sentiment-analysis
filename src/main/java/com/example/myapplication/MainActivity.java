package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Properties;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
public class MainActivity extends AppCompatActivity {
    EditText name;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showSentiment();
            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    public void showSentiment() {
        name=findViewById(R.id.name);
        String nameText=name.getText().toString();
        TextView sentiment=findViewById(R.id.Sentiment);
        new AsyncTask<Object, Void, Integer>() {
            @Override
            protected Integer doInBackground(Object... params) {
                Integer ans = 0;
                try {
                    // Set up the Stanford CoreNLP pipeline
                    Properties props = new Properties();
                    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
                    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
                    System.out.println("Shivansh " + ans);

                    // Annotate an example text
                    Annotation annotation = pipeline.process(nameText);

                    // Get the sentiment of the text
                    for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                        Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                        ans = RNNCoreAnnotations.getPredictedClass(tree);
                        System.out.println("Shivansh " + ans);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ans;
            }
            @SuppressLint("SetTextI18n")
            @Override
            protected void onPostExecute(Integer response) {
                super.onPostExecute(response);
                sentiment.setText(" Magnitude : " + response);
            }
        }.execute();
    }
}