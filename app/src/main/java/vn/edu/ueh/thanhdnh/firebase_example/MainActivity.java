
package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

  private EditText etTitle, etContent, etImageUrl;
  private Button btAdd, btShow;
  private FirebaseFirestore db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    etTitle = findViewById(R.id.etTitle);
    etContent = findViewById(R.id.etContent);
    etImageUrl = findViewById(R.id.etImageUrl);
    btAdd = findViewById(R.id.btAdd);
    btShow = findViewById(R.id.btShow);

    db = FirebaseFirestore.getInstance();

    btAdd.setOnClickListener(v -> addArticle());

    btShow.setOnClickListener(v -> {
      Intent intent = new Intent(
              MainActivity.this,
              ShowDataActivity.class
      );
      startActivity(intent);
    });
  }

  private void addArticle() {
    String title = etTitle.getText().toString().trim();
    String content = etContent.getText().toString().trim();
    String imageUrl = etImageUrl.getText().toString().trim();

    if (title.isEmpty() || content.isEmpty()) {
      Toast.makeText(
              this,
              "Please enter title and content",
              Toast.LENGTH_SHORT
      ).show();
      return;
    }

    // Tạo document ID trước để dùng làm Article ID
    String articleId = db.collection("articles").document().getId();

    Article article = new Article(
            articleId,
            title,
            content,
            imageUrl
    );

    btAdd.setEnabled(false);

    db.collection("articles")
            .document(articleId)
            .set(article)
            .addOnSuccessListener(unused -> {
              Toast.makeText(
                      MainActivity.this,
                      "Article saved successfully!",
                      Toast.LENGTH_LONG
              ).show();

              etTitle.setText("");
              etContent.setText("");
              etImageUrl.setText("");
            })
            .addOnFailureListener(e -> {
              Log.e("FIRESTORE_ERROR",
                      "Failed to save Article", e);

              Toast.makeText(
                      MainActivity.this,
                      "Save failed: " + e.getMessage(),
                      Toast.LENGTH_LONG
              ).show();
            })
            .addOnCompleteListener(task ->
                    btAdd.setEnabled(true));
  }
}
