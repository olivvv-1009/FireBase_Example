
package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {

  public TextView tvArticleTitle;
  public TextView tvArticleContent;
  public TextView tvArticleImageUrl;

  public ArticleViewHolder(@NonNull View itemView) {
    super(itemView);

    tvArticleTitle = itemView.findViewById(
            R.id.tvArticleTitle
    );

    tvArticleContent = itemView.findViewById(
            R.id.tvArticleContent
    );

    tvArticleImageUrl = itemView.findViewById(
            R.id.tvArticleImageUrl
    );
  }
}
