import implementations.ArrayDeque;
import implementations.BalancedParentheses;
import implementations.DoublyLinkedList;

public class Main {
    public static void main(String[] args) {
        BalancedParentheses bp = new BalancedParentheses("{[()]}");
        System.out.println(bp.solve());
    }
}
