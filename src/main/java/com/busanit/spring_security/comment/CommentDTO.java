package com.busanit.spring_security.comment;

import com.busanit.spring_security.article.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private String author;
    private Long articleId;

    // DTO -> 엔티티 (엔티티에 @Builder 적용, 빌더 패턴 사용)
    public Comment toEntity(Article article) {
        return Comment.builder()
                .id(id)
                .content(content)
                .author(author)
                .article(article)
                .build();
    }
}
