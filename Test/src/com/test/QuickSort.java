package com.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//public class QuickSort {
//    public void quickSort(int[] arr,int left,int right){
//        if (left>=right){
//            return;
//        }
//        int len=arr.length;
//        int cur=left;
//    }
//}


//  自己写的版本，但是会超时
//public class QuickSort {
//    public ArrayList<Integer> quickSort(ArrayList<Integer> array){
//        if (array.size()<=1){
//            return array;
//        }
//        ArrayList<Integer> pre=new ArrayList<>();
//        ArrayList<Integer> behind=new ArrayList<>();
//        Collections.shuffle(array);
//        int cur=array.get(0);
//        for (int i=1;i<array.size();i++){
//            int a=array.get(i);
//            if (a<cur){
//                pre.add(a);
//            }else {
//                behind.add(a);
//            }
//        }
//        ArrayList<Integer> re=new ArrayList<>();
//        re.addAll(quickSort(pre));re.add(cur);re.addAll(quickSort(behind));
//        return re;
//    }
//}