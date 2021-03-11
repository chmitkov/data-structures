package implementations;

import interfaces.Solvable;

import java.util.Stack;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        if (parentheses.length() % 2 != 0) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < parentheses.length(); i++) {
            char current = parentheses.charAt(i);
            if (current == '{' || current == '[' || current == '(') {
                stack.push(current);
            } else {
                if (current == stack.peek() + 1 ||
                        current == stack.peek() + 2 ) {
                    stack.pop();
                } else {
                     return false;
                }
            }
        }

        return true;
    }
}
