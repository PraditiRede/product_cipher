import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.*;


// This function receives cipher-text and key 
// and returns the original text after decryption 
class RailFenceDec{
    String decryptRailFence(String cipher, int key) 
    { 
        // create the matrix to cipher plain text 
        // key = rows , length(text) = columns 
        char rail[][] = new char[key][cipher.length()]; 
      
        // filling the rail matrix to distinguish filled 
        // spaces from blank ones 
        for (int i=0; i < key; i++) 
            for (int j=0; j < cipher.length(); j++) 
                rail[i][j] = '\n'; 
      
        // to find the direction 
        boolean dir_down = true; 
      
        int row = 0, col = 0; 
      
        // mark the places with '*' 
        for (int i=0; i < cipher.length(); i++) 
        { 
            // check the direction of flow 
            if (row == 0) 
                dir_down = true; 
            if (row == key-1) 
                dir_down = false; 
      
            // place the marker 
            rail[row][col++] = '*'; 
      
            // find the next row using direction flag 
            if(dir_down)
                row++;
            else
                row--; 
        } 
      
        // now we can construct the fill the rail matrix 
        int index = 0; 
        for (int i=0; i<key; i++) {
            for (int j=0; j<cipher.length(); j++) {
                if (rail[i][j] == '*' && index<cipher.length()) {
                    rail[i][j] = cipher.charAt(index);
                    index++;
                }
            }
        }
      
      
        // now read the matrix in zig-zag manner to construct 
        // the resultant text 
        String result = cipher; 
        int k=0;
        row = 0;
        col = 0; 
        for (int i=0; i< cipher.length(); i++) 
        { 
            // check the direction of flow 
            if (row == 0) 
                dir_down = true; 
            if (row == key-1) 
                dir_down = false; 
      
            // place the marker 
            if (rail[row][col] != '*') {
                result = result.substring(0,k) + rail[row][col++] + result.substring(k+1);
                // result[k] = rail[row][col++];
                k++;
            }
      
            // find the next row using direction flag 
            if(dir_down)
                row++;
            else
                row--; 
        } 
        return result; 
    } 
}

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
class VigDec{
	// This function decrypts the encrypted text 
	// and returns the original text 
	static String VignereDecrypt(String ct, String key) 
	{  
		String pt=""; 
		for (int i = 0 ; i < ct.length() && i < key.length(); i++) 
		{ 
			if(Character.isWhitespace(ct.charAt(i)))
			{
				pt+=" ";
				continue;
			}
			// converting in range 0-25 
			int x = (ct.charAt(i) - key.charAt(i) + 26) %26; 
			// convert into alphabets(ASCII) 
			x += 'A'; 
			pt+=(char)(x); 
		} 
		return pt; 
	} 
}


class server
{ 
	// Driver code 
	public static void main(String[] args) 
	{ 
		int n, ch;
		Scanner sc = new Scanner(System.in);
		String str; 
		String keyword = "LOCK"; 
		try{  
			ServerSocket ss = new ServerSocket(4444);
			System.out.println("Waiting for client connection");
			Socket s= ss.accept();
			System.out.println("Established connection with Client");
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());  
			String ct2 = dis.readUTF();  
			System.out.println("Ciphertext received: " + ct2+ "\n"); 
			RailFenceDec d = new RailFenceDec();
			String ct1 = d.decryptRailFence(ct2, 2);
			System.out.println(ct1);
			System.out.println("Rail Fence Decryption: " + ct1);
			genKey g = new genKey();
			String key = g.generateKey(ct1, keyword); 
			VigDec v = new VigDec();
			String msg = v.VignereDecrypt(ct1,key) ;//Put ct2 once done
			System.out.println("Plain Text after Vignere Decryption : " + msg); 
			s.close();
		} 
			catch(Exception e){
				System.out.println(e);
			}
		}
} 






/*
C:\Users\NISHA MASCARENHAS\Desktop>java svignere
Waiting for client connection
Established connection with Client
Ciphertext received: SNZYCFSV YZ

SSNVZ YYCZF
Rail Fence Decryption: SSNVZ YYCZF
Plain Text after Vignere Decryption : HELLO WORLD

C:\Users\NISHA MASCARENHAS\Desktop>
*/