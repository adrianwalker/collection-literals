package org.adrianwalker.collectionliterals;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CollectionLiteralUsageStatic {

  /**
   [1,2,3,4,5,6,7,8,9,10]
   */
  @ArrayList
  private static List<Integer> list;
  /**
   ["0","1","2","3","4","5","6","7",
   "8","9","A","B","C","D","E","F"]
   */
  @HashSet
  private static Set<String> set;  
  /**
   {
     "name":"Adrian Walker", 
     "age":31 
   }
   */
  @HashMap
  private static Map<String, Object> map;

  public static void main(final String[] args) {
    System.out.println(list);
    System.out.println(set);
    System.out.println(map);
  }
}