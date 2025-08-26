import java.net.*;
import java.io.*;

public class server {
    public static void main(String[] args) throws IOException {
            // Creates a server at localhost on port 1234
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Server is waiting for a connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // Creating an input stream in order to read messages from the client
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

            // Creating an output stream in order to send messages to the client
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));

            // Asks for the server's name (the person's name who'll be chatting)
            System.out.print("Enter your name (Server): ");
            String serverName = serverInput.readLine();
            
            // Asks for the client's name(the other person's name who'll be chatting)
            dos.writeUTF("What is your name?");
            String clientName = dis.readUTF();
            System.out.println(clientName + " has joined the chatroom!");

            String messageFromClient = "", messageToClient = "";
            boolean clientSaidBye = false, serverSaidBye = false;



            while (true) {
                // Reads messages from the client
                messageFromClient = dis.readUTF();
                System.out.println(clientName + ": " + messageFromClient);

                // Checking to see whether the client user typed in the word "bye" to begin the termination of the code
                if (messageFromClient.equalsIgnoreCase("bye")) {
                    clientSaidBye = true;
                }

                // Sends responses to the client
                System.out.print(serverName + ": ");
                messageToClient = serverInput.readLine();
                dos.writeUTF(messageToClient);

                // Checking to see whether the server user typed in the word "bye" to begin the termination of the code
                if (messageToClient.equalsIgnoreCase("bye")) {
                    serverSaidBye = true;
                }

                // Ends the loop only if both the client user and server user have typed in the word "bye", then the code will terminate
                if (clientSaidBye && serverSaidBye) {
                    System.out.println("Both said 'bye'. Closing connection...");
                    break;
                }
            }

            // Closes streams and socket
            dis.close();
            dos.close();
            clientSocket.close();
        }
    }
}
