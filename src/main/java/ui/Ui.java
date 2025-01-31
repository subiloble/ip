package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import task.Task;
import task.TaskList;

public class Ui {
    String logo;

    public Ui() {
        this.logo = "  ______ _    _ _____  ______ _   __      __       \n"
                + " |  ____| |  | |  __ \\|  ____| | / /    / /\\ \\     \n"
                + " | |__  | |  | | |__) | |__  |  / /    / /__\\ \\     \n"
                + " |  __| | |  | |  _  /|  __| |   <    / /____\\ \\       \n"
                + " | |____| |__| | | \\ \\| |____|  \\ \\  / /      \\ \\ \n"
                + " |______|\\____/|_|  \\_\\______|_| \\_\\/_/        \\_\\\n";
        ;
    }

    public void printLine(){
        System.out.print("  ");
        for(int i = 0; i < 50; i++)
        {
            System.out.print("_");
        }
        System.out.println();
        System.out.println();
    }

    public void printLogo(){
        System.out.println(logo);
    }

    public void welcome(){
        System.out.println("  Hello! I'm Eureka.");
        System.out.println("  What can I do for you?");
        printLine();
    }

    public void byeMessage(){
        printLine();
        System.out.println("  Bye. Hope to see you again soon!");
        printLine();
    }

    public void listMessage(TaskList tasks){
        printLine();
        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + tasks.get(i));
        }
        printLine();
    }

    public void markMessage(Task task){
        printLine();
        System.out.println("  Nice! I've marked this task as done:");
        System.out.println("    " + task);
        printLine();
    }

    public void unmarkMessage(Task task){
        printLine();
        System.out.println("  OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
        printLine();
    }

    public void todoWarning(){
        printLine();
        System.out.println("  A todo without a description doesn’t work. Try again with more details!");
        printLine();
    }

    public void todoMessage(ArrayList<Task> tasks){
        printLine();
        System.out.println("  Got it. I've added this task:");
        System.out.println("    " + tasks.get(tasks.size() - 1));
        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    public void deadMessage(TaskList tasks){
        printLine();
        System.out.println("  Got it. I've added this task:");
        System.out.println("    " + tasks.get(tasks.size() - 1));
        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    public void eventMessage(TaskList tasks){
        printLine();
        System.out.println("  Got it. I've added this task:");
        System.out.println("    " + tasks.get(tasks.size() - 1));
        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    public void deleteNotFound(){
        printLine();
        System.out.println("Oops! That task doesn’t exist. Try a valid task number.");
        printLine();
    }

    public void deleteMessage(Task removedTask, int size){
        printLine();
        System.out.println("  Noted. I've removed this task:");
        System.out.println("    " + removedTask);
        System.out.println("  Now you have " + size + " tasks in the list.");
        printLine();
    }

    public void notUnderstand(){
        printLine();
        System.out.println("  Sorry, I didn’t get that. Could you try something else?");
        printLine();
    }
}