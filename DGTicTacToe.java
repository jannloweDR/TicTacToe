import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DGTicTacToe {
	public static boolean playerIsX;
	public static boolean winnerAnnounced = false;
	public static char[] gameboard;
	public static String serverMsg;
	public static Socket socket;
	public static InputStream input;
	public static BufferedReader reader;
	public static Scanner clientScanner;
	public static String clientInput;
	
	public static void main (String[] args) {	
		System.out.println("Start");
		try(Socket socket = new Socket("www.dagertech.net", 3800)) {
			input = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
			// display start message
			serverMsg = reader.readLine();
			System.out.println(serverMsg);
			boolean playerInput = false;
			
			// LOOP:keep prompting for X/x or O/o character 
			while(!playerInput) {
				//display char prompt
				serverMsg = reader.readLine();
				System.out.println(serverMsg);
			
				//client scans for input
				clientScanner = new Scanner(System.in);
				clientInput = clientScanner.nextLine();
				
				// client sends input to server
				byte[] outputData = clientInput.getBytes();
				OutputStream output = socket.getOutputStream();
				output.write(outputData);
			
				//if input contains X/x, playerIsX = true, elseIf input contains O/o playerIsX = false, else input doesn't contains these two, send error msg
				if(clientInput.matches("X") || clientInput.matches("x")) {
					playerInput = true;
					playerIsX = true;
				}else if(clientInput.matches("O") || clientInput.matches("o")){
					playerInput = true;
					playerIsX = false;
				}else {
					serverMsg = reader.readLine();
					System.out.println(serverMsg);
			}
			}
			
			
			//Gameloop begins
			while(winnerAnnounced == false) {
				if(playerIsX) {
					// get "9charboard" & display 9charboard
					serverMsg = reader.readLine();
					if(serverMsg.equals("'X' Wins!") || serverMsg.equals("'O' Wins!") || serverMsg.equals("Tie game.")) {
						System.out.println(serverMsg);
						break;
					}
					if(serverMsg.contains("Server move:") || serverMsg.contains("Client move") || serverMsg.contains("Error")) {
						System.out.println(serverMsg);
					}else {
						gameboard = serverMsg.toCharArray();
						displayGameboard(gameboard);
					}			
					
					// get "client move?" message from server
					serverMsg = reader.readLine();
					if(serverMsg.equals("'X' Wins!") || serverMsg.equals("'O' Wins!") || serverMsg.equals("Tie game.")) {
						System.out.println(serverMsg);
						break;
					}
					if(serverMsg.contains("Server move:") || serverMsg.contains("Client move") || serverMsg.contains("Error")) {
						System.out.println(serverMsg);
					}else {
						gameboard = serverMsg.toCharArray();
						displayGameboard(gameboard);
					}			
					
					//client scans input and sends input to server
					clientScanner = new Scanner(System.in);
					clientInput = clientScanner.nextLine();
					byte[] outputData = clientInput.getBytes();
					OutputStream output = socket.getOutputStream();
					output.write(outputData);
					
					// get "serverMove: #" message from server
					serverMsg = reader.readLine();
					if(serverMsg.equals("'X' Wins!") || serverMsg.equals("'O' Wins!") || serverMsg.equals("Tie game.")) {
						System.out.println(serverMsg);
						break;
					}
					if(serverMsg.contains("Server move:") || serverMsg.contains("Client move") || serverMsg.contains("Error")) {
						System.out.println(serverMsg);
					}else {
						gameboard = serverMsg.toCharArray();
						displayGameboard(gameboard);
					}					
				}
				if(!playerIsX) {
					serverMsg = reader.readLine();
					if(serverMsg.equals("'X' Wins!") || serverMsg.equals("'O' Wins!") || serverMsg.equals("Tie game.")) {
						System.out.println(serverMsg);
						break;
					}
					if(serverMsg.contains("Server move:") || serverMsg.contains("Client move") || serverMsg.contains("Error")) {
						System.out.println(serverMsg);
					}else {
						gameboard = serverMsg.toCharArray();
						displayGameboard(gameboard);
					}	
					
					serverMsg = reader.readLine();
					if(serverMsg.equals("'X' Wins!") || serverMsg.equals("'O' Wins!") || serverMsg.equals("Tie game.")) {
						System.out.println(serverMsg);
						break;
					}
					if(serverMsg.contains("Server move:") || serverMsg.contains("Client move") || serverMsg.contains("Error")) {
						System.out.println(serverMsg);
					}else {
						gameboard = serverMsg.toCharArray();
						displayGameboard(gameboard);
					}	
					
					// get "client move?"
					serverMsg = reader.readLine();
					if(serverMsg.equals("'X' Wins!") || serverMsg.equals("'O' Wins!") || serverMsg.equals("Tie game.")) {
						System.out.println(serverMsg);
						break;
					}
					if(serverMsg.contains("Server move:") || serverMsg.contains("Client move") || serverMsg.contains("Error")) {
						System.out.println(serverMsg);
					}else {
						gameboard = serverMsg.toCharArray();
						displayGameboard(gameboard);
					}	
					
					//client scans input and sends input
					clientScanner = new Scanner(System.in);
					clientInput = clientScanner.nextLine();
					byte[] outputData = clientInput.getBytes();
					OutputStream output = socket.getOutputStream();
					output.write(outputData);
					
				}
			}
			serverMsg = reader.readLine();
			System.out.println(serverMsg);
			socket.close();
			
		} catch (UnknownHostException e) {
			System.out.println("Server not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O error encountered");
			e.printStackTrace();
		}
		
	}

	private static void displayGameboard(char[] gameBoard) {
		System.out.println("");
		System.out.println(gameBoard[0]+ "  |  " +gameBoard[1]+  "  |  " +gameBoard[2]);
		System.out.println("---+-----+---");
		System.out.println(gameBoard[3]+ "  |  " +gameBoard[4]+  "  |  " +gameBoard[5]);
		System.out.println("---+-----+---");
		System.out.println(gameBoard[6]+ "  |  " +gameBoard[7]+  "  |  " +gameBoard[8]);
		System.out.println("");
	}	
}
