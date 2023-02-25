package com.test;

public class MergeSort {
    int[] temp;

    public int[] sortArray(int[] nums) {    //函数入口
        temp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    public void mergeSort(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) / 2;
        mergeSort(nums, l, mid);
        mergeSort(nums, mid + 1, r);
        int count = 0;  //对temp这个临时数据建立索引
        int first = l;
        int second = mid + 1;   //两个数组的第一个元素
        while (first <= mid && second <= r) {
            if (nums[first] <= nums[second]) {
                temp[count] = nums[first];
                count++;
                first++;
            } else {
                temp[count] = nums[second];
                count++;
                second++;
            }
        }
        while (first <= mid) {   // 当其中一个数组全部被选完，则另一个数组直接copy到temp
            temp[count] = nums[first];
            count++;
            first++;
        }
        while (second <= r) {
            temp[count] = nums[second];
            count++;
            second++;
        }
        for (int k = 0; k < r - l + 1; k++) {   //将排序好的temp覆盖源数组对应位置
            nums[k + l] = temp[k];
        }
    }
}
