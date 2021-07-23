package com.example.myapplication;

/**
 * 主要针对单链表的操作
 */
public class ListTest {

    /**
     * 获取单链表的长度
     */
    public int getLength(Node head){
        if(head == null){
            return 0;
        }

        int len = 0;
        while (head.next != null){
            head = head.next;
            len++;
        }
        return len;
    }


    /**
     * 查询指定索引的节点值
     */

    public int getValueOfIndex(Node head,int index) throws Exception {
        if (index < 0 || index > getLength(head)){
            throw new Exception("角标越界");
        }
        if(head == null){
            throw new Exception("当前链表为空");
        }
        Node findNode = head;
        while (findNode.next != null && index > 0){
            findNode = findNode.next;
            index--;
        }
        return findNode.value;
    }

    /**
     * 获取节点值等于 value 的第一个元素角标
     */
    public int getNodeIndex(Node head,int value){
        int index = -1;
        Node findNode = head;
        while (findNode.next != null){
            index++;
            if (findNode.value == value){
                return index;
            }
            findNode = findNode.next;
        }
        return -1;
    }

    //链表不像数组那样，可以直接拿下标就可得到对应的值，而链表则需要进行遍历，这就是为什么说链表增删快，查询慢。
    /**
     * 在已有的链表头部添加一个节点
     */
    public Node addAtHead(Node h,int value){
        Node newNode = new Node(value);
        newNode.next = h;
        return newNode;
    }

    /**
     * 在已有的链表尾部添加一个节点
     */
    public void  addAtTail(Node head,int value){
        Node newNode = new Node(value);
        Node originNode = head;
        /**
         * 这个遍历表示寻找链表的尾部
         */
        while (originNode.next != null){
            originNode = originNode.next;
        }
        originNode.next = newNode;
    }

    /**
     * 删除头部节点
     */
    private Node deleteHead(Node node) throws Exception {
        if (node == null){
            throw new Exception("当前链表为空");
        }
        return node.next;
    }

    /**
     * 删除为节点
     */

    public void deleteTail(Node node) throws Exception {
        if (node == null){
            throw new Exception("当前链表为空");
        }
        Node findNode = node;
        while (findNode.next != null){
            findNode = findNode.next;
        }
        findNode.next = null;
    }

    /**
     * 快慢指针的应用：一个是查询中间数，另一个则是判断是否有环
     */
    /**
     * 寻找单链表的中间元素
     */
    /**
     * 思路：设置 两个指针 slow、fast 起始都指向单链表的头节点。其中 fast 的移动速度是 slow 的2倍。当 fast 指向末尾节点的时候，slow 正好就在中间了。
     */

    public Node getMid(Node head){
        if (head == null){
            return null;
        }
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 判断一个链表是否是循环链表
     * 用双指针，如果是循环链表，那快的一定会追上慢的
     */
    public boolean isLoopList(Node head){
        if (head == null){
            return false;
        }

        Node slow = head;
        Node fast = head;

        while (slow != null && fast != null && fast.next != null){
            if (fast == slow || fast.next == slow){
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

    /**
     * 求链表的倒数第N个节点，可以理解成一个滑动窗口。如果我们让快指针先走 n-1 步后，然后让慢指针出发。快慢指针每次都只移动一个位置，当快指针移动到链表末尾的时候，慢指针是否就正处于倒数第 N 个节点的位置
     */
    /**
     * 已知一个单链表求倒数第 N 个节点
     */

    public Node getLastIndexNode(Node node,int n){
        if (n < 0 || node == null){
            return null;
        }
        Node slow = node;
        Node fast = node;
        for (int i = 0; i < n; i++){
            if (fast.next != null){
                fast = fast.next;
            }else {
                //表明链表没有那么多元素
                return null;
            }
        }
        while (fast.next != null){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
    /**
     * 删除单链表的倒数第 n 个节点
     * 这个跟上面的唯一区别就是，删除第n个节点，我们得需要知道前一个节点。所以需要构造n+1的窗口
     */
}
