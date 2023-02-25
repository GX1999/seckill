package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class SongBox {
    ArrayList<Song> al = new ArrayList<Song>();

    public void getSong() {
        try {
            File f = new File("data/test.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            Song temp;
            while ((line = br.readLine()) != null) {
                temp = new Song(line.split("/")[0], line.split("/")[1]);
                al.add(temp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void go() {
        Collections.sort(al);
        TreeSet<Song> t = new TreeSet<>();
        t.addAll(al);
        Iterator<Song> i = t.iterator();

        //  System.out.println(t);
        while (i.hasNext()) {
            System.out.println(i.next().title);
        }

    }
}
