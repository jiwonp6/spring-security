-- src/main/resources/data.sql
-- 이 파일에 데이터를 정의할 경우 정의된 데이터베이스에 자동으로 삽입
-- application.properties 에 설정 필요.

-- Sample data for Article
INSERT INTO article (id, title, content, author) VALUES (1, '봄 여행', '봄에는 꽃이 피어 아름답습니다.', '홍길동');
INSERT INTO article (id, title, content, author) VALUES (2, '여름 바다', '여름에는 바다가 최고입니다.', '이몽룡');
INSERT INTO article (id, title, content, author) VALUES (3, '가을 단풍', '가을에는 단풍이 멋집니다.', '성춘향');
INSERT INTO article (id, title, content, author) VALUES (4, '겨울 눈', '겨울에는 눈이 내려요.', '변학도');
INSERT INTO article (id, title, content, author) VALUES (5, '봄 소풍', '봄에는 소풍가기 좋습니다.', '이몽룡');
INSERT INTO article (id, title, content, author) VALUES (6, '여름 캠핑', '여름에는 캠핑이 재밌어요.', '성춘향');
INSERT INTO article (id, title, content, author) VALUES (7, '가을 독서', '가을에는 독서하기 좋습니다.', '홍길동');
INSERT INTO article (id, title, content, author) VALUES (8, '겨울 스포츠', '겨울에는 스키를 타요.', '변학도');
INSERT INTO article (id, title, content, author) VALUES (9, '봄 꽃놀이', '봄에는 꽃놀이가 좋습니다.', '박문수');
INSERT INTO article (id, title, content, author) VALUES (10, '여름 수영', '여름에는 수영을 즐깁니다.', '황진이');
INSERT INTO article (id, title, content, author) VALUES (11, '가을 산책', '가을에는 산책이 좋아요.', '홍길동');
INSERT INTO article (id, title, content, author) VALUES (12, '겨울 난로', '겨울에는 난로가 필요해요.', '이몽룡');
INSERT INTO article (id, title, content, author) VALUES (13, '봄 운동', '봄에는 운동하기 좋습니다.', '성춘향');
INSERT INTO article (id, title, content, author) VALUES (14, '여름 여행', '여름에는 여행을 떠나요.', '박문수');
INSERT INTO article (id, title, content, author) VALUES (15, '가을 요리', '가을에는 요리가 즐거워요.', '변학도');
INSERT INTO article (id, title, content, author) VALUES (16, '겨울 독서', '겨울에는 독서를 즐깁니다.', '황진이');
INSERT INTO article (id, title, content, author) VALUES (17, '봄 바람', '봄에는 바람이 산들산들.', '홍길동');
INSERT INTO article (id, title, content, author) VALUES (18, '여름 더위', '여름에는 더워서 힘들어요.', '이몽룡');
INSERT INTO article (id, title, content, author) VALUES (19, '가을 열매', '가을에는 열매가 열립니다.', '박문수');
INSERT INTO article (id, title, content, author) VALUES (20, '겨울 추위', '겨울에는 추워요.', '변학도');
INSERT INTO article (id, title, content, author) VALUES (21, '봄 피크닉', '봄에는 피크닉이 최고에요.', '성춘향');
INSERT INTO article (id, title, content, author) VALUES (22, '여름 비', '여름에는 비가 자주 내려요.', '황진이');
INSERT INTO article (id, title, content, author) VALUES (23, '가을 하늘', '가을에는 하늘이 높아요.', '박문수');
INSERT INTO article (id, title, content, author) VALUES (24, '겨울 집', '겨울에는 집이 따뜻해요.', '이몽룡');
INSERT INTO article (id, title, content, author) VALUES (25, '봄 꽃', '봄에는 꽃이 예뻐요.', '홍길동');

-- Sample data for Comment
INSERT INTO comment (id, content, author, article_id) VALUES (1, '첫 번째 댓글', '댓글 작성자1', 1);
INSERT INTO comment (id, content, author, article_id) VALUES (2, '두 번째 댓글', '댓글 작성자2', 1);
INSERT INTO comment (id, content, author, article_id) VALUES (3, '세 번째 댓글', '댓글 작성자1', 2);
INSERT INTO comment (id, content, author, article_id) VALUES (4, '네 번째 댓글', '댓글 작성자3', 2);
INSERT INTO comment (id, content, author, article_id) VALUES (5, '다섯 번째 댓글', '댓글 작성자2', 3);
INSERT INTO comment (id, content, author, article_id) VALUES (6, '여섯 번째 댓글', '댓글 작성자1', 3);
INSERT INTO comment (id, content, author, article_id) VALUES (7, '일곱 번째 댓글', '댓글 작성자3', 4);
INSERT INTO comment (id, content, author, article_id) VALUES (8, '여덟 번째 댓글', '댓글 작성자2', 4);
INSERT INTO comment (id, content, author, article_id) VALUES (9, '아홉 번째 댓글', '댓글 작성자1', 5);
INSERT INTO comment (id, content, author, article_id) VALUES (10, '열 번째 댓글', '댓글 작성자2', 5);
