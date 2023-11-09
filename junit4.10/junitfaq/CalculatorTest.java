package junitfaq;


 import org.junit.*;
 import static org.junit.Assert.*;

 import java.util.*;


public class CalculatorTest {
//  private Calculator calculator;
  @Test
  public void evaluatesExpression() {
     Calculator calculator = new Calculator();
    int sum = calculator.evaluate("1+2+3");
    assertEquals(6, sum);
  }

  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("junitfaq.SimpleTest");
  }
}
