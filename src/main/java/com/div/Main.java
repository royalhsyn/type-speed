package com.div;

import java.io.*;
import java.net.URL;
import java.util.*;


public class Main {

    public static List<String> wordList=new ArrayList();
    private static int count =0;
    public static class Reminder {
        Timer timer;

        public Reminder(int seconds) {
            timer = new Timer();
            timer.schedule(new RemindTask(), seconds*1000);
        }

        class RemindTask extends TimerTask {
            public void run() {
                System.out.println(count+" "+"word");
                System.out.println("Time's up!");
                timer.cancel(); //Terminate the timer thread
            }
        }
    public static void main(String[] args) {

        String url="https://random-word-api.vercel.app/api?word";
        try {

            String filePath = "C:\\java\\type.txt";
            File file = new File(filePath);
            file.createNewFile();
            writeWordsToFile(url, filePath);
            addWordsToList(filePath);
            new Reminder(60);

            System.out.println("One minute start.");
            startTyping();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void writeWordsToFile(String url,String filePath) throws Exception{


        try(FileWriter fw=new FileWriter(filePath,true);
            BufferedWriter bw=new BufferedWriter(fw)) {
            URL wordUrl = new URL(url);
            for(int i=0;i<15;i++){
            Scanner scanner = new Scanner(wordUrl.openStream());
            String response = scanner.useDelimiter("\\A").next();
            //task1 String.replace
            int start=response.indexOf("[\"")+2;
            int end=response.indexOf("\"]");
            String word=response.substring(start,end);
            //System.out.println(word);
            bw.write(word);
            bw.newLine();}
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void addWordsToList(String filePath){
        try {
            FileReader fw = new FileReader(filePath);
            BufferedReader br =new BufferedReader(fw);
           wordList=br.lines().toList();
            System.out.println(wordList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void startTyping() {



            Scanner sc =new Scanner(System.in);
        String s=getRandomWord();
        System.out.println(s);
            String clinet =sc.next();
            if (s.equalsIgnoreCase(clinet)) {
                count++;
                startTyping();
            }else {
                System.out.println(count+" "+"word");
                System.exit(0);
            }
        }

    public static String getRandomWord(){
        Random random=new Random();
        int randomNumber=random.nextInt(wordList.size());
        return wordList.get(randomNumber);
    }
}}