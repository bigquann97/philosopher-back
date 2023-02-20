package gladiator.philosopher.factory;

import gladiator.philosopher.category.entity.Category;

public class CategoryFactory {

  public static Category createCategory1() {
    return new Category("철학");
  }

  public static Category createCategory2() {
    return new Category("인문");
  }

  public static Category createCategory3() {
    return new Category("연애");
  }

}
