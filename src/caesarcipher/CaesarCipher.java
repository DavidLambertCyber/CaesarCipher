package caesarcipher;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Lambert MCSE, BSCS
 * @
 */
public class CaesarCipher {

    //globals----------------------
    static int slow;
    static int shift;
    static String choice = "";
    static String mess_in = new String();
    static char[] mess_out = new char[0xfa0];
    static Scanner input = new Scanner(System.in);
    static boolean encrypt = true;
    static char[] al = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z'};
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length > 0){
            slow = Integer.parseInt(args[0]);
        } else {
            slow = 0;
        }
        
        intro();
        getInputLine();
        ask();

        if (encrypt) {
            processEncrypt();
        } else {
            processDecrypt();
        }

        outputMess();
        System.out.println();
        System.out.println("Goodbye");

        System.exit(0);

    }
    
    private static void ask() {
        System.out.println();
        System.out.println();
        System.out.println("Encrypt or Decrypt ? (e or d)");
        System.out.print("--> ");

        choice = input.nextLine();

        if (choice.toLowerCase().startsWith("d")) {
            encrypt = false;
        } else {
            encrypt = true;
        }

    }

    private static void intro() {
        System.out.println();
        System.out.println();
        System.out.println("Welcome to the Caesar Cypher");
        System.out.println("Please enter the message on one line");

    }

    private static void getInputLine() {

        System.out.print("--> ");
        mess_in = input.nextLine().toLowerCase();

    }

    private static char getCharRight(char ch, int shift) {
        char letter = ' ';

        if (ch == ' ') {
            return letter;
        }
        
        int index;

        if (shift > 25) {    //if 26 or greater then adjust
            shift = shift % 26;
        }

        index = Arrays.binarySearch(al, ch);
        if (index < 0) {
            System.err.println("Error in cipher wheel");
            System.exit(0);
        }

        index = index + shift;

        if (index >= 26) {
            index = index - 26;
        }

        return al[index];
    }

    private static char getCharLeft(char ch, int shift) {
        char letter = ' ';

        if (ch == ' ') {
            return letter;
        }

        int index;

        if (shift > 25) {    //if 26 or greater then adjust
            shift = shift % 26;
        }

        index = Arrays.binarySearch(al, ch);
        if (index < 0) {
            System.err.println("Error in cipher wheel");
            System.exit(0);
        }

        index = index - shift;

        if (index < 0) {
            index = index + 26;
        }

        return al[index];
    }

    private static void processDecrypt() {
        char ch = 'a';
        int index = 0;
        int length = mess_in.length();

        System.out.println();
        System.out.println("Please enter number of character shifts to right");
        System.out.println("Higher than 26 will loop back to a");
        System.out.print("--> ");

        shift = input.nextInt();

        while (ch != '\0' && index < length) {
            ch = mess_in.charAt(index);     //char of message in
            ch = getCharRight(ch, shift);       //shift right of alphebet            
            mess_out[index++] = Character.toUpperCase(ch);  //add to message out            
        }

        //decrypt will be in cap

    }

    private static void processEncrypt() {
        char ch = 'a';
        int index = 0;
        int length = mess_in.length();

        System.out.println();
        System.out.println("Please enter number of character shifts to left");
        System.out.println("Higher than 26 will loop back to a");
        System.out.print("--> ");

        shift = input.nextInt();

        while (ch != '\0' && index < length) {
            ch = mess_in.charAt(index);     //char of message in
            ch = getCharLeft(ch, shift);       //shift left of alphebet            
            mess_out[index++] = Character.toLowerCase(ch);  //add to message out            
        }

        //incrypt will be in lowercase

    }

    private static void outputMess() {
        int index = 0;
        long pause = shift * slow;
        int length = mess_in.length();

        System.out.println();
        System.out.println("Your processed message is below.");

        while (index < length) {
            System.out.print("" + mess_out[index++]);

            try {
                Thread.sleep(pause);
            } catch (InterruptedException ex) {
                Logger.getLogger(CaesarCipher.class.getName()).
                        log(Level.SEVERE, null, ex);
            }

        }
    }
}
