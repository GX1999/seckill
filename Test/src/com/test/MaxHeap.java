package com.test;

import java.util.PriorityQueue;

public class MaxHeap {

    //（1）自建大根堆，不使用数的结构，而是在数组中使用索引
    public int findKthLargest(int[] nums, int k) {
        int len = nums.length;
        buildMaxHeap(nums, len);
        for (int i = len - 1; i >= 0; i--) {
            swap(nums, i, 0);
            len = len - 1;
            maxHeap(nums, len, 0);           //每次将root放到最后，从上而下更新
        }
        return nums[nums.length - k];
    }

    public void buildMaxHeap(int[] nums, int len) {   //建立大根堆，需要从下而上建造，每次维护节点时从上而下
        for (int i = len / 2 - 1; i >= 0; i--) {       //len/2-1是最后一个非叶子节点索引
            maxHeap(nums, len, i);
        }
    }

    public void maxHeap(int[] nums, int len, int cur) {
        int left = cur * 2 + 1;
        int right = cur * 2 + 2;
        int max = cur;
        if (left < len && nums[left] > nums[max]) {
            max = left;
        }
        if (right < len && nums[right] > nums[max]) {
            max = right;
        }
        if (max != cur) {
            swap(nums, max, cur);
            maxHeap(nums, len, max);
        }
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //（2）利用PriorityQueue 小根堆API(优先队列)
    /*public void findKthLargest(int[] nums, int k) {
        int len= nums.length;
        PriorityQueue<Integer> minHeap=new PriorityQueue<>();
        for (int i=0;i<len;i++){
            minHeap.offer(nums[i]);
        }
        for (int j=0;j<len;j++){
            System.out.println(minHeap.peek());
            minHeap.poll();
        }

    }*/


}
