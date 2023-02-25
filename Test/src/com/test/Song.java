package com.test;

public class Song implements Comparable<Song> {
    String title;
    String artist;

    public Song(String s1, String s2) {
        title = s1;
        artist = s2;
    }

    public int compareTo(Song s) {
        return title.compareTo(s.title);
    }

}
