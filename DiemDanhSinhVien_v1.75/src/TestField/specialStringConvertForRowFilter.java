/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestField;

import java.util.Scanner;
import java.util.regex.Matcher;

/**
 *
 * @author chuon
 */
public class specialStringConvertForRowFilter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //while(true){
            String input = "\\.[{(*+?^$|";
            //input += ".[{(*+?^$|";
            System.out.println("Input : " + input);
            if(input.contains("$")){
                System.out.println("$ co ton tai");
            }
            else{
                System.out.println("$ eo ton tai");
            }
            for(int i = 0; i < input.length(); i++){
                System.out.println("char at + " + i +": " + input.charAt(i));
                if(input.charAt(i) == '$'){
                    System.out.println("$ at " + i);
                    String inputTmp = input.substring(i, i+1);
                    System.out.println("inputTmp: " + inputTmp);
                }
            }
            System.out.println("index of $: " + input.indexOf("\\\\$"));;
                // s.replaceAll("\\\\", "\\\\\\\\"));
//            String inputModified = input.replaceAll(".", "\\.");
//            inputModified = inputModified.replaceAll("[", "\\[");
//            inputModified = inputModified.replaceAll("{", "\\{");
//            inputModified = inputModified.replaceAll("(", "\\(");
//            inputModified = inputModified.replaceAll("*", "\\*");
//            inputModified = inputModified.replaceAll("+", "\\+");
//            inputModified = inputModified.replaceAll("?", "\\?");

//        String inputModified = input.replaceAll("\\.", "\\\\.");
////        inputModified = inputModified.replaceAll("\\[", "\\\\[");
////        inputModified = inputModified.replaceAll("\\{", "\\\\{");
////        inputModified = inputModified.replaceAll("\\(", "\\\\(");
////        inputModified = inputModified.replaceAll("\\*", "\\\\*");
////        inputModified = inputModified.replaceAll("\\+", "\\\\+");
////        inputModified = inputModified.replaceAll("\\?", "\\\\?");
//        // 3 character nay se ko cho phep input vao "^", "$", "|"
//        //inputModified = inputModified.replaceAll("\\^", "\\\\^");
//        inputModified = inputModified.replace("$", "\\$");// cai nay dang bi loi
//        //inputModified = inputModified.replace("MotStringTmps", "\\$");// cai nay dang bi loi
//        //inputModified = inputModified.replaceAll("\\|", "\\\\|");
        String inputModified = input.replace("\\", "\\");
        inputModified = inputModified.replace(".", "\\.");
        inputModified = inputModified.replace("[", "\\[");
        inputModified = inputModified.replace("{", "\\{");
        inputModified = inputModified.replace("(", "\\(");
        inputModified = inputModified.replace("*", "\\*");
        inputModified = inputModified.replace("+", "\\+");
        inputModified = inputModified.replace("?", "\\?");
        inputModified = inputModified.replaceAll("\\^", "\\\\^");
        inputModified = inputModified.replace("$", "\\$");
        inputModified = inputModified.replaceAll("\\|", "\\\\|");
        inputModified = inputModified.replaceAll("\\|", "\\\\|");

        System.out.println("Input Modified: " + inputModified);
    }
}

