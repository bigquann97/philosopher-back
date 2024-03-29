//package gladiator.philosopher.common.util;
//
//import gladiator.philosopher.account.entity.Account;
//import gladiator.philosopher.account.entity.AccountImage;
//import gladiator.philosopher.account.enums.Gender;
//import gladiator.philosopher.account.enums.UserRole;
//import gladiator.philosopher.account.enums.UserStatus;
//import gladiator.philosopher.account.repository.AccountInfoRepository;
//import gladiator.philosopher.account.repository.AccountRepository;
//import gladiator.philosopher.category.entity.Category;
//import gladiator.philosopher.category.repository.CategoryRepository;
//import gladiator.philosopher.comment.entity.Comment;
//import gladiator.philosopher.comment.entity.Mention;
//import gladiator.philosopher.comment.repository.CommentRepository;
//import gladiator.philosopher.comment.repository.MentionRepository;
//import gladiator.philosopher.post.entity.Post;
//import gladiator.philosopher.post.entity.PostImage;
//import gladiator.philosopher.post.entity.PostOpinion;
//import gladiator.philosopher.post.repository.PostImageRepository;
//import gladiator.philosopher.post.repository.PostOpinionRepository;
//import gladiator.philosopher.post.repository.PostRepository;
//import gladiator.philosopher.rank.entity.Rank;
//import gladiator.philosopher.rank.repository.RankRepository;
//import gladiator.philosopher.recommend.entity.CommentRecommend;
//import gladiator.philosopher.recommend.entity.PostRecommend;
//import gladiator.philosopher.recommend.entity.ThreadRecommend;
//import gladiator.philosopher.recommend.repository.CommentRecommendRepository;
//import gladiator.philosopher.recommend.repository.PostRecommendRepository;
//import gladiator.philosopher.recommend.repository.ThreadRecommendRepository;
//import gladiator.philosopher.report.entity.CommentReport;
//import gladiator.philosopher.report.entity.PostReport;
//import gladiator.philosopher.report.entity.ThreadReport;
//import gladiator.philosopher.report.enums.ReportCategory;
//import gladiator.philosopher.report.repository.comment.CommentReportRepository;
//import gladiator.philosopher.report.repository.post.PostReportRepository;
//import gladiator.philosopher.report.repository.thread.ThreadReportRepository;
//import gladiator.philosopher.thread.entity.Thread;
//import gladiator.philosopher.thread.entity.ThreadImage;
//import gladiator.philosopher.thread.entity.ThreadOpinion;
//import gladiator.philosopher.thread.repository.ThreadImageRepository;
//import gladiator.philosopher.thread.repository.ThreadOpinionRepository;
//import gladiator.philosopher.thread.repository.ThreadRepository;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//@Transactional
//public class InitData implements ApplicationRunner {
//
//  private final PasswordEncoder passwordEncoder;
//  private final AccountRepository accountRepository;
//  private final ThreadRepository threadRepository;
//  private final PostRepository postRepository;
//  private final PostImageRepository postImageRepository;
//  private final AccountInfoRepository accountInfoRepository;
//  private final PostOpinionRepository postOpinionRepository;
//  private final CategoryRepository categoryRepository;
//  private final CommentRepository commentRepository;
//  private final MentionRepository mentionRepository;
//  private final ThreadOpinionRepository threadOpinionRepository;
//  private final PostRecommendRepository postRecommendRepository;
//  private final CommentRecommendRepository commentRecommendRepository;
//  private final ThreadRecommendRepository threadRecommendRepository;
//  private final PostReportRepository postReportRepository;
//  private final ThreadReportRepository threadReportRepository;
//  private final ThreadImageRepository threadImageRepository;
//  private final CommentReportRepository commentReportRepository;
//  private final RankRepository rankRepository;
//
//
//  @Override
//  @Transactional
//  public void run(ApplicationArguments args) throws Exception {
//
//    // 회원부 ( 10명 )
//    Account account1 = new Account(1L, "wlsdn7717@naver.com", passwordEncoder.encode("rlawlghks1"),
//        20, "김지환", Gender.FEMALE, UserRole.ROLE_MANAGER, UserStatus.ACTIVATED);
//    accountRepository.save(account1);
//    AccountImage accountImage1 = new AccountImage(account1, "jipang1.jpg");
//    accountInfoRepository.save(accountImage1);
//
//    Account account2 = new Account(2L, "test1@naver.com", passwordEncoder.encode("rlawlghks1"), 40,
//        "박정수", Gender.MALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
//    accountRepository.save(account2);
//    AccountImage accountImage2 = new AccountImage(account2,
//        "https://the-gladiators.s3.ap-northeast-2.amazonaws.com/AccountImg/cbf685ea-6cb2-4448-872d-0e503a7eddddimage");
//    accountInfoRepository.save(accountImage2);
//
//    Account account3 = new Account(3L, "test2@naver.com", passwordEncoder.encode("rlawlghks1"), 40,
//        "이정국", Gender.MALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
//    accountRepository.save(account3);
//    AccountImage accountImage3 = new AccountImage(account3, "jipang3.jpg");
//    accountInfoRepository.save(accountImage3);
//
//    Account account4 = new Account(4L, "test3@naver.com", passwordEncoder.encode("rlawlghks1"), 80,
//        "하규호", Gender.MALE, UserRole.ROLE_ADMIN, UserStatus.ACTIVATED);
//    accountRepository.save(account4);
//
//    Account account5 = new Account(5L, "test4@naver.com", passwordEncoder.encode("rlawlghks1"), 100,
//        "김관호", Gender.FEMALE, UserRole.ROLE_MANAGER, UserStatus.SUSPENDED);
//    accountRepository.save(account5);
//
//    Account account6 = new Account(6L, "test5@naver.com", passwordEncoder.encode("rlawlghks1"), 10,
//        "이승윤", Gender.MALE, UserRole.ROLE_MANAGER, UserStatus.ACTIVATED);
//    accountRepository.save(account6);
//    Account account7 = new Account(7L, "test6@naver.com", passwordEncoder.encode("rlawlghks1"), 30,
//        "김미란", Gender.MALE, UserRole.ROLE_USER, UserStatus.SUSPENDED);
//    accountRepository.save(account7);
//    Account account8 = new Account(8L, "test7@naver.com", passwordEncoder.encode("rlawlghks1"), 31,
//        "박도화", Gender.FEMALE, UserRole.ROLE_MANAGER, UserStatus.ACTIVATED);
//    accountRepository.save(account8);
//    Account account9 = new Account(9L, "test8@naver.com", passwordEncoder.encode("rlawlghks1"), 35,
//        "박연주", Gender.FEMALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
//    accountRepository.save(account9);
//    Account account10 = new Account(10L, "test9@naver.com", passwordEncoder.encode("rlawlghks1"),
//        37,
//        "김두이", Gender.MALE, UserRole.ROLE_USER, UserStatus.SUSPENDED);
//    accountRepository.save(account10);
//
//    // 카테고리
//    Category category1 = new Category("철학");
//    categoryRepository.save(category1);
//    Category category2 = new Category("인문");
//    categoryRepository.save(category2);
//    Category category3 = new Category("사회");
//    categoryRepository.save(category3);
//    Category category4 = new Category("연애");
//    categoryRepository.save(category4);
//    Category category5 = new Category("논쟁");
//    categoryRepository.save(category5);
//
//    // 게시글 부
//    Post post1 = new Post(account1, " 깻잎 논쟁 보고만 있나요? ", "김지환, 박정수", category1);
//    postRepository.save(post1);
//
//    Post post2 = new Post(account2, " 닭이 먼저인가요 치킨이 먼저인가요 ", "박정수, 박건하", category1);
//    postRepository.save(post2);
//
//    Post post3 = new Post(account3, " 아 배고프다 ", "박건하, 이정국", category2);
//    postRepository.save(post3);
//
//    Post post4 = new Post(account4, " 5억년 버튼 누르실 건가여? ", "이정국 ,이진호", category2);
//    postRepository.save(post4);
//
//    Post post5 = new Post(account4, " 원더걸스 vs 뉴진스 ", "이진호, 이명박", category4);
//    postRepository.save(post5);
//
//    Post post6 = new Post(account5, " 여자친구 앞에서 이성 반찬 밀어주기 ", "이명박, 박명수", category5);
//    postRepository.save(post6);
//    Post post7 = new Post(account6, " 팥붕 vs 슈붕 ", "이명박, 존박", category3);
//    postRepository.save(post7);
//    Post post8 = new Post(account7, " 길가는 사람에게 한대맞으면 5000원? ", "이명박, 박명수", category3);
//    postRepository.save(post8);
//    Post post9 = new Post(account8, " 1년뒤 1억 10년뒤 5억 ", " 9번 게시물의 내용입니다 ", category2);
//    postRepository.save(post9);
//    Post post10 = new Post(account9, " 수저 세팅 ", "10번 게시물의 내용입니다", category5);
//    postRepository.save(post10);
//    Post post11 = new Post(account10, " 아 몰라 ", "11번 게시물의 내용입니다", category5);
//    postRepository.save(post11);
//    Post post12 = new Post(account9, " 못참아 ", "12번 게시물의 내용입니다", category4);
//    postRepository.save(post12);
//    Post post13 = new Post(account8, " 사후세계? ", "이명박, 박명수", category1);
//    postRepository.save(post13);
//    Post post14 = new Post(account7, " 메이웨더 vs 파퀴아오 ", "이명박, 박명수", category1);
//    postRepository.save(post14);
//    Post post15 = new Post(account6, " 안락사 ", "김지환", category2);
//    postRepository.save(post15);
//    Post post16 = new Post(account5, " 죽음이란? ", "배고프다", category2);
//    postRepository.save(post16);
//    Post post17 = new Post(account4, " 지옥 있나요?  ", "네 없습니다", category2);
//    postRepository.save(post17);
//
//    // 포스트 이미지
//    PostImage postImage = new PostImage(
//        "https://the-gladiators.s3.ap-northeast-2.amazonaws.com/postImg/84e41f39-7dee-404f-b27e-2c1de1ce3f25image",
//        post1);
//    postImageRepository.save(postImage);
//    PostImage postImage2 = new PostImage(
//        "https://the-gladiators.s3.ap-northeast-2.amazonaws.com/postImg/974acf4c-6b79-4515-9c84-fda9b221d51cimage",
//        post2);
//    postImageRepository.save(postImage2);
//    PostImage postImage3 = new PostImage(
//        "https://the-gladiators.s3.ap-northeast-2.amazonaws.com/postImg/416b76d0-e852-475c-b2eb-1a1bb6da53c4image",
//        post2);
//    postImageRepository.save(postImage3);
//    PostImage postImage4 = new PostImage("test4.png", post3);
//    postImageRepository.save(postImage4);
//    PostImage postImage5 = new PostImage("test5.png", post5);
//    postImageRepository.save(postImage5);
//
//    // 회원 1~5 // 게시글 1~6
//    // 댓글부
//    PostRecommend recommend17 = new PostRecommend(account1, post1);
//    postRecommendRepository.save(recommend17);
//
//    PostRecommend recommend16 = new PostRecommend(account1, post3);
//    postRecommendRepository.save(recommend16);
//
//    PostRecommend recommend3 = new PostRecommend(account1, post4);
//    postRecommendRepository.save(recommend3);
//    PostRecommend recommend4 = new PostRecommend(account2, post2);
//    postRecommendRepository.save(recommend4);
//    PostRecommend recommend5 = new PostRecommend(account2, post4);
//    postRecommendRepository.save(recommend5);
//    PostRecommend recommend6 = new PostRecommend(account2, post6);
//    postRecommendRepository.save(recommend6);
//    PostRecommend recommend7 = new PostRecommend(account3, post1);
//    postRecommendRepository.save(recommend7);
//
//    PostRecommend recommend8 = new PostRecommend(account3, post5);
//    postRecommendRepository.save(recommend8);
//
//    PostRecommend recommend9 = new PostRecommend(account3, post6);
//    postRecommendRepository.save(recommend9);
//
//    PostRecommend recommend10 = new PostRecommend(account4, post2);
//    postRecommendRepository.save(recommend10);
//    PostRecommend recommend11 = new PostRecommend(account4, post6);
//    postRecommendRepository.save(recommend11);
//    PostRecommend recommend12 = new PostRecommend(account4, post4);
//    postRecommendRepository.save(recommend12);
//    PostRecommend recommend13 = new PostRecommend(account5, post2);
//    postRecommendRepository.save(recommend13);
//
//    PostRecommend recommend14 = new PostRecommend(account5, post3);
//    postRecommendRepository.save(recommend14);
//
//    PostRecommend recommend15 = new PostRecommend(account5, post4);
//    postRecommendRepository.save(recommend15);
//
//    PostRecommend recommend26 = new PostRecommend(account4, post1);
//    postRecommendRepository.save(recommend26);
//    PostRecommend recommend27 = new PostRecommend(account4, post17);
//    postRecommendRepository.save(recommend27);
//    PostRecommend recommend18 = new PostRecommend(account4, post16);
//    postRecommendRepository.save(recommend18);
//    PostRecommend recommend19 = new PostRecommend(account4, post15);
//    postRecommendRepository.save(recommend19);
//    PostRecommend recommend20 = new PostRecommend(account4, post14);
//    postRecommendRepository.save(recommend20);
//    PostRecommend recommend21 = new PostRecommend(account4, post13);
//    postRecommendRepository.save(recommend21);
//    PostRecommend recommend22 = new PostRecommend(account4, post12);
//    postRecommendRepository.save(recommend22);
//    PostRecommend recommend23 = new PostRecommend(account4, post3);
//    postRecommendRepository.save(recommend23);
//    PostRecommend recommend24 = new PostRecommend(account4, post5);
//    postRecommendRepository.save(recommend24);
//    PostRecommend recommend25 = new PostRecommend(account4, post7);
//    postRecommendRepository.save(recommend25);
//
//    List<String> opinions = Arrays.asList("opinion1", "opinion2", "opinion3");
//    List<String> opinion1 = Arrays.asList("한다", "안한다", "입닥쳐라");
//    List<String> opinion2 = Arrays.asList("누른다", "안누른다", "모르겠다");
//    List<String> opinion3 = Arrays.asList("틀렸다", "맞다");
//    List<String> opinion4 = Arrays.asList("니 말이 다 맞음", "내 말이 다 맞음");
//    List<String> opinion5 = Arrays.asList("아니랑께", "맞당께");
//
//    PostReport postReport = new PostReport("이사람 이상합니다,", account3, account6, ReportCategory.ABUSE,
//        post8.getId());
//    postReportRepository.save(postReport);
//    PostReport postReport2 = new PostReport("커피땅콩", account5, account8, ReportCategory.ABUSE,
//        post10.getId());
//    postReportRepository.save(postReport2);
//    PostReport postReport3 = new PostReport("알고리즘 책", account6, account9, ReportCategory.ABUSE,
//        post6.getId());
//    postReportRepository.save(postReport3);
//    PostReport postReport4 = new PostReport("김영한", account7, account4, ReportCategory.ABUSE,
//        post4.getId());
//    postReportRepository.save(postReport4);
//    PostReport postReport5 = new PostReport("인프런", account8, account1, ReportCategory.ADVERTISEMENT,
//        post2.getId());
//    postReportRepository.save(postReport5);
//    PostReport postReport6 = new PostReport("프로그래밍", account9, account6, ReportCategory.ABUSE,
//        post1.getId());
//    postReportRepository.save(postReport6);
//    PostReport postReport7 = new PostReport("신고합니다", account10, account3,
//        ReportCategory.SEXUAL_HARASSMENT, post3.getId());
//    postReportRepository.save(postReport7);
//    PostReport postReport8 = new PostReport("내배캠 118", account8, account5,
//        ReportCategory.ADVERTISEMENT, post5.getId());
//    postReportRepository.save(postReport8);
//    PostReport postReport9 = new PostReport("이펙티브 자바", account6, account3,
//        ReportCategory.SEXUAL_HARASSMENT, post7.getId());
//    postReportRepository.save(postReport9);
//    PostReport postReport10 = new PostReport("우분투 리눅스", account4, account1, ReportCategory.ABUSE,
//        post9.getId());
//    postReportRepository.save(postReport10);
//    PostReport postReport11 = new PostReport("갤럭시 이온", account2, account6,
//        ReportCategory.ADVERTISEMENT, post8.getId());
//    postReportRepository.save(postReport11);
//    PostReport postReport12 = new PostReport("백북", account1, account4,
//        ReportCategory.SEXUAL_HARASSMENT, post7.getId());
//    postReportRepository.save(postReport12);
//    PostReport postReport13 = new PostReport("빅맥", account3, account6, ReportCategory.ABUSE,
//        post6.getId());
//    postReportRepository.save(postReport13);
//    PostReport postReport14 = new PostReport("감자탕", account5, account6,
//        ReportCategory.SEXUAL_HARASSMENT, post5.getId());
//    postReportRepository.save(postReport14);
//    PostReport postReport15 = new PostReport("치즈", account10, account9,
//        ReportCategory.ADVERTISEMENT, post4.getId());
//    postReportRepository.save(postReport15);
//
//    List<PostOpinion> list = opinions.stream().map(x -> new PostOpinion(post1, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list2 = opinion1.stream().map(x -> new PostOpinion(post2, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list3 = opinion2.stream().map(x -> new PostOpinion(post3, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list4 = opinion3.stream().map(x -> new PostOpinion(post4, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list5 = opinion2.stream().map(x -> new PostOpinion(post5, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list6 = opinion1.stream().map(x -> new PostOpinion(post6, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list7 = opinion3.stream().map(x -> new PostOpinion(post7, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list8 = opinion5.stream().map(x -> new PostOpinion(post8, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list9 = opinion5.stream().map(x -> new PostOpinion(post9, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list10 = opinion3.stream().map(x -> new PostOpinion(post10, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list11 = opinion2.stream().map(x -> new PostOpinion(post11, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list12 = opinion4.stream().map(x -> new PostOpinion(post12, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list13 = opinion1.stream().map(x -> new PostOpinion(post13, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list14 = opinion3.stream().map(x -> new PostOpinion(post14, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list15 = opinion5.stream().map(x -> new PostOpinion(post15, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list16 = opinion2.stream().map(x -> new PostOpinion(post16, x))
//        .collect(Collectors.toList());
//    List<PostOpinion> list17 = opinion4.stream().map(x -> new PostOpinion(post17, x))
//        .collect(Collectors.toList());
//
//    postOpinionRepository.saveAll(list);
//    postOpinionRepository.saveAll(list2);
//    postOpinionRepository.saveAll(list3);
//    postOpinionRepository.saveAll(list4);
//    postOpinionRepository.saveAll(list5);
//    postOpinionRepository.saveAll(list6);
//    postOpinionRepository.saveAll(list7);
//    postOpinionRepository.saveAll(list8);
//    postOpinionRepository.saveAll(list9);
//    postOpinionRepository.saveAll(list10);
//    postOpinionRepository.saveAll(list11);
//    postOpinionRepository.saveAll(list12);
//    postOpinionRepository.saveAll(list13);
//    postOpinionRepository.saveAll(list14);
//    postOpinionRepository.saveAll(list15);
//    postOpinionRepository.saveAll(list16);
//    postOpinionRepository.saveAll(list17);
//
//    // 쓰레드 부
//    Thread thread1 = new Thread(post4.getTitle(), post4.getContent(), account4, LocalDateTime.now(),
//        null);
//    threadRepository.save(thread1);
//    Thread thread2 = new Thread(post5.getTitle(), post5.getContent(), account5, LocalDateTime.now(),
//        null);
//    threadRepository.save(thread2);
//    ThreadImage threadImage = new ThreadImage(
//        "https://the-gladiators.s3.ap-northeast-2.amazonaws.com/postImg/ec6c575a-7c1b-4f93-9f5c-724137154e66image",
//        thread1);
//    threadImageRepository.save(threadImage);
//    Thread thread3 = new Thread(post6.getTitle(), post6.getContent(), post6.getAccount(),
//        LocalDateTime.now(),
//        post6.getCategory());
//    threadRepository.save(thread3);
//    Thread thread4 = new Thread(post7.getTitle(), post7.getContent(), post7.getAccount(),
//        LocalDateTime.now(),
//        post7.getCategory());
//    threadRepository.save(thread4);
//    Thread thread5 = new Thread(post8.getTitle(), post8.getContent(), post8.getAccount(),
//        LocalDateTime.now(),
//        post8.getCategory());
//    threadRepository.save(thread5);
//    Thread thread6 = new Thread(post9.getTitle(), post9.getContent(), post9.getAccount(),
//        LocalDateTime.now(),
//        post9.getCategory());
//    threadRepository.save(thread6);
//
//    ThreadOpinion threadOpinion1 = new ThreadOpinion(thread1, "한다");
//    ThreadOpinion threadOpinion2 = new ThreadOpinion(thread1, "안한다");
//    threadOpinionRepository.save(threadOpinion1);
//    threadOpinionRepository.save(threadOpinion2);
//
//    Comment comment = new Comment(account1, thread2, "내용", "의견1");
//    Comment comment2 = new Comment(account2, thread2, "내용", "의견2");
//    Comment comment3 = new Comment(account2, thread2, "내용", "의견2");
//    Comment comment4 = new Comment(account2, thread2, "내용", "의견2");
//
//    commentRepository.save(comment);
//    commentRepository.save(comment2);
//    commentRepository.save(comment3);
//    commentRepository.save(comment4);
//
//    Mention mention1 = new Mention(comment, comment2);
//    Mention mention2 = new Mention(comment3, comment2);
//    Mention mention3 = new Mention(comment4, comment2);
//    mentionRepository.saveAndFlush(mention1);
//    mentionRepository.saveAndFlush(mention2);
//    mentionRepository.saveAndFlush(mention3);
//
//    CommentRecommend recommend1 = new CommentRecommend(account1, comment);
//    CommentRecommend recommend2 = new CommentRecommend(account2, comment);
//    commentRecommendRepository.save(recommend1);
//    commentRecommendRepository.save(recommend2);
////    recommendRepository.save(recommend2);
//    ThreadRecommend threadRecommend = new ThreadRecommend(account1, thread1);
//    threadRecommendRepository.save(threadRecommend);
////    ThreadRecommend threadRecommend1 = new ThreadRecommend(account4, thread1);
////    threadRecommendRepository.save(threadRecommend1);
////    ThreadRecommend threadRecommend2 = new ThreadRecommend(account4, thread1);
////    threadRecommendRepository.save(threadRecommend2);
////    ThreadRecommend threadRecommend3 = new ThreadRecommend(account4, thread1);
////    threadRecommendRepository.save(threadRecommend3);
////    ThreadRecommend threadRecommend4 = new ThreadRecommend(account4, thread1);
////    threadRecommendRepository.save(threadRecommend4);
////    ThreadRecommend threadRecommend5 = new ThreadRecommend(account4, thread1);
////    threadRecommendRepository.save(threadRecommend5);
////    ThreadRecommend threadRecommend6 = new ThreadRecommend(account4, thread1);
////    threadRecommendRepository.save(threadRecommend6);
////    ThreadRecommend threadRecommend7 = new ThreadRecommend(account4, thread1);
////    threadRecommendRepository.save(threadRecommend7);
////    ThreadRecommend threadRecommend8 = new ThreadRecommend(account4, thread1);
////    threadRecommendRepository.save(threadRecommend8);
////    ThreadRecommend threadRecommend9 = new ThreadRecommend(account4, thread1);
////    threadRecommendRepository.save(threadRecommend9);
////    ThreadRecommend threadRecommend10 = new ThreadRecommend(account4, thread6);
////    threadRecommendRepository.save(threadRecommend10);
////    ThreadRecommend threadRecommend11 = new ThreadRecommend(account4, thread4);
////    threadRecommendRepository.save(threadRecommend11);
////    ThreadRecommend threadRecommend12 = new ThreadRecommend(account4, thread2);
////    threadRecommendRepository.save(threadRecommend12);
////    ThreadRecommend threadRecommend13 = new ThreadRecommend(account4, thread5);
////    threadRecommendRepository.save(threadRecommend13);
////    ThreadRecommend threadRecommend14 = new ThreadRecommend(account4, thread3);
////    threadRecommendRepository.save(threadRecommend14);
////    ThreadRecommend threadRecommend15 = new ThreadRecommend(account4, thread1);
////    threadRecommendRepository.save(threadRecommend15);
//
//    for (int i = 0; i < 123; i++) {
//      Thread thread = new Thread("title" + i, "content" + i, account1,
//          LocalDateTime.now().plusDays(1L), category1);
//      threadRepository.save(thread);
//      ThreadRecommend threadRecommend10 = new ThreadRecommend(account4, thread);
//      threadRecommendRepository.save(threadRecommend10);
//    }
//
//    Thread thread = new Thread("테스트용 데이터", "콘텐츠", account1, LocalDateTime.now().plusDays(1L),
//        category1);
//    threadRepository.save(thread);
//    ThreadOpinion to1 = new ThreadOpinion(thread, "한다.");
//    ThreadOpinion to2 = new ThreadOpinion(thread, "안한다.");
//    ThreadOpinion to3 = new ThreadOpinion(thread, "무관.");
//
//    threadOpinionRepository.saveAll(List.of(to1, to2, to3));
//
//    for (int i = 0; i < 123; i++) {
//      Comment comment1 = new Comment(account2, thread, "content", "한다.");
//      commentRepository.save(comment1);
//    }
//    Comment cm1 = new Comment(account2, thread, "content", "opinion");
//    commentRepository.save(cm1);
//
//    Comment cm2 = new Comment(account2, thread, "content", "opinion");
//    commentRepository.save(cm2);
//
//    Comment cm3 = new Comment(account2, thread, "content", "opinion");
//    commentRepository.save(cm3);
//
//    CommentRecommend commentRecommend1 = new CommentRecommend(account1, cm1);
//    CommentRecommend commentRecommend2 = new CommentRecommend(account2, cm1);
//    CommentRecommend commentRecommend3 = new CommentRecommend(account3, cm1);
//    CommentRecommend commentRecommend4 = new CommentRecommend(account1, cm2);
//    CommentRecommend commentRecommend5 = new CommentRecommend(account2, cm2);
//    CommentRecommend commentRecommend6 = new CommentRecommend(account1, cm3);
//    commentRecommendRepository.save(commentRecommend1);
//    commentRecommendRepository.save(commentRecommend2);
//    commentRecommendRepository.save(commentRecommend3);
//    commentRecommendRepository.save(commentRecommend4);
//    commentRecommendRepository.save(commentRecommend5);
//
//    // 신고 관련 init 데이터 ( 쓰레드 신고 )
//    ThreadReport threadReport = new ThreadReport("신고입니다", account2, account2, ReportCategory.ABUSE,
//        thread3.getId());
//    threadReportRepository.save(threadReport);
//
//    ThreadReport threadReport2 = new ThreadReport("신고입니다1", account3, account1,
//        ReportCategory.SEXUAL_HARASSMENT, thread4.getId());
//    threadReportRepository.save(threadReport2);
//
//    ThreadReport threadReport3 = new ThreadReport("신고입니다2", account4, account10,
//        ReportCategory.SPAMMER, thread5.getId());
//    threadReportRepository.save(threadReport3);
//
//    ThreadReport threadReport4 = new ThreadReport("신고입니다3", account5, account9,
//        ReportCategory.IRRELEVANT, thread6.getId());
//    threadReportRepository.save(threadReport4);
//
//    ThreadReport threadReport5 = new ThreadReport("신고입니다4", account6, account8,
//        ReportCategory.ABUSE, thread1.getId());
//    threadReportRepository.save(threadReport5);
//
//    ThreadReport threadReport6 = new ThreadReport("신고입니다5", account7, account7,
//        ReportCategory.SPAMMER, thread2.getId());
//    threadReportRepository.save(threadReport6);
//
//    ThreadReport threadReport7 = new ThreadReport("신고입니다6", account8, account2,
//        ReportCategory.IRRELEVANT, thread3.getId());
//    threadReportRepository.save(threadReport7);
//
//    ThreadReport threadReport8 = new ThreadReport("신고입니다7", account9, account3,
//        ReportCategory.SEXUAL_HARASSMENT, thread2.getId());
//    threadReportRepository.save(threadReport8);
//
//    ThreadReport threadReport9 = new ThreadReport("신고입니다8", account1, account4,
//        ReportCategory.SEXUAL_HARASSMENT, thread4.getId());
//    threadReportRepository.save(threadReport9);
//
//    ThreadReport threadReport10 = new ThreadReport("신고입니다9", account2, account5,
//        ReportCategory.ABUSE, thread1.getId());
//    threadReportRepository.save(threadReport10);
//
//    ThreadReport threadReport11 = new ThreadReport("신고입니다10", account2, account6,
//        ReportCategory.ABUSE, thread2.getId());
//    threadReportRepository.save(threadReport11);
//
//    // 댓글 신고
//    CommentReport commentReport = new CommentReport("댓글 신고입니다", account2, account6,
//        ReportCategory.ABUSE, comment3.getId());
//    commentReportRepository.save(commentReport);
//
//    CommentReport commentReport1 = new CommentReport("댓글 신고입니다1", account3, account7,
//        ReportCategory.SEXUAL_HARASSMENT, comment2.getId());
//    commentReportRepository.save(commentReport1);
//
//    CommentReport commentReport2 = new CommentReport("댓글 신고입니다2", account4, account9,
//        ReportCategory.SPAMMER, comment4.getId());
//    commentReportRepository.save(commentReport2);
//
//    CommentReport commentReport3 = new CommentReport("댓글 신고입니다3", account5, account10,
//        ReportCategory.SEXUAL_HARASSMENT, comment.getId());
//    commentReportRepository.save(commentReport3);
//
//    CommentReport commentReport4 = new CommentReport("댓글 신고입니다4", account6, account1,
//        ReportCategory.ABUSE, comment2.getId());
//    commentReportRepository.save(commentReport4);
//
//    CommentReport commentReport5 = new CommentReport("댓글 신고입니다5", account7, account2,
//        ReportCategory.SPAMMER, comment3.getId());
//    commentReportRepository.save(commentReport5);
//
//    CommentReport commentReport6 = new CommentReport("댓글 신고입니다6", account8, account3,
//        ReportCategory.SEXUAL_HARASSMENT, comment.getId());
//    commentReportRepository.save(commentReport6);
//
//    CommentReport commentReport7 = new CommentReport("댓글 신고입니다7", account9, account4,
//        ReportCategory.ABUSE, comment2.getId());
//    commentReportRepository.save(commentReport7);
//
//    CommentReport commentReport8 = new CommentReport("댓글 신고입니다8", account10, account5,
//        ReportCategory.SPAMMER, comment3.getId());
//    commentReportRepository.save(commentReport8);
//
//    CommentReport commentReport9 = new CommentReport("댓글 신고입니다9", account1, account6,
//        ReportCategory.SEXUAL_HARASSMENT, comment.getId());
//    commentReportRepository.save(commentReport9);
//
//    CommentReport commentReport10 = new CommentReport("댓글 신고입니다10", account2, account7,
//        ReportCategory.ABUSE, comment2.getId());
//    commentReportRepository.save(commentReport10);
//
//    Rank rank1 = Rank.builder().nickname("하규호").count(3L).build();
//    rankRepository.save(rank1);
//    Rank rank2 = Rank.builder().nickname("이정국").count(1L).build();
//    rankRepository.save(rank2);
//    Rank rank3 = Rank.builder().nickname("박정수").count(5L).build();
//    rankRepository.save(rank3);
//    Rank rank4 = Rank.builder().nickname("박도화").count(4L).build();
//    rankRepository.save(rank4);
//    Rank rank5 = Rank.builder().nickname("박연주").count(7L).build();
//    rankRepository.save(rank5);
//    Rank rank6 = Rank.builder().nickname("김지환").count(6L).build();
//    rankRepository.save(rank6);
//  }
//
//}
