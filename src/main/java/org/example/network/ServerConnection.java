package org.example.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * The ServerConnection class represents a connection to a server using Java sockets.
 * <p>
 * Protocol:
 * Client to Server Messages:
 * <ul>
 *     <li>JOIN_LOBBY {@code <lobby_id>}: Join the lobby with the specified ID.</li>
 *     <li>LEAVE_LOBBY: Leave the current lobby.</li>
 *     <li>READY: Signal that the client is ready to start the game.</li>
 *     <li>CHOOSE_AMOUNT {@code <amount>}: Choose the amount of shrimp to fish for the current
 *     round.</li>
 *     <li>COMMUNICATE {@code <message>}: Send a message to other players during communication
 *     rounds
 *     .</li>
 *     <li>DISCONNECT: Disconnect from the server.</li>
 *     <li>BECOME_ADMIN {@code <password>}: Authenticate user as admin.</li>
 * </ul>
 * <p>
 * Server to Client Messages:
 * <ul>
 *     <li>LOBBY_CREATED {@code <lobby_id>}: Sent to the client after a new lobby has been created.
 *     Includes the ID of the new lobby.</li>
 *  <li>LOBBY_LIST {@code <lobbies>} : Sent to the client in response to a request for a list of
 *  available lobbies. Includes a list of available lobbies.</li>
 *  <li>LOBBY_JOINED: Sent to the client after successfully joining a lobby.</li>
 *  <li>LOBBY_LEFT: Sent to the client after successfully leaving a lobby.</li>
 *  <li>GAME_STARTING: Sent to the client when a game is starting.</li>
 *  <li>ROUND_STARTING {@code <round_number>}: Sent to the client when a new round is starting.
 *  Includes the number of the new round.</li>
 *  <li>ROUND_ENDED {@code <shrimp_price> <income> <expenses>}: Sent to the client when a round
 *  ends. Includes the price of shrimp, income, and expenses for the current round.</li>
 *  <li>COMMUNICATION_STARTING: Sent to the client when a communication round is starting.</li>
 *  <li>COMMUNICATION_ENDED: Sent to the client when a communication round is ending.</li>
 *  <li>SCOREBOARD {@code <round> <player1_shrimp> <player2_shrimp> <player3_shrimp>
 *      <total_shrimp> <income> <profit> <total_profit>} : Sent to the client when requested,
 *  providing the scoreboard for the specified round.</li>
 *  <li>RESULTS {@code <player1_score> <player2_score> <player3_score>}: Sent to the client after
 *  the game has ended, providing the final scores for all players.</li>
 *  <li>ERROR {@code <message>}: Sent to the client if an error occurs on the server.</li>
 *  <li>DISCONNECTED: Sent to the client if the server disconnects the client.</li>
 *  <li>BECOME_ADMIN_SUCCESSFUL: Sent to the client after successfully becoming an admin.</li>
 *  <li>BECOME_ADMIN_FAILED: Sent to the client if the password entered to become an admin is
 *  incorrect.</li>
 * </ul>
 */
public class ServerConnection
{
    private String hostname;
    private int port;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    /**
     * Constructs a new ServerConnection object with the specified IP address and port number.
     *
     * @param hostname the IP address of the server to connect to
     * @param port     the port number to connect to on the server
     */
    public ServerConnection(String hostname, int port)
    {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Establishes a connection with the server by creating a new socket and input/output streams.
     *
     * @throws IOException if there is an error creating the socket or streams
     */
    public void connect() throws IOException
    {
        if (!this.isConnected())
        {
            this.socket = new Socket();
            this.socket.connect(new InetSocketAddress(this.hostname, this.port), 5000);
            this.bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        }
    }

    /**
     * Closes the socket and input/output streams.
     *
     * @throws IOException if there is an error closing the socket or streams
     */
    public void disconnect() throws IOException
    {
        this.socket.close();
        this.bufferedWriter.close();
        this.bufferedReader.close();
    }

    /**
     * Sends a string message to the server through the output stream.
     *
     * @param message the message to send to the server
     * @throws IOException if there is an error sending the message
     */
    public void send(String message) throws IOException
    {
        bufferedWriter.write(message + "\r\n");
        bufferedWriter.flush();
    }

    /**
     * Receives a string message from the server through the input stream.
     *
     * @return the message received from the server
     * @throws IOException if there is an error receiving the message
     */
    public String receive()
    {
        String response = null;
        try
        {
            response = this.bufferedReader.readLine();
            return response;
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Failed to receive message from the server: " + exception);
        }
    }

    /**
     * Checks if the socket is connected to the server.
     *
     * @return true if the socket is connected to the server, false otherwise
     */
    public boolean isConnected()
    {
        boolean isConnected = false;
        if (this.socket != null)
        {
            isConnected = this.socket.isConnected();
        }
        return isConnected;
    }

    /**
     * Sends a request to the server to get a username for the client.
     * Waits for a response from the server, extracts the username from the response and returns it.
     *
     * @return the username that the server has assigned to the client
     * @throws RuntimeException if an error occurs while sending the request or processing the
     *                          response
     */
    public String sendUsernameRequest()
    {
        String username = "";
        try
        {
            this.send("REQUEST_USERNAME");
            String[] input = this.receive().split(" ");
            if (input[0].equals("USERNAME"))
            {
                username = input[1];
            }
            else
            {
                throw new RuntimeException("Unrecognized reply from the server: " + input[1]);
            }
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Failed to send REQUEST_USERNAME request");
        }
        return username;
    }

    public boolean sendBecomeAdminMessage(String password)
    {
        boolean sentBecomeAdminMessage = false;
        try
        {
            this.send("BECOME_ADMIN " + password);
            sentBecomeAdminMessage = true;
        }
        catch (IOException exception)
        {
            System.err.println("Failed to send become admin message: " + exception);
        }
        return sentBecomeAdminMessage;
    }

    /**
     * Sends a join lobby message to the server with the specified lobby ID.
     *
     * @param lobbyId the ID of the lobby to join
     * @throws IOException if there is an error sending the message
     */
    public void sendJoinLobbyMessage(String lobbyId) throws IOException
    {
        this.send("JOIN_LOBBY " + lobbyId);
    }

    /**
     * Sends a leave lobby message to the server.
     *
     * @throws IOException if there is an error sending the message
     */
    public void sendLeaveLobbyMessage() throws IOException
    {
        this.send("LEAVE_LOBBY");
    }

    /**
     * Sends a ready message to the server.
     *
     * @throws IOException if there is an error sending the message
     */
    public void sendReadyMessage() throws IOException
    {
        this.send("READY");
    }

    /**
     * Sends a choose amount message to the server with the specified amount.
     *
     * @param amount the amount of shrimp to fish for the current round
     * @throws IOException if there is an error sending the message
     */
    public void sendChooseAmountMessage(int amount) throws IOException
    {
        this.send("CHOOSE_AMOUNT " + amount);
    }

}
