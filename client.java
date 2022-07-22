/*
NISHA MASCARENHAS
TE COMPUTERS	8616
CLIENT SIDE USING TCP
*/
import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.*;

class genKey
{
	// This function generates the key in 
	// a cyclic manner until it's length isi'nt 
	// equal to the length of original text 
	static String generateKey(String str, String key) 
	{ 
		int x = str.length(); 
		for (int i = 0;i<str.length(); i++) 
		{ 
			if (x == i) 
				i = 0; 
			if (key.length() == str.length()) 
				break; 
			key+=(key.charAt(i)); 
		} 
		return key; 
	} 
}

class VigEnc{
	// This function returns the encrypted text 
	// generated with the help of the key 
	static String VignereEncrypt(String str, String key) 
	{ 
		String ct=""; 

		for (int i = 0; i < str.length(); i++) 
		{  
			if(Character.isWhitespace(str.charAt(i)))
			{
				ct+=" ";
				continue;
			}
			// converting in range 0-25 
			int x = (str.charAt(i) + key.charAt(i)) %26; 

			// convert into alphabets(ASCII) 
			x += 'A'; 

			ct+=(char)(x); 
		} 
		return ct; 
	} 
}

class RailFenceEnc{
    // function to encrypt a message 
    String encryptRailFence(String text, int key) 
    { 
        // create the matrix to cipher plain text 
        // key = rows , length(text) = columns 
        char rail[][] = new char[key][(text.length())]; 
      
        // filling the rail matrix to distinguish filled 
        // spaces from blank ones 
        for (int i=0; i < key; i++) 
            for (int j = 0; j < text.length(); j++) 
                rail[i][j] = '\n'; 
      
        // to find the direction 
        boolean dir_down = false; 
        int row = 0, col = 0; 
      
        for (int i=0; i < text.length(); i++) 
        { 
            // check the direction of flow 
            // reverse the direction if we've just 
            // filled the top or bottom rail 
            if (row == 0 || row == key-1) 
                dir_down = !dir_down; 
      
            // fill the corresponding alphabet 
            rail[row][col++] = text.charAt(i); 
      
            // find the next row using direction flag 
            if(dir_down)
                row++;
            else 
                row--; 
        } 
      
        //now we can construct the cipher using the rail matrix 
        String result = text; 
        int k=0;
        for (int i=0; i < key; i++) {
            for (int j=0; j < text.length(); j++) {
                if (rail[i][j]!='\n') {
                    result = result.substring(0,k) + rail[i][j] + result.substring(k+1);
                    // result.charAt(k) = rail[i][j];
                    k++;
                }
            }
        }
      
        return result; 
    } 
}

class client{
	public static void main(String Args[]) throws UnknownHostException , IOException{ 
		String keyword = "LOCK";
		int ch,n,cnt,j;
		Socket c = new Socket("localhost" , 4444); 
		Scanner sc=new Scanner(System.in);
		DataInputStream dis = new DataInputStream(c.getInputStream());
		DataOutputStream dos = new DataOutputStream(c.getOutputStream());
		System.out.println("Enter the message");  
		String str=sc.nextLine();  
		genKey g = new genKey(); 
		String key = g.generateKey(str, keyword);
		VigEnc v = new VigEnc();
		String ct1 = v.VignereEncrypt(str, key);
		System.out.println("Vignere Cipher: " + ct1); 
		RailFenceEnc e = new RailFenceEnc();
        String ct2 = e.encryptRailFence(ct1, 2);
        System.out.println("Rail Fence Cipher: " + ct2);
		dos.writeUTF(ct2); 
      }
} 


/*

C:\Users\NISHA MASCARENHAS\Desktop>javac client.java

C:\Users\NISHA MASCARENHAS\Desktop>java client
Enter the message
HELLO WORLD
Vignere Cipher: SSNVZ YYCZF
Rail Fence Cipher: SNZYCFSV YZ

C:\Users\NISHA MASCARENHAS\Desktop>

*/

