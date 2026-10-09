
package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity {

    private RecyclerView rvArticles;
    private TextView tvEmpty;
    private ArticleViewAdapter adapter;
    private final List<Article> articleList = new ArrayList<>();

    private FirebaseFirestore db;
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        rvArticles = findViewById(R.id.rvArticles);
        tvEmpty = findViewById(R.id.tvEmpty);

        rvArticles.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter = new ArticleViewAdapter(articleList);
        rvArticles.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        listenForArticles();
    }

    private void listenForArticles() {
        listenerRegistration = db.collection("articles")
                .addSnapshotListener((snapshots, error) -> {

                    if (error != null) {
                        Log.e("FIRESTORE_ERROR",
                                "Cannot load articles", error);

                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText(
                                "Cannot load articles: " + error.getMessage()
                        );
                        return;
                    }

                    if (snapshots == null) {
                        return;
                    }

                    articleList.clear();

                    for (DocumentSnapshot document : snapshots.getDocuments()) {
                        Article article = document.toObject(Article.class);

                        if (article != null) {
                            if (article.getId() == null
                                    || article.getId().isEmpty()) {
                                article.setId(document.getId());
                            }

                            articleList.add(article);
                        }
                    }

                    adapter.notifyDataSetChanged();

                    boolean isEmpty = articleList.isEmpty();
                    tvEmpty.setVisibility(
                            isEmpty ? View.VISIBLE : View.GONE
                    );

                    if (isEmpty) {
                        tvEmpty.setText("No articles found.");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }
}
