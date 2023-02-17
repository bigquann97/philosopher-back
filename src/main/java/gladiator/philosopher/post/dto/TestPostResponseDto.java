package gladiator.philosopher.post.dto;

import gladiator.philosopher.post.entity.PostImage;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class TestPostResponseDto {

  // post
  private Long id; // 식별자
  private String title; // 제목
  private String content; // 내용
  private String category; // 카테고리
  private LocalDateTime createdDate; // 작성일자
  private PostStatus status; // 상태

  // account
  private String nickname; // 작성자 닉네임
//  private List<PostImage> images;

//  //  //image
  private List<String> images; // 이미지리스트

  //recommend
  private Long recommend; // 추천수

  public TestPostResponseDto(Long id, String title, String content, String category,
      LocalDateTime createdDate, PostStatus status, String nickname,
      Long recommend) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.category = category;
    this.createdDate = createdDate;
    this.status = status;
    this.nickname = nickname;
    this.recommend = recommend;
  }

  public TestPostResponseDto(TestPostResponseDto testPostResponseDto, List<String> url){
    this.id = testPostResponseDto.getId();
    this.title = testPostResponseDto.getTitle();
    this.content = testPostResponseDto.getContent();
    this.category = testPostResponseDto.category;
    this.createdDate = testPostResponseDto.getCreatedDate();
    this.status = testPostResponseDto.getStatus();
    this.nickname = testPostResponseDto.getNickname();
    this.recommend = testPostResponseDto.getRecommend();
    this.images = url;
  }

}
