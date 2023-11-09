package junitfaq;

 import org.junit.*;
 import static org.junit.Assert.*;

 import java.util.*;

 public class SimpleTest {
 @Test
 public void testEmptyCollection() {
 	Collection collection = new ArrayList();
 	assertTrue(collection.isEmpty());
 }
 public static void main(String args[]) {
   org.junit.runner.JUnitCore.main("junitfaq.SimpleTest");
 }
}
