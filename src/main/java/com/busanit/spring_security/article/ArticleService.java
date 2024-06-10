package com.busanit.spring_security.article;

import com.busanit.spring_security.comment.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();

        /*
        List<ArticleDTO> articleDTOs = new ArrayList<>();   // ArticleDTO를 담을 빈 객체

        // 조회된 전체 엔티티를 순회하며 Article -> ArticleDTO 로 변경
        for (Article article : articles) {
            // 게시글을 참조하는 댓글 전체 순회하며 Comment -> CommentDTO 로 변경
            List<Comment> comments = article.getComments();     // Article에서 커멘트 가져옴.
            List<CommentDTO> commentDTOs = new ArrayList<>();   // CommentDTO를 담을 빈 객체
            for (Comment comment : comments) {
                CommentDTO commentDTO = new CommentDTO(comment.getId(), comment.getContent(), comment.getAuthor(), comment.getArticle().getId());  // CommentDTO 생성
                commentDTOs.add(commentDTO);        // 컬렉션에 추가
            }

            // ArticleDTO 생성
            ArticleDTO articleDTO = new ArticleDTO(article.getId(), article.getTitle(), article.getContent(), article.getAuthor(), commentDTOs);

            articleDTOs.add(articleDTO);        // 컬렉션에 ArticleDTO 추가
        }
         */

        return articles.stream()
                .map(Article::toDTO)
                .toList();
    }

    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        // DTO로 변환
        if (article != null) {
            return article.toDTO();
        } else {
            return null;
        }
        // return article != null ? article.toDTO() : null;
    }

    @Transactional
    public ArticleDTO createArticle(ArticleDTO dto) {
        Article saved = articleRepository.save(dto.toEntity());
        return saved.toDTO();
    }

    @Transactional
    public ArticleDTO updateArticle(Long id, ArticleDTO updateArticle) {
        Article article = articleRepository.findById(id).orElse(null);

        if (article != null) {
            if (updateArticle.getTitle() != null) {
                article.setTitle(updateArticle.getTitle());
            }
            if (updateArticle.getContent() != null) {
                article.setContent(updateArticle.getContent());
            }
            if (updateArticle.getAuthor() != null) {
                article.setAuthor(updateArticle.getAuthor());
            }
            return articleRepository.save(article).toDTO();

        } else {
            return null;
        }
    }

    @Transactional
    public Boolean deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article != null) {
            articleRepository.delete(article);
            return true;
        } else {
            return false;
        }
    }

    // 쿼리 메서드 사용
    public List<ArticleDTO> getArticleByAuthor(String author) {
        List<Article> articleList = articleRepository.findByAuthor(author);
        return articleList.stream().map(Article::toDTO).toList();

    }

    public List<ArticleDTO> getArticleByTitleContaining(String title) {
        List<Article> articleList = articleRepository.findByTitleContaining(title);
        return articleList.stream().map(Article::toDTO).toList();
    }

    public Page<ArticleDTO> getArticles(int page, int size, String sortBy) {
        // PageRequest.of 를 사용하여 페이지, 사이즈, 정렬 정보가 포함된 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        // 레포지토리에서 데이터를 조회(Pagable 객체를 매개변수로 사용) => Page<T> 객체를 반환
        Page<Article> articles = articleRepository.findAll(pageable);
        // Entity -> DTO (리스트)
        List<ArticleDTO> list = articles.stream().map(article -> article.toDTO()).toList();
        // List -> Page (Page 객체)
        Page<ArticleDTO> articleDTOs = new PageImpl<>(list, pageable, articles.getTotalElements());
        // Page<DTO>를 컨트롤러에 전달
        return articleDTOs;
    }

    public Page<ArticleDTO> getArticlesByAuthor(String author, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Article> articles = articleRepository.findByAuthor(author, pageable);
        List<ArticleDTO> list = articles.stream().map(article -> article.toDTO()).toList();
        Page<ArticleDTO> articleDTOs = new PageImpl<>(list, pageable, articles.getTotalElements());
        return articleDTOs;
    }

}