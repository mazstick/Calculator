import java.util.Arrays;
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
    pow("^");
    private final String value;

    private Operator(String value) {
      this.value = value;
    }
    ;
  }

  public String evaluation(String infix, boolean isDegree) {

    String postfix[] = infixToPostfixStrings(infix);
    for (int i = 0; i < postfix.length; i++) {
      if (postfix[i] == null) break;
      String temp = postfix[i];
      if (isOperator(postfix[i])) {
        double resulte = 0;
        if (temp.equals("+")
            || temp.equals("-")
            || temp.equals("X")
            || temp.equals("/")
            || temp.equals("^")) {
          double op2 = Double.parseDouble(String.valueOf(stack.pop()));
          double op1 = Double.parseDouble(String.valueOf(stack.pop()));

          switch (temp) {
            case "+" -> {
              resulte = op1 + op2;
              stack.push(resulte);
            }
            case "-" -> {
              resulte = op1 - op2;
              stack.push(resulte);
            }
            case "/" -> {
              resulte = op1 / op2;
              stack.push(resulte);
            }
            case "X" -> {
              resulte = op1 * op2;
              stack.push(resulte);
            }
            case "^" -> {
              resulte = Math.pow(op1, op2);
              stack.push(resulte);
            }
          }
        } else {
          double op1 = Double.parseDouble(stack.pop().toString());
          resulte = 0;
          switch (temp) {
            case "Sin" -> {
              resulte = (isDegree) ? Math.sin(op1 * (Math.PI / 180.0)) : Math.sin(op1);
              stack.push(resulte);
            }
            case "Cos" -> {
              resulte = (isDegree) ? Math.cos(op1 * (Math.PI / 180.0)) : Math.cos(op1);
              stack.push(resulte);
            }
            case "Tan" -> {
              resulte = (isDegree) ? Math.tan(op1 * (Math.PI / 180.0)) : Math.tan(op1);
              stack.push(resulte);
            }
            case "Ln" -> {
              resulte = Math.log(op1);
              stack.push(resulte);
            }
            case "Log" -> {
              resulte = Math.log10(op1);
              stack.push(resulte);
            }
            case "Sqrt" -> {
              resulte = Math.sqrt(op1);
              stack.push(resulte);
            }
          }
        }
      } else {
        stack.push(temp);
      }
    }

    return stack.pop().toString();
  }

  private boolean isOperator(String c) {
    if (Character.isDigit(c.charAt(0))) {
      return false;
    } else return true;
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
        temp="";
        while (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.') {
          temp += infix.charAt(i);
          i++;

          if (i == infix.length()) {
            break;
          }
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
        if (Character.isLetter(temp.charAt(0)) && temp.charAt(0)!= 'X') {
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
            && (temp.equals("+")
                || temp.equals("-")
                || temp.equals("/")
                || temp.equals("X")
                || temp.equals("^"))) {
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
    while (stack.size() > 0) {
      if (stack.peek().equals("(")) 
      {
        stack.pop();
        continue;
      }
        postfix[j] = String.valueOf(stack.pop());
      j++;
    }
    
    return postfix;
  }
}
