package com.test;

import java.util.ArrayList;
/*
public class Tree {
    //[['1','-'],['-','-'],['0','-'],['2','7'],['-','-'],['-','-'],['5','-'],['4','6']]
    ArrayList<ArrayList<Integer>> a=new ArrayList<>();
    ArrayList<Integer> re=new ArrayList<>();
    public Tree(){
        getData();
    }
    public void getData(){
        ArrayList<Integer> temp1=new ArrayList<>(){{
            add(1);
            add(-1);
        }};
        a.add(temp1);

        ArrayList<Integer> temp2=new ArrayList<>(){{
            add(-1);
            add(-1);
        }};
        a.add(temp2);

        ArrayList<Integer> temp3=new ArrayList<>(){{
            add(0);
            add(-1);
        }};
        a.add(temp3);

        ArrayList<Integer> temp4=new ArrayList<>(){{
            add(2);
            add(7);
        }};
        a.add(temp4);

        ArrayList<Integer> temp5=new ArrayList<>(){{
            add(-1);
            add(-1);
        }};
        a.add(temp5);

        ArrayList<Integer> temp6=new ArrayList<>(){{
            add(-1);
            add(-1);
        }};
        a.add(temp6);

        ArrayList<Integer> temp7=new ArrayList<>(){{
            add(5);
            add(-1);
        }};
        a.add(temp7);

        ArrayList<Integer> temp8=new ArrayList<>(){{
            add(4);
            add(6);
        }};
        a.add(temp8);

    }
    public TreeNode getTree(int index){
        TreeNode root=new TreeNode(index);
        int first=a.get(index).get(0);
        int second=a.get(index).get(1);
        if (first!=-1){
            root.left=getTree(first);
        }
        if (second!=-1){
            root.right=getTree(second);
        }
        return root;
    }
    public void traversal(TreeNode n){
        if (n==null){
            return;
        }
        if (n.right==null&&n.left==null){
            re.add(n.val);
        }
        traversal(n.left);
        traversal(n.right);
    }

}
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}*/
