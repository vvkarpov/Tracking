package ru.vkarpov.dev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
Study task: Tracking changes
*/

public class Solution {

    public static String file1 = null;
    public static String file2 = null;
    public static List<String> file1Array = new ArrayList<>();
    public static List<String> file2Array = new ArrayList<>();
    public static List<LineItem> lines = new ArrayList<>();

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            file1 = reader.readLine();
            file2 = reader.readLine();
        }catch (IOException e) {
            e.printStackTrace();
        }

        readFiles(file1, file1Array);//Read file1 in to file1Array
        readFiles(file2, file2Array);//Read file2 in to file2Array

        for (int i = 0; i < file1Array.size(); i++) {
            if (file1Array.get(i).equals(file2Array.get(i))) {
                lines.add(new LineItem(Type.SAME, file1Array.get(i)));
            }
            else if (i + 1 < file1Array.size() && file1Array.get(i + 1).equals(file2Array.get(i))){
                lines.add(new LineItem(Type.REMOVED, file1Array.get(i)));
                file2Array.add(i, file1Array.get(i));
            }
            else if (i + 1 < file1Array.size() && file1Array.get(i).equals(file2Array.get(i + 1))){
                lines.add(new LineItem(Type.ADDED, file2Array.get(i)));
                file1Array.add(i, file2Array.get(i));
            }
        }
        for (LineItem line : lines){
            System.out.println(line.type + " " + line.line);
        }
    }

    public static enum Type {
        ADDED,        //add new string
        REMOVED,      //delete string
        SAME          //without changes
    }

    public static void readFiles(String filename, List<String> list){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            while (reader.ready()){
                list.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class LineItem {
        public Type type;
        public String line;

        public LineItem(Type type, String line) {
            this.type = type;
            this.line = line;
        }
    }
}