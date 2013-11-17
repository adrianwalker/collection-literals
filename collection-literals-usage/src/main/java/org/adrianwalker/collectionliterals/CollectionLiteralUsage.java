package org.adrianwalker.collectionliterals;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CollectionLiteralUsage {

  /**
   [1,2,3,4,5,6,7,8,9,10]
   */
  @LinkedList
  private List<Integer> list;
  /**
   ["0","1","2","3","4","5","6","7", 
   "8","9","A","B","C","D","E","F"]
   */
  @HashSet
  private Set<String> set;
  /**
   {
     "name":"Adrian Walker", 
     "age":31 
   }
   */
  @HashMap
  private Map<String, Object> map;

  public List getList() {
    return list;
  }

  public Set<String> getSet() {
    return set;
  }

  public Map getMap() {
    return map;
  }

  public static void main(final String[] args) {
    CollectionLiteralUsage collectionUsage = new CollectionLiteralUsage();
    System.out.println(collectionUsage.getList());
    System.out.println(collectionUsage.getSet());
    System.out.println(collectionUsage.getMap());
  }
}
