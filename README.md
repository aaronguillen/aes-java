# aes-java
Some Java AES stuff

This README written 12 November 2016

An Assignment from Professor Marius Zimand at Towson University

You can see his school webpage here: http://orion.towson.edu/~mzimand/

1. Write a Java program that allows the user to enter a string S consisting of 4 characters, a key K consisting of 4 hex digits, and an init vector IV consisting of 4 hex digits. For example, string S=“aabb”, K=“a2b2”, and IV=“abab”.
a) The program generates 1) the string ES, which is the string S repeated 4 times (16 characters in all); 2) the key EK, which is the key K repeated 4 times; and 3) the init vector EIV, which is the init vector IV repeated 4 times.  The program next uses AES in CBC mode with init vector EIV to encrypt the string ES with the key EK. 
b) The program then prints the encrypted result. 

Sources I used to achieve this particular solution (may or may not also be cited in comments in the code):

http://stackoverflow.com/questions/15554296/simple-java-aes-encrypt-decrypt-example

the hexCode method, I, irresponibly, don't know where I got it from. I can tell you it's not original and that I found it somewhere on the Internet.

Also NOTE:
I'm not sure with what version of Java I developed this. Here is (possibly) The NetBeans version information:

Product Version: NetBeans IDE 7.4 (Build 201310111528)
Updates: NetBeans IDE is updated to version NetBeans 7.4 Patch 3
Java: 1.7.0_51; Java HotSpot(TM) 64-Bit Server VM 24.51-b03
Runtime: Java(TM) SE Runtime Environment 1.7.0_51-b13
System: Windows 8 version 6.2 running on amd64; Cp1252; en_US (nb)

NOTE: This older version of NetBeans seems to think that Windows 10 is Windows 8.

I pulled this code from an old email I sent a friend with this code as an attachment. It's possible that I developed on a different machine than my usual development machine.
