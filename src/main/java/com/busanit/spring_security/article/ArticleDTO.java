package com.busanit.spring_security.article;


import com.busanit.spring_security.comment.Comment;
import com.busanit.spring_security.comment.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 데이터 전송 객체

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private List<CommentDTO> comments;

    // DTO -> 엔티티 (엔티티에 @Builder 적용, 빌더 패턴 사용)
    public Article toEntity() {
        // DTO -> 엔티티 필드 매핑
        Article article = Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        // DTO(댓글 리스트) -> 엔티티(댓글 리스트)
        if (comments != null) {
            List<Comment> commentList = comments.stream().map(commentDTO ->
                    commentDTO.toEntity(article))
                    .toList();
            article.setComments(commentList);
        }
        return article;
    }
}
