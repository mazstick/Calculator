import java.util.Stack;

/**
 * @author Mohammad Ali Zahmatkesh
 */
public class Calculate {
  private enum Operator {
    plus("+"),
    min("-"),
    mul("X"),
    div("/"),
    pow("^"),
    sin("S"),
    cos("C"),
    sqrt("S"),
    tan("T"),
    ln("L"),
    log("L");
    private final String value;
    private Operator(String value) {
      this.value = value;
    }
    ;
  }

  private Stack stack = new Stack();

  private int priority(String c) {
    switch (c) {
      case "+", "-" -> {
        return 0;
      }
      case "X", "/" -> {
        return 1;
      }
      case "^", "Sqrt" -> {
        return 2;
      }
      case "Sin", "Cos", "Tan", "Log", "Ln" -> {
        return 3;
      }
    }
    return -1;
  }

  public String[] infixToPostfixStrings(String infix) {
    int j = 0;
    String[] postfix = new String[infix.length()];
    String temp;
    for (int i = 0; i < infix.length(); i++) {
      postfix[j] = "";

      temp = String.valueOf(infix.charAt(i));

      // is number
      if (Character.isDigit(infix.charAt(i))) {
        while (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.') {
          temp += infix.charAt(i);
          i++;
          if (i == infix.length()) break;
        }
        postfix[j] = temp;
        i--;
        j++;
      } else {

        // is "("
        if (temp.equals("(")) {
          stack.push(temp);
        }

        // is ")"
        if (temp.equals(")")) {
          while (!stack.peek().equals("(")) {
            postfix[j] = String.valueOf(stack.pop());
            j++;
          }
          stack.pop();
        }

        // is nonsymbol operator
        if (Character.isLetter(temp.charAt(0))) {
          i++;
          while (Character.isLetter(infix.charAt(i))) {
            temp += infix.charAt(i);
            i++;
          }
          stack.push(temp);
          i--;
        }

        // is Symbol operator
        if (!temp.equals("(")
            &&  (temp.equals(Operator.plus.value)
                || temp.equals(Operator.min.value)
                || temp.equals(Operator.div.value)
                || temp.equals(Operator.mul.value)
                || temp.equals(Operator.pow.value))) {
          while (stack.size() > 0
              && !String.valueOf(stack.peek()).equals("(")
              && priority(temp) <= priority(String.valueOf(stack.peek()))) {
              postfix[j] = String.valueOf(stack.pop());
              j++;
          }
          stack.push(temp);
        }
      }
    }
    while(stack.size()>0){
      postfix[j] = String.valueOf(stack.pop());
      j++;
    }
    return postfix;
  }
}
