package com.test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Topological {
    ArrayList<ArrayList<Integer>> edges;
    int[] in;

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        edges = new ArrayList<ArrayList<Integer>>();  //记录每个节点指向哪些节点
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<Integer>());
        }
        in = new int[numCourses];   //记录每个节点的入度
        for (int[] pre : prerequisites) {   //存储图的结构，方便查找
            edges.get(pre[1]).add(pre[0]);
            in[pre[0]] = in[pre[0]] + 1;
        }
        Queue<Integer> queue = new ArrayDeque<>();
        for (int j = 0; j < numCourses; j++) {   //初次遍历寻找入度为0的节点
            if (in[j] == 0) {
                queue.add(j);
            }
        }
        int visited = 0;  //由于不用记录拓扑排序
        while (!queue.isEmpty()) {
            visited = visited + 1;
            int u = queue.poll();
            for (int v : edges.get(u)) {
                in[v] = in[v] - 1;
                if (in[v] == 0) {
                    queue.add(v);
                }
            }
        }
        return visited == numCourses;
    }
}
