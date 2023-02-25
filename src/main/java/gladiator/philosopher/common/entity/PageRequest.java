package gladiator.philosopher.common.entity;

public class PageRequest {

  private int page = 1;
  private int size = 10;


  public void setPage(int page){
    this.page=page<=0?1:page;
  }
  public void setSize(int size){
    int DEFAULT_SIZE= 10;
    int MAX_SIZE =20;
    this.size = size>MAX_SIZE?DEFAULT_SIZE:size;
  }
  public org.springframework.data.domain.PageRequest of(){
    return org.springframework.data.domain.PageRequest.of(page-1,size);
  }
}
