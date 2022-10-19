package com.example.myapplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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
     * 求二叉树的节点个数
     * 左节点个数 + 右节点个数 + 1
     */
    public int getNodeNum(TreeNode node){
        if (node == null){
            return 0;
        }
        return getNodeNum(node.getLeft()) + getNodeNum(node.getRight()) + 1;
    }

    /**
     * 二叉树的最大层数
     *  max(左子树深度，右子树深度) + 1
     */

    public int maxDepth(TreeNode node){
        if (node == null){
            return 0;
        }
        return Math.max(maxDepth(node.getLeft()),maxDepth(node.getRight())) + 1;
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

    /**
     * 前序遍历(递归)
     * 根左右
     */
    public void preTraversal(TreeNode node){
        List<Integer> list = new ArrayList<>();
        robot(node,list);
    }

    private void robot(TreeNode node, List<Integer> list) {
        if (node == null){
            return;
        }
        if (list == null){
            list = new ArrayList<>();
        }
        list.add(node.getValue());
        robot(node.getLeft(),list);
        robot(node.getRight(),list);
    }

    /**
     * 迭代
     * 栈的先进后出性质
     */
    public List<Integer> preTraversal2(TreeNode node){
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        stack.push(node);
        while (!stack.isEmpty()){
            //pop弹出的是第size-1个位置上的元素，也就是最上面的元素，因为最下面是第0个位置上的元素
            TreeNode tmp = stack.pop();
            if (tmp != null){
                list.add(tmp.getValue());
                //先将右子节点放入，然后再放入左子节点，那样后进先出，左子节点将先取出
                if (tmp.getRight() != null){
                    stack.push(tmp.getRight());
                }
                if (tmp.getLeft() != null){
                    stack.push(tmp.getLeft());
                }
            }
        }
        return list;
    }

    /**
     * 中序遍历
     * 左根右
     */

    public List<Integer> inorderTraversal(TreeNode node){
        List<Integer> list = new ArrayList<>();
        robot1(node,list);
        return list;
    }

    private void robot1(TreeNode node, List<Integer> list) {
        robot1(node.getLeft(),list);
        list.add(node.getValue());
        robot1(node.getRight(),list);
    }

    /**
     * 非递归中序遍历
     */
    public List<Integer> inorderTraversal2(TreeNode node){
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        while (node != null || stack != null){
            //先将左子树压栈
            while (node != null){
                stack.push(node);
                node = node.getLeft();
            }
            node = stack.pop();
            list.add(node.getValue());
            node = node.getRight();
        }
        return list;
    }

    /**
     * 后序遍历
     */
    public List<Integer> postOrderTraversal(TreeNode node){
        List<Integer> list = new ArrayList<>();
        if (node == null){
            return list;
        }
        List<Integer> left = postOrderTraversal(node.getLeft());
        List<Integer> right = postOrderTraversal(node.getRight());
        list.addAll(left);
        list.addAll(right);
        list.add(node.getValue());
        return list;
    }

    /**
     * 非递归
     */
    public List<Integer> postOrderTraversal2(TreeNode node){
        List<Integer> list = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()){
            TreeNode cur = stack.pop();
            list.add(0,cur.getValue());
            if (node.getLeft() != null){
                stack.push(node.getLeft());
            }
            if (node.getRight() != null){
                stack.push(node.getRight());
            }
        }
        return list;
    }


    /**
     * 广度优先
     */
    public List<Integer> printFromTopToBottom(TreeNode node){
        List<Integer> list = new ArrayList<>();
        if (node == null){
            return list;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            TreeNode treeNode = queue.poll();
            if (treeNode.getLeft() != null){
                queue.offer(treeNode.getLeft());
            }
            if (treeNode.getRight() != null){
                queue.offer(treeNode.getRight());
            }
            list.add(treeNode.getValue());
        }
        return list;
    }

    /**
     * 深度优先
     */
    public List<Integer> deepFirst(TreeNode node){
        List<Integer> list = new ArrayList<>();
        if (node == null){
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()){
            TreeNode treeNode = stack.pop();
            if (treeNode.getRight() != null){
                stack.push(treeNode.getRight());
            }
            if (treeNode.getLeft() != null){
                stack.push(treeNode.getLeft());
            }
            list.add(treeNode.getValue());
        }
        return list;
    }

    /**
     * 递归
     */
    public List<Integer> deepFirst1(TreeNode node){
        List<Integer> list = new ArrayList<>();
        if (node == null){
            return list;
        }
        list.add(node.getValue());
        List<Integer> list2 = deepFirst1(node.getLeft());
        List<Integer> list1 = deepFirst1(node.getRight());
        list.addAll(list2);
        list.addAll(list1);
        return list;
    }

    /**
     * 自下而上分层遍历
     */
    public List<Integer> levelOrderBottom(TreeNode node){
        List<Integer> list = new ArrayList<>();
        if (node == null){
            return list;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            TreeNode treeNode = queue.poll();
            if (treeNode.getLeft() != null){
                queue.offer(node.getLeft());
            }
            if (treeNode.getRight() != null){
                queue.offer(treeNode.getRight());
            }
            list.add(0,treeNode.getValue());
        }
        return list;
    }

    /**
     * 之字型打印
     */
    public ArrayList<ArrayList<Integer>> zhiPrint(TreeNode node){
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (node != null){
            Stack<TreeNode> s1 = new Stack<>();
            Stack<TreeNode> s2 = new Stack<>();
            s1.push(node);
            while (!s1.isEmpty() || !s2.isEmpty()){
                ArrayList<Integer> odd = new ArrayList<>();
                while (!s1.isEmpty()){
                    TreeNode node1 = s1.pop();
                    if (node1.getLeft() != null){
                        s2.push(node1.getLeft());
                    }
                    if (node1.getRight() != null){
                        s2.push(node1.getRight());
                    }
                    odd.add(node1.getValue());
                }
                res.add(odd);

                ArrayList<Integer> even = new ArrayList<>();
                while (!s2.isEmpty()){
                    TreeNode treeNode = s2.pop();
                    if (treeNode.getLeft() != null){
                        s1.push(treeNode.getLeft());
                    }
                    if (treeNode.getRight() != null){
                        s1.push(treeNode.getRight());
                    }
                    even.add(treeNode.getValue());
                }
                res.add(even);
            }
        }
        return res;
    }

    /**
     * 二叉树的最小深度
     * 对于一个节点，如果左子树或者右子树为空，那么最小深度为 left + right + 1
     * 如果左子树和右子树都不为空，那么最小深度为 Math.min(left,right) + 1
     */

    public int minDeep3(TreeNode node){
        if (node == null){
            return 0;
        }
        int left = minDeep3(node.getLeft());
        int right = minDeep3(node.getRight());
        return (left == 0 || right == 0) ? left + right + 1 : Math.max(left,right) + 1;
    }

    /**
     * 平衡树
     * 平衡树左右子树高度差都小于等于 1
     */
    public boolean result = true;
    public boolean isBalance(TreeNode node){
        maxDepth1(node);
        return result;
    }

    public int maxDepth1(TreeNode root) {
        if (root == null) return 0;
        int l = maxDepth(root.getLeft());
        int r = maxDepth(root.getRight());
        if (Math.abs(l - r) > 1) {
            result = false;
        }else {
            result = true;
        }
        return 1 + Math.max(l, r);
    }

    /**
     * 两节点的最长路径
     * 因为最长直径不一定过根节点，所以需要分别以每一个节点为中心计算最长路径。
     * 用一个全局变量max来维护最长路径（左子树的深度+右子树的深度）。
     * 为了计算每个子树的深度，使用深度优先遍历计算树中每一个节点的深度（max（左子树深度，右子树深度））
     */

    private int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return max;
    }

    private int depth(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = depth(root.getLeft());
        int rightDepth = depth(root.getRight());
        max = Math.max(max, leftDepth + rightDepth);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 翻转树(镜像)
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null){
            return null;
        }
        //TreeNode left = root.getLeft();
        root.setLeft(invertTree(root.getRight()));
        root.setRight(invertTree(root.getLeft()));
        return root;
    }

    /**
     * 归并两棵树
     *
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null){
            return null;
        }
        if (t1 == null){
            return t2;
        }
        if (t2 == null){
            return t1;
        }
        TreeNode root = new TreeNode(t1.getValue() + t2.getValue());
        root.setLeft(mergeTrees(t1.getLeft(),t2.getLeft()));
        root.setRight(mergeTrees(t1.getRight(),t2.getRight()));
        return root;
    }

    /**
     * 判断路径和是否等于一个数
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null){
            return false;
        }
        if (root.getLeft() == null && root.getRight() == null && root.getValue() == sum){
            return true;
        }
        return hasPathSum(root.getLeft(),sum - root.getValue()) || hasPathSum(root.getRight(),sum - root.getValue());
    }

    /**
     * 树的对称
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isSymmetric(root.getLeft(), root.getRight());
    }

    private boolean isSymmetric(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        if (t1.getValue() != t2.getValue()) return false;
        return isSymmetric(t1.getLeft(), t2.getRight()) && isSymmetric(t1.getRight(), t2.getLeft());
    }

    /**
     * 子树
     */
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null){
            return false;
        }
        return isSubtreeWithRoot(s, t) || isSubtree(s.getLeft(), t) || isSubtree(s.getRight(), t);
    }

    private boolean isSubtreeWithRoot(TreeNode s, TreeNode t) {
        if (t == null && s == null) return true;
        if (t == null || s == null) return false;
        if (t.getValue() != s.getValue()) return false;
        return isSubtreeWithRoot(s.getLeft(), t.getLeft()) && isSubtreeWithRoot(s.getRight(), t.getRight());
    }
}
