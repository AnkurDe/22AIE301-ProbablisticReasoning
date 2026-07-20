package com.ankur;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        int max = 10, min = 7;
        for (int i = 0; i < 100; i++) {

            IO.println((int) (Math.random() * ((max - min) + 1)) + min);
        }
    }
}
