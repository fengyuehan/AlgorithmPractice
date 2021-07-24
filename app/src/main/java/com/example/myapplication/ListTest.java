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
    /**
     * node的长度为len，当n = 1,相当于fast.next = null，slow在倒数第二个位置上。满足条件
     * 1<n<len 窗口为n+1，此时删除倒数第n个数，直接将倒数第n+1个数指向n-1个数。
     * n = len时，循环结束后，fast == null，而此时slow位于头节点，所以要删除的就是头节点，返回head.next即可
     * n>len 时直接返回原数组
     *
     * @param node
     * @param n
     * @return
     */
    public Node deleteLastNode(Node node,int n){
        if ( node == null || n < 1){
            return null;
        }
        Node fast = node;
        //构建n+1的窗口
        for (int i = 0; i < n;i++){
            if (fast.next != null){
                fast = fast.next;
            }else {
                return node;
            }
        }
        if (fast == null){
            return node.next;
        }
       Node slow = node;
        while (fast.next != null){
            slow = slow.next;
            fast = fast.next;
        }
        //循环结束后相当于找到倒数第n+1个数，然后将倒数第n个数删除，后面的整体前移
        slow.next = slow.next.next;
        return slow;
    }

    /**
     * 旋转单链表
     */
    /**
     * 思路：
     *  其实跟上面的找到倒数第n个节点一样，然后将该节点与前面的节点断开，并将尾节点指向头节点
     *  这里有个点，如果n 大于链表长度时，又会循环一遍，所以需要取余操作
     *
     * @param head
     * @param n
     * @return
     */
    public Node rotateList(Node head,int n){
        if (head == null || n <= 0){
            return head;
        }
        //求链表的长度
        int k = getLength(head);
        //k = 0表示链表为空，直接返回
        //n % k == 0则表示循环一遍，又回到原位，所以继续返回head
        if (k == 0 || n % k == 0){
            return head;
        }

        n = n % k;
        Node slow = head;
        Node fast = head;
        //让快指针先走n，相当于构建一个n的窗口
        while (fast.next != null && n-- > 0){
            fast = fast.next;
        }
        while (fast.next != null){
            slow = slow.next;
            fast = fast.next;
        }

        fast.next = head;
        head.next = slow.next;
        slow.next = null;
        return fast.next;
    }

    /**
     * 翻转链表
     */
    public Node reverseNode(Node head){
        //保存前一个节点
        Node pre = null;
        //用来保存下一个节点，因为我们先把第一个指向null，那么下次就找不到
        //下一个元素，所以我们在将头节点置为null之前，先把下一个节点用这个变量保存下来
        Node next;
        Node curr = head;

        while (curr != null){
            //先将头节点的下一个节点保存
            next = curr.next;
            //将head的下一个节点指向前一个节点，本来时指向下一个节点的，现在进行改变
            curr.next = pre;
            //然后将pre移到当前位置
            pre = curr;
            //然后将头节点移到保存的下一个节点
            curr = next;
        }
        return pre;
    }

    /**
     * 采用递归的方式,这种方式需要从后面来遍历
     */
    public Node reverseNode2(Node head){
        if (head == null || head.next == null){
            return head;
        }
        Node newNode = reverseNode2(head);
        head.next.next = head;
        head.next = null;
        return newNode;
    }

    /**
     * 删除有序链表中的重复元素
     */
    public void deleteSame(Node node){
        if (node == null){
            return;
        }
        Node head = node;
        while (head.next != null){
            if (head.value == head.next.value){
                head.next = head.next.next;
            }else {
                head = head.next;
            }
        }
    }

    /**
     * 删除无序的重复元素
     * 我们必须使用一个指针记住当前考察元素 cur 的上一个元素 pre ，并以此遍历考察元素之后的所有节点，如果有重复则将 pre 指针的 next 指针指向当 cur.next; 重复遍历每个节点，直至链表结尾。
     */
    public void deleteNoSortSame(Node head){
        if (head == null){
            return;
        }
        //记录前一个位置
        Node pre;
        //记录后一个位置
        Node next;
        //这个变量时控制外部循环的
        Node cur = head;
        while (cur != null){
            pre = cur;
            next = cur.next;
            while (next != null){
                if (cur.value == next.value){
                    //值相同则删除，直接将指针指向下一个节点即可
                    pre.next = next.next;
                }else {
                    pre = next;
                }
                next = next.next;
            }
            cur = cur.next;
        }
    }
}
