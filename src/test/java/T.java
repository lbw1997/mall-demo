import java.util.ArrayList;

/**
 * description: T <br>
 * date: 2020/9/14 9:00 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
public class T {
    public static TreeNode convertBST(TreeNode root) {
        TreeNode head = root;
        ArrayList<Integer> nums = new ArrayList<>();
        getNums(root,nums);
        setNums(root,nums,0);
        return head;
    }

    public static void getNums(TreeNode root,ArrayList nums) {
        if(root!=null) {
            if(root.left!=null) {
                getNums(root.left,nums);
            }
            nums.add(root.val);
            if(root.right!=null) {
                getNums(root.right,nums);
            }
        }
    }
    public static void setNums(TreeNode root,ArrayList<Integer> nums,int i) {
        if(root!=null) {
            if(root.left!=null) {
                setNums(root.left,nums,i);
            }
            nums.remove(0);
            for(int j = 0;j<nums.size();j++) {

                root.val += nums.get(j);
            }
            if(root.right!=null) {
                setNums(root.right,nums,i);
            }
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        TreeNode head = root;
        root.right = new TreeNode(2);
        root.left = new TreeNode(13);
        convertBST(root);

        System.out.println(head.left.val);
        System.out.println(head.right.val);
        System.out.println(head.val);
    }
}
 class TreeNode {
     int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
