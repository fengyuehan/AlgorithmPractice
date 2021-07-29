package com.example.myapplication;

import android.graphics.Bitmap;

public class RBTree<K extends Comparable<K>,V> {
    private RBNode mRoot;    // 根结点

    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    /**
     *  红黑树的特点：
     *  1、每个节点是红色或者是黑色
     *  2、根节点是黑色
     *  3、每个叶子节点是黑色
     *  4、如果一个节点是红色，则它的叶子节点是黑字（即不能有连续的两个红色）
     *  5、从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑色节点，因此需要将插入的节点着色为红色
     */

    public class RBNode{
        boolean color;
        K key;
        V value;
        RBNode left;
        RBNode right;
        RBNode parent;

        public RBNode(K key,boolean color,RBNode left, RBNode right, RBNode parent) {
            this.color = color;
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public RBNode(K key,V value,RBNode parent){
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public K getKey() {
            return key;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }
    }

    private RBNode parentOf(RBNode node) {
        return node!=null ? node.parent : null;
    }

    private boolean isRed(RBNode node) {
        return ((node!=null)&&(node.color==RED)) ? true : false;
    }

    private boolean isBlack(RBNode node) {
        return !isRed(node);
    }

    private boolean colorOf(RBNode node){
        return node == null ? BLACK:node.color;
    }

    private RBNode leftOf(RBNode node){
        return node != null ? node.left:null;
    }

    private RBNode rightOf(RBNode node){
        return node != null?node.right:null;
    }

    private void setColor(RBNode node,boolean color){
        if (node != null){
            node.setColor(color);
        }
    }

    /**
     * 前序遍历 根 -- 左 -- 右
     */
    private void preOrder(RBNode node){
        if (node == null){
            return;
        }
        System.out.println(node.key);
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 中序遍历 左 -- 跟 -- 右
     */
    private void inOrder(RBNode node){
        if (node == null){
            return;
        }
        inOrder(node.left);
        System.out.println(node.key);
        inOrder(node.right);
    }

    /**
     * 后序遍历  左 -- 右 -- 根
     */
    private void postOrder(RBNode node){
        if (node == null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.key);
    }

    /**
     * 左旋
     *     x              y
     *    / \            / \
     *   xl  y   --->   x   yr    发生改变的是：  yl变成x的右孩子
     *      / \        / \
     *     yl  yr     xl  yl
     */
    private void leftRotate(RBNode x){
        //先拿到x的右孩子
        RBNode y = x.right;
        x.right = y.left;
        //如果yl不为null则需要将他的父亲设置为x
        if (y.left != null){
            y.left.parent = x;
        }
        //将x的父亲的父亲设置成y的父亲
        y.parent = x.parent;
        if (x.parent == null){
            //x没有父亲，将将y设置于成根节点
            mRoot = y;
        }else {
            if (x.parent.left == x){
                //表示刚开始x是位于根节点的左边，那旋转后y也放到根节点的左边
                x.parent.left = y;
            }else {
                x.parent.right = y;
            }
        }
        //将x设置成y的左节点
        y.left = x;
        //x的父节点设置成y
        x.parent = y;
    }

    /**
     * 右旋
     *         y                     x
     *        / \                   / \
     *       x   yr    --->        xl  y    改变的为将xr -- > x 变成 xr --> y
     *      / \                       / \
     *     xl  xr                    xr  yr
     */
    private void rightRotate(RBNode y){
        //先拿到y的左节点
        RBNode x = y.left;
        //将xr -- > x 变成 xr --> y
        y.left = x.right;
        if (x.right != null){
            x.right.parent = y;
        }
        //将y的父亲变成x的父亲
        x.parent = y.parent;
        if (y.parent == null){
            mRoot = x;
        }else {
            if (y.parent.left == y){
                y.parent.left = x;
            }else {
                y.parent.parent = x;
            }
        }
        //将y设置成x的右节点
        x.right = y;
        //将y的父节点设置成x
        y.parent = x;
    }

    /**
     * 查找最小节点
     */
    private RBNode minNode(RBNode node){
        if (node == null){
            return null;
        }
        while (node.left != null){
            node = node.left;
        }
        return node;
    }

    /**
     * 查找最大值
     */
    private RBNode maxNode(RBNode node){
        if (node == null){
            return null;
        }
        while (node.right != null){
            node = node.right;
        }
        return node;
    }

    /**
     * 后驱节点：就是当前节点右边的最小节点
     */
    private RBNode successor(RBNode node){
        if (node.right != null){
            return minNode(node.right);
        }else {
            /**
             * 当node.right为空的时候，有下面两种情况                                          o
             *                                                                             /
             *        p                                                                   p
             *      /                                                                      \
             *     x  这种情况就是x为父节点的左节点，那他的后继节点就是他的父节点               x   x为父节点的右节点，
             *    /                                                                        /
             *   xl                                                                      xl
             *
             */
            //如果位于父节点的左节点，直接返回他的父节点即可
            RBNode p = node.parent;
            //如果是位于父节点的右侧，值返回他的父节点的父节点的最小值
            while (p != null && p.right == node){
                node = p;
                p = p.parent;
            }
            return p;
        }

    }

    /**
     * 前驱节点
     */
    private RBNode predcessor(RBNode node){
        if (node.left != null){
            return maxNode(node.left);
        }else {
            //当当前节点没有左节点的时候，也有两种情况
            /**                                    o
             *                                      \
             *        p                              p
             *         \                            /
             *          x                          x
             *           \                          \
             *            xr                         xr
             *        第一种返回的是 p点，  第二种返回的是o点
             */
            RBNode p = node.parent;
            while (p != null && p.left == node){
                node = p;
                p = p.parent;
            }
            return p;
        }
    }

    /**
     * 插入：有一个整体原则是，新插入的节点为红色
     *     情景1：红黑树为空树，直接把插入的节点设置为根节点，并且设置成黑色
     *     情景2：插入节点的key已经存在，更新当前节点的值为插入节点的值
     *     情景3：插入节点的父节点为黑节点，直接插入
     *     情景4：插入节点的父节点为红色节点,则需要进行着色处理，因为我们插入的为红色，而且不能连续的两个红色
     *          4.1 插入节点的父节点是插入节点爷爷的左子节点
     *               4.1.1 插入节点的父节点没有叔叔节点
     *                      4.1.1.1 插入节点在父节点的左侧
     *                           3                                                                       2
     *                          /                                                                      /  \
     *                         2    1表示待插入的节点，2节点为红色  此时需要一次对爷爷节点进行右旋，      1     3  然后把父节点设置成黑色，爷爷节点变成红色
     *                        /
     *                       1
     *                      4.1.1.2 插入节点在父节点的右侧
     *                       3                                                                3                                      2.5
     *                      /                                                                /                                      /  \
     *                     2      2.5表示待插入的点，2为红色，此时需要先对父节点进行左旋变成    2.5    然后在对爷爷节点进行右旋          2    3     父节点设置成黑色，爷爷节点变成红色（这里有点不好理解，需要先理解一下左旋和右旋，他们的parent其实是发生了改变的）
     *                      \                                                               /                                                  因为旋转之后，插入的点变成了2,2.5变成了2的父节点，2一开始是红色的，2.5也是红色的，所以需要把2.5变成黑色
     *                      2.5                                                            2                                                   也就是所谓的父节点变成黑色
     *               4.1.2 插入节点的父节点有叔叔节点
     *                     4.1.2.1 插入节点在父节点的左侧
     *                           3
     *                         /  \
     *                        2    4       这种需要对爷爷节点变成红色，父节点和叔叔节点变成黑色  但是这种可能爷爷节点上面还有父节点和叔叔节点，并且叔叔节点为红色，
     *                       /             这个时候我们将爷爷节点变成了红色，与不能有连续两个红色节点违背，所以我们需要递归处理
     *                      1
     *                     4.1.2.2 插入节点在父节点的右侧
     *                            3
     *                          /  \
     *                         2    4  这种需要对爷爷节点变成红色，父节点和叔叔节点变成黑色
     *                          \
     *                          2.5
     *                     上面两种的操作是一样的其实。
     *          4.2 插入节点的父节点是插入节点爷爷的右子节点
     *               4.2.1 插入节点的父节点没有叔叔节点
     *                    4.2.1.1  插入节点在父节点的左侧
     *                         3
     *                          \
     *                          4   先对父节点进行右旋，然后再对爷爷节点左旋，父节点设置成黑色，爷爷节点变成红色
     *                         /
     *                        3.5
     *                    4.2.1.2  插入节点在父节点的右侧
     *                         3
     *                          \
     *                           4    对爷爷节点进行左旋，然后把父节点设置成黑色，爷爷节点变成红色
     *                            \
     *                             5
     *               4.2.2 插入节点的父节点有叔叔节点
     *                   4.2.2.1  插入节点在父节点的左侧
     *                           3
     *                         /  \
     *                        2    4  这种需要对爷爷节点变成红色，父节点和叔叔节点变成黑色
     *                            /
     *                           3.5
     *                   4.2.2.2  插入节点在父节点的右侧
     *                           3
     *                         /   \
     *                        2     4  这种需要对爷爷节点变成红色，父节点和叔叔节点变成黑色
     *                               \
     *                                5
     */
    private void put(K key,V value){
        RBNode t = mRoot;
        if (t == null){
            //对应情景1
            mRoot = new RBNode(key,value,null);
            return;
        }
        //查找插入的位置
        int cmp;
        RBNode parent;
        if (key == null){
            throw new NullPointerException();
        }
        do {
            parent = t;
            cmp = key.compareTo((K) t.key);
            if (cmp < 0){
                t = t.left;
            }else if (cmp > 0){
                t = t.right;
            }else {
                //情景2
                t.setValue(value == null ? (V) key :value);
                return;
            }
        }while (t != null);
        //经过上面的循环，则parent为找到插入的位置
        RBNode e = new RBNode(key,value,parent);
        if (cmp > 0){
            //大于0.在父类的右节点
            parent.right = e;
        }else {
            parent.left = e;
        }
        //做平衡处理  旋转和变色操作
        fixAfterPut(e);
    }

    /**
     * 做平衡处理  旋转和变色操作
     * @param e
     */
    private void fixAfterPut(RBNode e) {
        //新插入的为红色
        setColor(e,RED);
        //当插入的点不是根节点，然后其父节点为红色才进行平衡操作，因为我们插入的为红色，而且不能连续的两个红色
        while (e != null && e != mRoot && parentOf(e).color == RED){
            if (parentOf(e) == parentOf(parentOf(e)).left){
                //插入节点在爷爷节点的左侧
                //获取插入节点的叔叔节点
                RBNode y = rightOf(parentOf(parentOf(e)));
                if (y.color == RED){
                    /**
                     * 这里为什么一个 y.color == RED 条件就能判断叔叔节点存在？根据特性红黑树的特性5可以知道，如果从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑色节点，
                     *              3 （黑色）
                     *            /        \
                     *         1 （红色）   2
                     * 假设叔叔节点为黑色，即2为黑色，则3到2的黑色节点有两个，而3到1的黑色节点只有一个，不相等，所以违背了上面的特性5，所以要想叔叔节点存在，叔叔节点必须是红色
                     */
                    //说明叔叔节点存在
                    //对爷爷节点变成红色，父节点和叔叔节点变成黑色
                    //对应4.1.2.1 和 4.1.2.2场景
                    setColor(parentOf(e),BLACK);
                    setColor(y,BLACK);
                    setColor(parentOf(parentOf(e)),RED);
                    //把爷爷节点赋值到e，相当于把下面的一块看成一个整体进行递归
                    e = parentOf(parentOf(e));
                }else {
                    if (e == rightOf(parentOf(e))){
                        //对应4.1.1.2场景
                        //此时需要先对父节点进行左旋,然后在对爷爷节点进行右旋
                        leftRotate(parentOf(e));
                    }
                    //对应4.1.1.1场景
                    setColor(parentOf(e),BLACK);
                    setColor(parentOf(parentOf(e)),RED);
                    rightRotate(parentOf(parentOf(e)));
                }
            }else {
                //插入节点在爷爷节点的右侧
                //获取插入节点的叔叔节点
                RBNode y = leftOf(parentOf(parentOf(e)));
                if (y.color == RED){
                    //对应4.2.2.1 和 4.2.2.2场景
                    //说明叔叔节点存在
                    //对爷爷节点变成红色，父节点和叔叔节点变成黑色
                    setColor(parentOf(e),BLACK);
                    setColor(y,BLACK);
                    setColor(parentOf(parentOf(e)),RED);
                    //把爷爷节点赋值到e，相当于把下面的一块看成一个整体进行递归
                    e = parentOf(parentOf(e));
                }else {
                    if (e == leftOf(parentOf(e))){
                        //对应4.2.1.2场景
                        //此时需要先对父节点进行左旋,然后在对爷爷节点进行右旋
                        rightRotate(parentOf(e));
                    }
                    //对应4.2.1.1场景
                    setColor(parentOf(e),BLACK);
                    setColor(parentOf(parentOf(e)),RED);
                    leftRotate(parentOf(parentOf(e)));
                }
            }
        }
        //根节点都为黑色
        setColor(mRoot, BLACK);
    }

    /**
     * 删除
     * @param key
     * @return
     */
    private V remove(K key){
        RBNode node = getNode(key);
        if (node == null){
            return null;
        }
        V oldValue = node.value;
        //删除节点
        deleteNode(node);
        return oldValue;
    }

    /**
     * 删除节点
     * @param node
     */
    /**
     *  有三种情况
     *    1、删除根节点，直接删除
     *    2、删除叶子节点，直接删除
     *    3、删除有一个子节点，用子节点来代替
     *    4、删除的节点有两个子节点，此时用前驱或者后继节点替代，这种情况可以转换成情况1和情况2
     *
     */
    private void deleteNode(RBNode node) {
        //属于情况4
        if (leftOf(node) != null && rightOf(node) != null){
            //找到要删除节点的后继节点
            RBNode successor = successor(node);
            //将后继节点的值覆盖要删除节点的信息
            node.key = successor.key;
            node.value = successor.value;
            node = successor;
        }
        //情况3
        RBNode replacement = node.left != null ? node.left:node.right;
        if (replacement != null){
            //表明是有一个子节点的
            /**
             *                 3
             *                /
             *               2    加入现在要删除2，那我们需要把1的父节点设置成3
             *              /
             *             1
             */
            replacement.parent = node.parent;
            if (node.parent == null){
                //说明删除掉的是根节点，此时直接把替代的点变成根节点
                mRoot = replacement;
            }else if (node == leftOf(parentOf(node))){
                /**
                 * 删除的点是父节点的左侧子节点
                 */
                parentOf(node).left = replacement;
            }else {
                parentOf(node).right = replacement;
            }
            //然后把要删除的点置空
            node.left = node.right = node.parent = null;
            if (node.color == BLACK){
                fixAfterRemove(replacement);
            }
        }else if(node.parent == null){
            //情况1
            mRoot = null;
        }else {
            //删除叶子节点 情况2
            //需要先调整
            if (node.color == BLACK){
                fixAfterRemove(node);
            }
            //在删除
            if (node.parent != null){
                if (node == leftOf(parentOf(node))){
                    parentOf(node).left = null;
                }else {
                    parentOf(node).right = null;
                }
                node = null;
            }
        }

    }

    /**
     * 红黑树的删除
     *    1、待删除的节点是红色节点（D），直接删除
     *          P                P
     *         /                  \
     *        D(RED)              D(RED)
     *    2、待删除的节点是黑色节点（D）
     *       2.1  D的孩子节点为红色,将DL代替D，并且把DL变成黑色
     *            P                   P                                          P                  P
     *           /                     \                                        /                    \
     *          D (BLACK)               D(BLACK)                  --》         DL(BLACK)             DL(BLACK)
     *         /                         \
     *        DL(RED)                    DR(RED)
     *       2.2 D的没有孩子节点
     *           2.2.1 兄弟节点为红色
     *             （1） D为左孩子                                                  (2) D为右孩子
     *                  P                                                                 P
     *                /   \                                                             /   \
     *              D     S(RED)   P左旋，S变成BLACK P变成RED                          S(RED) D          P右旋 S变成BLACK P变成RED
     *                   /  \                                                        /  \
     *                 SL   SR                                                      SL   SR
     *           2.2.2 兄弟节点为黑色
     *               2.2.2.1 兄弟节点的没有子节点
     *                   2.2.2.1.1 父节点为红色
     *                       P (RED)
     *                     /   \
     *                    D     S      直接删除，并将P变成BLACK  S变成RED
     *
     *                   2.2.2.1.2 父节点为黑色
     *                        P (BLACK)
     *                      /   \
     *                     D     S       直接删除  将S 变成RED
     *
     *               2.2.2.2 离D较远的点，且为红色,
     *                         P                                                                              P
     *                       /   \        D -> P -> S -> SR                                                 /  \     SL -> S -> P -> D
     *                      D     S      P左旋                                                              S    D   P右旋
     *                             \                                                                       /
     *                             SR(RED)                                                               SL(RED)
     *
     *
     *               2.2.2.3 离D较近的点，且为红色，
     *
     *                         P                                                                                      P
     *                       /   \                                                                                  /   \
     *                      D     S           S点右旋                                                              S     D   S点左旋
     *                           /                                                                                 \
     *                          SL(RED)                                                                            SR(RED)
     */

    /**
     * 我们可以根据待删除节点分为3种状态，分别为：无节点，一个子节点，两个子节点 和2种颜色，分别为：红和黑，于是就有下面的组合
     * 下面所有的例子D表示待删除的节点
     * 1、待删除的节点是红色  且 无子节点
     *       这种情况有这两种情况：
     *              P            P
     *             /              \
     *            D(RED)         D(RED)
     *      这种组合直接删除就好，不会影响原来的平衡
     *
     * 2、待删除的节点是红色  且 有一个子节点
     *      这种不满足性质5，所以不需要考虑
     *
     * 3、待删除的节点是黑色  且 无节点
     *      这种组合需要考虑兄弟节点的颜色以及兄弟节点的子节点个数。
     *      3.1  兄弟节点为红色，根据性质4可知，他的父亲节点肯定是黑色，且肯定有两个子节点都为黑色，才能平衡
     *           分为两种情况：
     *          (1)            P (BLACK)                           P (BLACK)                              B(RED)
     *                        /      \           删除D之后           /            p右旋                    /   \
     *                      B(RED)   D(BLACK)      ----->       B(RED)            ------》          BL(BLACK)  P (BLACK)   然后将B变成黑色，BR变成红色，这样就平衡了
     *                      /   \                               /   \                                         /
     *                BL(BLACK) BR(BLACK)                  BL(BLACK) BR(BLACK)                            BR(BLACK)
     *
     *
     *        (2)     P (BLACK)                                    P (BLACK)                                     B(RED)
     *                /      \                  删除D之后                 \                   p左旋              /     \
     *             D(BLACK)  B(RED)              ----->                  B(RED)              ------》      P (BLACK)  BR(BLACK)   然后将B变成黑色，BL变成红色，这样就平衡了
     *                       /   \                                       /   \                                 \
     *                 BL(BLACK) BR(BLACK)                         BL(BLACK) BR(BLACK)                       BL(BLACK)
     *
     *       3.2  兄弟节点为黑色  且没有子节点
     *                  P （可以是红色也可以为黑色）
     *                /  \
     *               D    B
     *           这种直接删除，并且将兄弟节点变成红色即可
     *
     *       3.3 兄弟节点为黑色   且只有一个子节点
     *       共有四种情况：根据性质5可以得到子节点全部为红色
     *       （1）             P （可以是红色也可以为黑色）
     *                      /    \                                          B右旋   BL变成P的颜色 P变成黑色  然后P点左旋
     *                    D      B
     *                          /
     *                         BL(RED)
     *        (2)           P
     *                   /    \
     *                  D      B                                       B变成P的颜色  P 变成黑色  P点左旋
     *                          \
     *                           BR
     *        (3)           P
     *                    /   \
     *                   B     D                                      B变成P的颜色  P 变成黑色  P点右旋
     *                  /
     *                 BL
     *        (4)           P
     *                    /   \
     *                   B     D                                     B左旋   BL变成P的颜色 P变成黑色  然后P点右旋
     *                    \
     *                    BR
     *
     *         3.4 兄弟节点为黑色   且有两个子节点
     *           有两种情况(根据性质5可知，子节点只能是红色)
     *        (1)    P
     *             /   \
     *            D     B                  B点左旋  B变成P的颜色，P变黑色 BR变黑
     *                /  \
     *              BL   BR
     *        (2)        P
     *                 /   \
     *                B     D            B点右旋  B变成P的颜色，P变黑色 BL变黑
     *              /   \
     *             BL   BR
     *
     * 4、待删除的节点是黑色  且 有一个节点
     *    这种情况有这两种情况：根据性质5，字节点只能是红色
     *        D (BLACK)    D (BLACK)
     *       /                \
     *      DL(RED)          DR(RED)
     *      这种组合将D点删除之后，DL或者DR变成黑色操作
     *
     * 5、待删除的节点是红色  且 有两个子节点
     * 6、待删除的节点是黑色  且 有两个子节点
     *   这两种可以转化成前面的1,3,4去操作，相当于去删除一个后继节点。
     *
     */

    private void fixAfterRemove(RBNode replacement) {
        //调整平衡
        while (replacement != mRoot && colorOf(replacement) ==BLACK){
            if (replacement == leftOf(parentOf(replacement))){
                //位于父节点的左侧
                //拿兄弟节点
                /**
                 * 找兄弟节点，如果找到的节点为红色，则不是真正的红色
                 *                 7                                                                                                                 9
                 *              /   \                                                                                                              /  \
                 *             6     9 (red)  此时找到的9是红色，而起真正的兄弟节点是8  所以我们                                                    7(red)10  经过上一步的操作后，就可以得到真正的兄弟节点
                 *                 /  \       需要先把找到的这个假的兄弟节点（9）变成黑色，将6的父节点（7）变成红色，然后再根据父节点左旋一次               /  \
                 *                8    10                                                                                                           6    8
                 */
                RBNode rNode = rightOf(parentOf(replacement));
                if (colorOf(rNode) == RED){
                    setColor(rNode,BLACK);
                    setColor(parentOf(replacement),RED);
                    leftRotate(parentOf(replacement));
                    rNode = rightOf(parentOf(replacement));
                }
                if (colorOf(leftOf(rNode)) == BLACK && colorOf(rightOf(rNode)) == BLACK){
                    //2节点没得借
                    //父亲节点是红色，兄弟节点是黑色， 删了之后需要把兄弟节点变成红色，父节点变成黑色
                    //父亲节点是黑色，兄节点也是黑色 ，删了之后把兄弟节点变红色，
                    setColor(rNode,RED);
                    replacement = parentOf(replacement);
                }else {
                    //3/4节点，可以借 但是有分两种情况，没有右节点，需要根据兄弟节点右旋，然后兄弟节点变成红色，左节点变成黑色
                    if (colorOf(rightOf(rNode)) == BLACK){
                        //不存在右节点，只有左节点
                        setColor(rNode,RED);
                        setColor(leftOf(rNode),BLACK);
                        rightRotate(rNode);
                        rNode = rightOf(parentOf(replacement));
                    }
                    setColor(rNode,colorOf(parentOf(replacement)));
                    setColor(parentOf(replacement),BLACK);
                    setColor(rightOf(rNode),BLACK);
                    leftRotate(parentOf(replacement));
                    replacement = mRoot;
                }

            }else {
                //位于父节点的右侧
                RBNode rNode = leftOf(parentOf(replacement));
                if (colorOf(rNode) == RED){
                    setColor(rNode,BLACK);
                    setColor(parentOf(replacement),RED);
                    rightRotate(parentOf(replacement));
                    rNode = leftOf(parentOf(replacement));
                }
                if (colorOf(rightOf(rNode)) == BLACK && colorOf(leftOf(rNode)) == BLACK){
                    //2节点没得借
                    //父亲节点是红色，兄弟节点是黑色， 删了之后需要把兄弟节点变成红色，父节点变成黑色
                    //父亲节点是黑色，兄节点也是黑色 ，删了之后把兄弟节点变红色，
                    setColor(rNode,RED);
                    replacement = parentOf(replacement);
                }else {
                    //3/4节点，可以借 但是有分两种情况，没有右节点，需要根据兄弟节点右旋，然后兄弟节点变成红色，左节点变成黑色
                    if (colorOf(leftOf(rNode)) == BLACK){
                        //不存在右节点，只有左节点
                        setColor(rNode,RED);
                        setColor(rightOf(rNode),BLACK);
                        leftRotate(rNode);
                        rNode = leftOf(parentOf(replacement));
                    }
                    setColor(rNode,colorOf(parentOf(replacement)));
                    setColor(parentOf(replacement),BLACK);
                    setColor(leftOf(rNode),BLACK);
                    rightRotate(parentOf(replacement));
                    replacement = mRoot;
                }
            }
        }


        //替代的节点是红色节点，直接设置成黑色即可
        /**
         *                 3
         *               /  \
         *              1    5 (red)   因为5为红色，删除5，并不会影响平衡，只需要把4替代5，并且4的颜色设置到5上面，5是红色，所以4一定是黑色，所以我们将替代的点设置成黑色即可
         *                  /
         *                 4
         */
        setColor(replacement,BLACK);

    }

    /**
     * 根据key找到相关的节点
     * @param key
     * @return
     */
    private RBNode getNode(K key) {
        RBNode node = mRoot;
        while (node != null){
            int cmp = key.compareTo(node.key);
            if (cmp < 0){
                node = node.left;
            }else if (cmp > 0){
                node = node.right;
            }else {
                return node;
            }
        }
        return null;
    }
}
