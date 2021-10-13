package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉查找树（BST）：根节点大于等于左子树所有节点，小于等于右子树所有节点。
 *
 * 二叉查找树中序遍历有序。
 * 若左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 * 若右子树不空，则右子树上所有结点的值均大于或等于它的根结点的值；
 * 左、右子树也分别为二叉排序树；
 * 没有键值相等的节点
 *
 * 二分查找的时间复杂度是O(log(n))，最坏情况下的时间复杂度是O(n)（相当于顺序查找）
 *
 */
public class BSTTree {

    /**
     * 保留值再L-R之间的节点
     *
     * 思路：根据根节点大于等于左子树所有节点，小于等于右子树的所有节点的性质
     */
    public TreeNode trimBST(TreeNode root,int L,int R){
        if (root == null){
            return null;
        }
        //临界点，当根节点的值大于最大区间，那说明我们要找的是在左边
        if (root.getValue() > R){
            return trimBST(root.getLeft(),L,R);
        }
        //当跟节点的值小于最左边，说明我们要找的在根节点的右边
        if (root.getValue() < L){
            return trimBST(root.getRight(),L,R);
        }
        root.setLeft(trimBST(root.getLeft(),L,R));
        root.setRight(trimBST(root.getRight(),L,R));
        return root;
    }

    /**
     * 寻找二叉查找树的第 k 个元素
     *
     * 思路：根据二叉查找树的中序排列是有序的这一特性
     */
    int cnt = 0;
    int val;
    public int kthSmallest(TreeNode root,int k){
        inOrder(root,k);
        return val;
    }

    private void inOrder(TreeNode root, int k) {
        if (root == null){
            return;
        }
        inOrder(root.getLeft(),k);
        cnt++;
        if (cnt == k){
            val = root.getValue();
            return;
        }
        inOrder(root.getRight(),k);
    }

    /**
     * 把二叉查找树每个节点的值都加上比它大的节点的值
     */
    int sum = 0;
    public TreeNode convertBST(TreeNode node){
        trfer(node);
        return node;
    }

    private void trfer(TreeNode node) {
        if (node == null){
            return;
        }
        trfer(node.getRight());
        sum += node.getValue();
        node.setValue(sum);
        trfer(node.getLeft());
    }

    /**
     * 二叉查找树的最近公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root,TreeNode p,TreeNode q){
        if (root.getValue() > p.getValue() && root.getValue() > q.getValue()){
            //根据二叉查找树的性质，在左边
            lowestCommonAncestor(root.getLeft(),p,q);
        }
        if (root.getValue() < p.getValue() && root.getValue() < q.getValue()){
            lowestCommonAncestor(root.getRight(),p,q);
        }
        return root;
    }

    /**
     * 二叉树的最近公共祖先
     */
    public TreeNode lowestCommonAncestor1(TreeNode node,TreeNode p,TreeNode q){
        if (node == null || node == p || node == q){
            return node;
        }
        TreeNode left = lowestCommonAncestor1(node.getLeft(),p,q);
        TreeNode right = lowestCommonAncestor1(node.getRight(),p,q);
        if (left == null && right != null){
            return right;
        }else if (right == null && left != null){
            return left;
        }else {
            return node;
        }
    }

    /**
     * 从有序数组中构造二叉查找树
     */
    public TreeNode sortArrayToBST(int[] nums){
        return toBST(nums,0,nums.length -1);
    }

    private TreeNode toBST(int[] nums, int i, int i1) {
        if (i > i1){
            return  null;
        }
        int midIndex = (i + i1) /2;
        TreeNode root = new TreeNode(nums[midIndex]);
        root.setLeft(toBST(nums,i,midIndex -1));
        root.setRight(toBST(nums,midIndex + 1,i1));
        return root;
    }

    /**
     * 根据有序链表构造平衡的二叉查找树
     */
    public TreeNode sortListToBST(Node head){
        if (head == null){
            return null;
        }
        if (head.next == null){
            return new TreeNode(head.value);
        }
        Node premid = preMid(head);
        Node mid = premid.next;
        premid.next = null;
        TreeNode t = new TreeNode(mid.value);
        t.setLeft(sortListToBST(head));
        t.setRight(sortListToBST(mid.next));
        return t;
    }

    private Node preMid(Node head) {
        Node slow = head;
        Node fast = head;
        Node pre = head;
        while (fast != null && fast.next != null){
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return pre;
    }

    /**
     * 在二叉查找树中寻找两个节点，使它们的和为一个给定值
     */
    public boolean findTarget(TreeNode root,int k){
        List<Integer> nums = new ArrayList<>();
        inOrder1(root,nums);
        int i = 0;
        int j = nums.size()-1;
        while (i < j){
            int sum = nums.get(i) + nums.get(j);
            if (sum == k){
                return true;
            }
            if (sum < k){
                i++;
            }else {
                j--;
            }
        }
        return false;
    }

    private void inOrder1(TreeNode root, List<Integer> nums) {
        if (root == null){
            return;
        }
        inOrder1(root.getLeft(),nums);
        nums.add(root.getValue());
        inOrder1(root.getRight(),nums);
    }


    /**
     * 在二叉查找树中查找两个节点之差的最小绝对值
     */
    public int getMinimuDifference(TreeNode root){
        List<Integer> list = new ArrayList<>();
        inOrder1(root,list);
        int i = 0;
        int min = Integer.MAX_VALUE;
        while (i < list.size()){
            int temp = list.get(i + 1) - list.get(i);
            if (temp < min){
                min = temp;
            }
            i++;
        }
        return min;
    }

    /**
     * 直接放在中序遍历中
     */
    TreeNode preNode = null;
    int min = Integer.MAX_VALUE;
    public int getMinimuDifference1(TreeNode root){
        order(root);
        return min;
    }

    private void order(TreeNode root) {
        if (root == null){
            return;
        }
        order(root.getLeft());
        if (preNode != null){
            min = Math.min(min,root.getValue() - preNode.getValue());
        }
        preNode = root;
        order(root.getRight());
    }
}
