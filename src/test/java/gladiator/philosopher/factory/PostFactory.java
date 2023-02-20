package gladiator.philosopher.factory;

import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.post.entity.PostOpinion;
import java.util.List;

public class PostFactory {

  public static Post createPost1() {
    return Post.builder()
        .account(AccountFactory.createUserAccount1())
        .title("post title1")
        .content("post content1")
        .category(CategoryFactory.createCategory1())
        .opinions(List.of(createPostOpinion1(), createPostOpinion2(), createPostOpinion3()))
        .build();
  }

  public static Post createPost2() {
    return Post.builder()
        .account(AccountFactory.createUserAccount2())
        .title("post title2")
        .content("post content2")
        .category(CategoryFactory.createCategory2())
        .opinions(List.of(createPostOpinion1(), createPostOpinion2(), createPostOpinion3()))
        .build();
  }

  public static PostOpinion createPostOpinion1() {
    return PostOpinion.builder()
        .opinion("opinion1")
        .post(createPost1())
        .build();
  }

  public static PostOpinion createPostOpinion2() {
    return PostOpinion.builder()
        .opinion("opinion2")
        .post(createPost1())
        .build();
  }

  public static PostOpinion createPostOpinion3() {
    return PostOpinion.builder()
        .opinion("opinion2")
        .post(createPost1())
        .build();
  }

  public static PostImage createPostImage1() {
    return PostImage.builder()
        .post(createPost1())
        .url("url1")
        .build();
  }

  public static PostImage createPostImage2() {
    return PostImage.builder()
        .post(createPost1())
        .url("url2")
        .build();
  }

  public static PostImage createPostImage3() {
    return PostImage.builder()
        .post(createPost1())
        .url("url3")
        .build();
  }


}
