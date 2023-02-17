package gladiator.philosopher.common.util;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.entity.AccountInfo;
import gladiator.philosopher.account.repository.AccountInfoRepository;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.category.repository.CategoryRepository;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.repository.CommentRepository;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.enums.UserStatus;
import gladiator.philosopher.mention.entity.Mention;
import gladiator.philosopher.mention.repository.MentionRepository;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.post.entity.PostOpinion;
import gladiator.philosopher.post.repository.PostImageRepository;
import gladiator.philosopher.post.repository.PostOpinionRepository;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.recommend.entity.CommentRecommend;
import gladiator.philosopher.recommend.entity.PostRecommend;
import gladiator.philosopher.recommend.entity.ThreadRecommend;
import gladiator.philosopher.recommend.repository.CommentRecommendRepository;
import gladiator.philosopher.recommend.repository.PostRecommendRepository;
import gladiator.philosopher.recommend.repository.RecommendRepository;
import gladiator.philosopher.recommend.repository.ThreadRecommendRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadOpinion;
import gladiator.philosopher.thread.repository.ThreadOpinionRepository;
import gladiator.philosopher.thread.repository.ThreadRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class InitData implements ApplicationRunner {

  private final PasswordEncoder passwordEncoder;
  private final AccountRepository accountRepository;
  private final ThreadRepository threadRepository;
  private final PostRepository postRepository;
  private final PostImageRepository postImageRepository;
  private final AccountInfoRepository accountInfoRepository;
  private final PostOpinionRepository postOpinionRepository;
  private final CategoryRepository categoryRepository;
  private final CommentRepository commentRepository;
  private final MentionRepository mentionRepository;
  private final RecommendRepository recommendRepository;
  private final ThreadOpinionRepository threadOpinionRepository;
  private final PostRecommendRepository postRecommendRepository;
  private final CommentRecommendRepository commentRecommendRepository;
  private final ThreadRecommendRepository threadRecommendRepository;


  @Override
  @Transactional
  public void run(ApplicationArguments args) throws Exception {

    // 회원부 ( 5명 )
    Account account1 = new Account(1L, "kiang18@naver.com", passwordEncoder.encode("rlawlghks1"),
        20, "김지환", Gender.FEMALE, UserRole.ROLE_MASTER, UserStatus.ACTIVATED);
    accountRepository.save(account1);
    AccountInfo accountImage1 = new AccountInfo(account1, "jipang1.jpg");
    accountInfoRepository.save(accountImage1);

    Account account2 = new Account(2L, "test1@naver.com", passwordEncoder.encode("rlawlghks1"), 40,
        "박정수", Gender.MALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    accountRepository.save(account2);
    AccountInfo accountImage2 = new AccountInfo(account2, "jipang2.png");
    accountInfoRepository.save(accountImage2);

    Account account3 = new Account(3L, "test2@naver.com", passwordEncoder.encode("rlawlghks1"), 40,
        "이정국", Gender.MALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    accountRepository.save(account3);
    AccountInfo accountImage3 = new AccountInfo(account3, "jipang3.jpg");
    accountInfoRepository.save(accountImage3);

    Account account4 = new Account(4L, "test3@naver.com", passwordEncoder.encode("rlawlghks1"), 80,
        "하규호", Gender.MALE, UserRole.ROLE_ADMIN, UserStatus.ACTIVATED);
    accountRepository.save(account4);

    Account account5 = new Account(5L, "test4@naver.com", passwordEncoder.encode("rlawlghks1"), 100,
        "김관호", Gender.FEMALE, UserRole.ROLE_USER, UserStatus.SUSPENDED);
    accountRepository.save(account5);

    // 카테고리
    Category category1 = new Category("철학");
    categoryRepository.save(category1);
    Category category2 = new Category("인문");
    categoryRepository.save(category2);
    Category category3 = new Category("사회");
    categoryRepository.save(category3);
    Category category4 = new Category("연애");
    categoryRepository.save(category4);
    Category category5 = new Category("논쟁");
    categoryRepository.save(category5);

    // 게시글 부
    List<String> opinions = Arrays.asList("opinion1", "opinion2", "opinion3");

    Post post1 = new Post(account1, "김지환의 테스트 데이터입니다.", "김지환의 테스트 데이터입니다", null, category1);
    postRepository.save(post1);

    Post post2 = new Post(account2, "박정수의 테스트 데이터입니다.", "박정수의  데이터입니다", null, category1);
    postRepository.save(post2);

    Post post3 = new Post(account3, "박정수의 테스트 데이터입니다.", "박정수의테스트 데이터입니다", null, category2);
    postRepository.save(post3);

    Post post4 = new Post(account4, "하규호의 테스트 데이터입니다.", "하규호의테스트 데이터입니다", null, category2);
    postRepository.save(post4);

    Post post5 = new Post(account4, "테스트 데이터입니다.", "테스트 데이터입니다", null, category4);
    postRepository.save(post5);

    Post post6 = new Post(account5, "테스트 데이터입니다.", "테스트 데이터입니다", null, category5);
    postRepository.save(post6);

    // 포스트 이미지
    PostImage postImage = new PostImage("jipang2.png", post1);
    postImageRepository.save(postImage);
    PostImage postImage2 = new PostImage("test2.png", post2);
    postImageRepository.save(postImage2);
    PostImage postImage3 = new PostImage("test3.png", post2);
    postImageRepository.save(postImage3);
    PostImage postImage4 = new PostImage("test4.png", post3);
    postImageRepository.save(postImage4);
    PostImage postImage5 = new PostImage("test5.png", post5);
    postImageRepository.save(postImage5);

    // 회원 1~5 // 게시글 1~6
    // 댓글부
    PostRecommend recommend17 = new PostRecommend(account1, post1);
    postRecommendRepository.save(recommend17);

    PostRecommend recommend16 = new PostRecommend(account1, post3);
    postRecommendRepository.save(recommend16);

    PostRecommend recommend3 = new PostRecommend(account1, post4);
    postRecommendRepository.save(recommend3);
    PostRecommend recommend4 = new PostRecommend(account2, post2);
    postRecommendRepository.save(recommend4);
    PostRecommend recommend5 = new PostRecommend(account2, post4);
    postRecommendRepository.save(recommend5);
    PostRecommend recommend6 = new PostRecommend(account2, post6);
    postRecommendRepository.save(recommend6);
    PostRecommend recommend7 = new PostRecommend(account3, post1);
    postRecommendRepository.save(recommend7);

    PostRecommend recommend8 = new PostRecommend(account3, post5);
    postRecommendRepository.save(recommend8);

    PostRecommend recommend9 = new PostRecommend(account3, post6);
    postRecommendRepository.save(recommend9);

    PostRecommend recommend10 = new PostRecommend(account4, post2);
    postRecommendRepository.save(recommend10);
    PostRecommend recommend11 = new PostRecommend(account4, post6);
    postRecommendRepository.save(recommend11);
    PostRecommend recommend12 = new PostRecommend(account4, post4);
    postRecommendRepository.save(recommend12);
    PostRecommend recommend13 = new PostRecommend(account5, post2);
    postRecommendRepository.save(recommend13);

    PostRecommend recommend14 = new PostRecommend(account5, post3);
    postRecommendRepository.save(recommend14);

    PostRecommend recommend15 = new PostRecommend(account5, post4);
    postRecommendRepository.save(recommend15);

    List<PostOpinion> list = opinions.stream().map(x -> new PostOpinion(post1, x))
        .collect(Collectors.toList());
    List<PostOpinion> list2 = opinions.stream().map(x -> new PostOpinion(post2, x))
        .collect(Collectors.toList());
    List<PostOpinion> list3 = opinions.stream().map(x -> new PostOpinion(post3, x))
        .collect(Collectors.toList());
    List<PostOpinion> list4 = opinions.stream().map(x -> new PostOpinion(post4, x))
        .collect(Collectors.toList());
    List<PostOpinion> list5 = opinions.stream().map(x -> new PostOpinion(post5, x))
        .collect(Collectors.toList());
    List<PostOpinion> list6 = opinions.stream().map(x -> new PostOpinion(post6, x))
        .collect(Collectors.toList());

    postOpinionRepository.saveAll(list);
    postOpinionRepository.saveAll(list2);
    postOpinionRepository.saveAll(list3);
    postOpinionRepository.saveAll(list4);
    postOpinionRepository.saveAll(list5);
    postOpinionRepository.saveAll(list6);

    // 쓰레드 부
    Thread thread1 = new Thread(post4.getTitle(), post4.getContent(), account4, LocalDateTime.now(),
        null);
    threadRepository.save(thread1);
    Thread thread2 = new Thread(post5.getTitle(), post5.getContent(), account5, LocalDateTime.now(),
        null);
    threadRepository.save(thread2);

    ThreadOpinion threadOpinion1 = new ThreadOpinion(thread1, "한다");
    ThreadOpinion threadOpinion2 = new ThreadOpinion(thread1, "안한다");
    threadOpinionRepository.save(threadOpinion1);
    threadOpinionRepository.save(threadOpinion2);

    Comment comment = new Comment(account1, thread2, "내용", "의견1");
    Comment comment2 = new Comment(account2, thread2, "내용", "의견2");
    Comment comment3 = new Comment(account2, thread2, "내용", "의견2");
    Comment comment4 = new Comment(account2, thread2, "내용", "의견2");

    commentRepository.save(comment);
    commentRepository.save(comment2);
    commentRepository.save(comment3);
    commentRepository.save(comment4);

    Mention mention1 = new Mention(comment, comment2);
    Mention mention2 = new Mention(comment3, comment2);
    Mention mention3 = new Mention(comment4, comment2);
    mentionRepository.saveAndFlush(mention1);
    mentionRepository.saveAndFlush(mention2);
    mentionRepository.saveAndFlush(mention3);

    CommentRecommend recommend1 = new CommentRecommend(account1, comment);
    CommentRecommend recommend2 = new CommentRecommend(account2, comment);
    commentRecommendRepository.save(recommend1);
    commentRecommendRepository.save(recommend2);
//    recommendRepository.save(recommend2);
    ThreadRecommend threadRecommend = new ThreadRecommend(account1, thread1);
    threadRecommendRepository.save(threadRecommend);

  }

}
