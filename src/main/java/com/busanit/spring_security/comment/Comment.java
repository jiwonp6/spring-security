package com.busanit.spring_security.comment;


import com.busanit.spring_security.article.Article;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String author;

    // 1 : N 관계에서 외래키를 갖는 엔티티는 (N)
    // 외래키 필드 지정
    // 게시글과 댓글 관계에서 댓글은 Many에 해당 => Many To One
    @ManyToOne                           // 관계 설정
    @JoinColumn(name = "article_id")     // 외래키로 사용될 컬럼 이름
    // @JoinColumn(name = "article_id", nullable = false)  // 외래키 Not Null(필수) 지정
    private Article article;

    // 엔티티 -> DTO 변환 메서드
    public CommentDTO toDTO() {
        // 댓글에 게시글 ID가 없는 경우 예외처리
        Long articleId = 0L;
        if (article != null) {
            articleId = article.getId();
        }
        return new CommentDTO(id, content, author, articleId);
    }

    // DTO -> 엔티티 변환 메서드 (생성 메서드)
    public static Comment createComment(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setAuthor(dto.getAuthor());

        Article article = new Article();
        article.setId(dto.getArticleId());
        comment.setArticle(article);
        return comment;
    }
}
