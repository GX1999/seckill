package com.test;

import com.sun.source.tree.Tree;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class Solution {
    int re;

    public int maxPathSum(TreeNode root) {
        re = root.val;
        find(root);
        return re;
    }

    public int find(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int temp;
        int curre;
        if (node.left == null && node.right == null) {
            temp = node.val;
            curre = temp;
        } else if (node.left == null) {
            int r = find(node.right);
            temp = node.val + Math.max(r, 0);
            curre = temp;
        } else if (node.right == null) {
            int l = find(node.left);
            temp = node.val + Math.max(l, 0);
            curre = temp;
        } else {
            int l = find(node.left);
            int r = find(node.right);
            temp = node.val + Math.max(0, Math.max(r, l));
            curre = Math.max(temp, node.val + l + r);
        }
        re = Math.max(re, curre);
        return temp;
    }
}
