import implementations.Tree;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

//        Tree<Integer> tree = new Tree<>(1,
//                new Tree<>(2, new Tree<>(3, new Tree<>(4))),
//                new Tree<>(5, new Tree<>(6, new Tree<>(7))));

        Tree<Integer> tree =  new Tree<>(7,
                new Tree<>(19,
                        new Tree<>(1),
                        new Tree<>(12),
                        new Tree<>(31)),
                new Tree<>(21),
                new Tree<>(14,
                        new Tree<>(23),
                        new Tree<>(6))
        );

        //System.out.println(tree);
        tree.orderBfs()
                .forEach(System.out::println);
        System.out.println("=================");
        tree.orderDfs()
                .forEach(System.out::println);
        System.out.println("=================");
        tree.addChild(1, new Tree<>(99));
        System.out.println("=================");
        tree.orderDfs()
                .forEach(System.out::println);
    }
}
