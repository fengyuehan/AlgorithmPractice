package com.example.myapplication;

import java.util.LinkedList;
import java.util.Queue;

public class Tree {
    /**
     * 二叉树的最小深度
     * 1 深度优先
     * 2 广度优先
     */
    public int minDeep(TreeNode root){
        if (root == null){
            return 0;
        }
        int min = Integer.MAX_VALUE;
        if (root.getLeft() != null){
            min = Math.min(minDeep(root.getLeft()) ,min) + 1;
        }

        if (root.getRight() != null){
            min = Math.min(min,minDeep(root.getRight())) + 1;
        }

        return min;
    }

    /**
     * 广度优先  采用队列的先进先出性质
     */
    public int minDeep2(TreeNode root){
        if (root == null){
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        root.setDeep(1);
        queue.offer(root);
        while (!queue.isEmpty()){
            TreeNode node = queue.poll();
            if (node.getLeft() == null && node.getRight() == null){
                return node.getDeep();
            }
            if (node.getLeft() != null){
                node.getLeft().setDeep(node.getDeep() + 1);
                queue.offer(node.getLeft());
            }
            if (node.getRight() != null){
                node.getRight().setDeep(node.getDeep() + 1);
                queue.offer(node.getRight());
            }
        }

        return 0;
    }


}
