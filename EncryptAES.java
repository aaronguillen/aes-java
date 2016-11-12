package encryptaes;

import java.util.Scanner;
import java.util.ArrayList;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;

public class EncryptAES {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        //Prompts and Input
        System.out.print("Please enter the plain text string:\t");
        String plainText = in.nextLine();
        
        String key = "g"; //NOT hex
        do {
            System.out.print("Please enter the key:\t0x");
            key = in.nextLine();
        } while (!isHex(key) || key.length() < 4); //If we don't have a valid hexadecimal string, or our string is less than four characters in legnth 
        
        String iv = "g"; //NOT hex
        do {
            System.out.print("Please enter the initialization vector:\t0x");
            iv = in.nextLine();
        } while (!isHex(iv) || iv.length() < 4); //If we don't have a valid hexadecimal string, or our string is less than four characters in legnth
        
        //Force whatever we just got from the user in our 
        plainText = forceInputFirst(plainText);
        key = forceInputLast(key);
        iv = forceInputLast(iv);
        
        //At this point, our input should be sanitized to fit our specific needs
        //That is "Write a Java program that allows the user to enter a string S consisting of 4 characters, a key K consisting of 4 hex digits, and an init vector IV consisting of 4 hex digits."
        
        //Next, "The program generates 1) the string ES, which is the string S repeated 4 times (16 characters in all); 2) the key EK, which is the key K repeated 4 times; and 3) the init vector EIV, which is the init vector IV repeated 4 times."
        
        //For this program, we repeat these values
        String es = plainText + plainText + plainText + plainText;
        String ek = key + key + key + key;
        String eiv = iv + iv + iv + iv;
        
        
        //Output Confirmation
        System.out.println("PT:\t" + es + "\nK:\t" + ek + "\nIV:\t" + eiv);
        
        //Following lines print ASCII values for valid Hex digits (0 - 9, A - F, a - f)
        /*
        for (char i = 'a'; i <= 'f'; i++) {
            System.out.println(Character.toString(i).getBytes()[0]);
        }
        
        for (char i = 'A'; i <= 'F'; i++) {
            System.out.println(Character.toString(i).getBytes()[0]);
        }
        
        for (char i = '0'; i <= '9'; i++) {
            System.out.println(Character.toString(i).getBytes()[0]);
        }
        */
        
        //Convert Byte to ASCII -- Example
        //Byte b = 65;
        //System.out.println((char)b.byteValue());
        
        //Alright, next..
        //"The program next uses AES in CBC mode with init vector EIV to encrypt the string ES with the key EK."
        
        //Alright, so we need our key and IV to be in byte arrays.
        byte[] keybytes = hexStringToByteArray(ek);
        byte[] ivbytes = hexStringToByteArray(eiv);
        
        //Now. Let's do crypto.
        SecretKeySpec skeySpec = new SecretKeySpec(keybytes, "AES");
        IvParameterSpec ivspec = new IvParameterSpec(ivbytes);
        
        byte[] cipherText = {};
        
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);
            cipherText = cipher.doFinal(es.getBytes());
        }
        catch (Exception e) {
            e.getStackTrace();
            System.exit(0);
        }
        
        //Lastly, "The program then prints the encrypted result."
        
        System.out.println("Ciphertext: " + hexEncode(cipherText) + "\n");
    }
    
    //We need a method to make our byte arrays look like hex:
    //(Code is stolen)
    
    /**
     * Hex encodes a byte array. <BR>
     * Return an empty string if the input array is null or empty.
     * 
     * @param input bytes to encode
     * @return string containing hex representation of input byte array
     */
    private static String hexEncode (byte[] input) {
        if (input == null || input.length == 0) {
            return "";
        }
        
        int inputLength = input.length;
        StringBuilder output = new StringBuilder(inputLength * 2);
        
        for (int i = 0; i < inputLength; i++) {
            int next = input[i] & 0xff;
            if (next < 0x10) {
                output.append("0");
            }
            
            output.append(Integer.toHexString(next));
        }
        
        return output.toString();
    }
    
    //This method really should throw an error, but I'm
    //going to ignore my security senses and just not.
    private static byte[] hexStringToByteArray(String s) {
        //Make sure we got hex
        if (!isHex(s)) {
            return null;
        }
        
        //All this is very clever. I got the main gist of it from StackOverflow
        //http://stackoverflow.com/questions/20110533/converting-hexadecimal-string-to-decimal-integer
        //username: AlexAndro
        //provided the lovely logic I'll use below
        
        //Let's get hex values in order
        String hex = "0123456789ABCDEF";
        //Since our string above uses upper case ...
        s = s.toUpperCase();
        
        ArrayList<Integer> builder = new ArrayList<Integer>();
        
       
        for (int i = 0; i < s.length(); i++) {//For every character in s
            char c = s.charAt(i);
            int v = hex.indexOf(c);
            builder.add(v);
        }
        
        //Just to make sure we got everything right
        /*
        for (int i = 0; i < builder.size(); i++) {
            System.out.println(builder.get(i));
        }
        */
        
        //We need this in a byte array
        byte[] ret = new byte[builder.size()];
        
        for (int i = 0; i < ret.length; i++) {
            ret[i] = builder.get(i).byteValue();
        }
        
        return ret;
    }
    
    //For this program, we force our input to be 4-character long strings
    //For the Hex I want the least significant digits, so
    //if they enter 0xDEADBEEF I just want 0xBEEF
    private static String forceInputLast(String s) {
        return s.substring(s.length() - 4, s.length());
    }
    
    //However, for the plain text I want just the first four characters, so
    //if they enter "This is plain text." I just want "This"
    private static String forceInputFirst(String s) {
        return s.substring(0, 4);
    }
    
    private static boolean isHex(String h) {
        //Instantiate list with legal hex values
        ArrayList<Byte> hexASCIIBytes = new ArrayList<Byte>();
        for (char i = 'a'; i <= 'f'; i++) {
            hexASCIIBytes.add(Character.toString(i).getBytes()[0]);
        }
        
        for (char i = 'A'; i <= 'F'; i++) {
            hexASCIIBytes.add(Character.toString(i).getBytes()[0]);
        }
        
        for (char i = '0'; i <= '9'; i++) {
            hexASCIIBytes.add(Character.toString(i).getBytes()[0]);
        }
        
        byte[] bh = h.getBytes();
        
        //assume true
        boolean isHex = true;
        
        for (byte i : bh) {
            //If we find a value that is not in hexASCIIBytes, false
            if (!hexASCIIBytes.contains(i)) {
                isHex = false;
                break;
            }
        }
        
        return isHex;
    }
    
}
