
package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArticleViewAdapter
        extends RecyclerView.Adapter<ArticleViewHolder> {

  private final List<Article> articleList;

  public ArticleViewAdapter(List<Article> articleList) {
    this.articleList = articleList;
  }

  @NonNull
  @Override
  public ArticleViewHolder onCreateViewHolder(
          @NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.contact_list, parent, false);

    return new ArticleViewHolder(view);
  }

  @Override
  public void onBindViewHolder(
          @NonNull ArticleViewHolder holder, int position) {

    Article article = articleList.get(position);

    holder.tvArticleTitle.setText(
            article.getTitle() == null ? "" : article.getTitle()
    );

    holder.tvArticleContent.setText(
            article.getContent() == null ? "" : article.getContent()
    );

    String imageUrl = article.getImage_url();

    holder.tvArticleImageUrl.setText(
            imageUrl == null || imageUrl.trim().isEmpty()
                    ? "No image URL"
                    : imageUrl
    );
  }

  @Override
  public int getItemCount() {
    return articleList == null ? 0 : articleList.size();
  }
}
