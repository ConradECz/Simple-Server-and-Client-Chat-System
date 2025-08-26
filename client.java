import java.net.*;
import java.io.*;

public class client {
    public static void main(String[] args) throws IOException {
        // Connect to server at localhost on port 1234
        Socket socket = new Socket("localhost", 1234);

        // Creating an output stream in order to send messages to the server
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // Creating an input stream in order to read messages from the server
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));

        // Ask for the client's name (the person's name who'll be chatting)
        System.out.print("Enter your name (Client): ");
        String clientName = clientInput.readLine();

        // Read the server's message asking for the user's name that is typed 
        String serverMessage = dis.readUTF();
        System.out.println("Server: " + serverMessage);

        // Sends the client's name back to the server
        dos.writeUTF(clientName);

        String messageFromServer = "", messageToServer = "";
        boolean clientSaidBye = false, serverSaidBye = false;


        while (true) {
            // Sends messages to the server
            System.out.print(clientName + ": ");
            messageToServer = clientInput.readLine();
            dos.writeUTF(messageToServer);

            // Checking to see whether the client user typed in the word "bye" to begin the termination of the code
            if (messageToServer.equalsIgnoreCase("bye")) {
                clientSaidBye = true;
            }

            // Read response from server
            messageFromServer = dis.readUTF();
            System.out.println("Server: " + messageFromServer);

            // Checking to see whether the server user typed in the word "bye" to begin the termination of the code
            if (messageFromServer.equalsIgnoreCase("bye")) {
                serverSaidBye = true;
            }

            // Ends the loop only if both the client user and server user have typed in the word "bye", then the code will terminate
            if (clientSaidBye && serverSaidBye) {
                System.out.println("Closing chatroom.");
                break;
            }
        }

        // Closes streams and socket
        dos.close();
        dis.close();
        socket.close();
    }
}
