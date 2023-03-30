package org.example.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

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
     * Establishes a connection with the server using the provided hostname and port number.
     *
     * @throws RuntimeException if there is a failure to connect to the server
     */
    public void connect()
    {
        if (!this.isConnected())
        {
            try
            {
                this.socket = new Socket();
                this.socket.connect(new InetSocketAddress(this.hostname, this.port), 5000);
                this.bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
                this.bufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            }
            catch (IOException exception)
            {
                throw new RuntimeException("Failed to connect to the server.");
            }
        }
    }

    /**
     * Closes the connection with the server.
     *
     * @throws RuntimeException if there is a failure to close the connection with the server
     */
    public void disconnect()
    {
        try
        {
            this.socket.close();
            this.bufferedWriter.close();
            this.bufferedReader.close();
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Failed to close connection with the server.");
        }

    }

    /**
     * Sends a message to the server through the established connection.
     *
     * @param message the message to be sent
     * @throws RuntimeException if there is a failure to send the message to the server
     */
    public void send(String message)
    {
        try
        {
            bufferedWriter.write(message + "\r\n");
            bufferedWriter.flush();
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Failed to send message to the server.");
        }
    }

    /**
     * Receives a message from the server through the established connection.
     *
     * @return the message received from the server
     * @throws RuntimeException if there is a failure to receive the message from the server
     */
    public String receive()
    {
        try
        {
            return this.bufferedReader.readLine();
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Failed to receive message from the server.");
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
     * Sends a request to the server for the username associated with the current client.
     *
     * @return the username associated with the current client
     * @throws RuntimeException if there is a failure to send the username request to the server
     *                          or if an unrecognized reply is received from the server
     */
    public String[] sendUsernameRequest()
    {
        String[] username = new String[2];
        try
        {
            this.send("REQUEST_USERNAME");
            String[] input = this.receive().split(" ");
            if (input[0].equals("USERNAME"))
            {
                username[0] = input[1];
                username[1] = input[2];
            }
            else
            {
                throw new RuntimeException(
                    "Unrecognized reply from the server: " + Arrays.toString(input));
            }
        }
        catch (RuntimeException exception)
        {
            throw new RuntimeException("Failed to send username request to the server.");
        }
        return username;
    }

    /**
     * Sends a request to the server to become an administrator using the provided password.
     *
     * @param password the password required to become an administrator
     * @throws RuntimeException if there is a failure to send the become admin request to the server
     */
    public void sendBecomeAdminRequest(String password)
    {
        try
        {
            this.send("BECOME_ADMIN " + password);
        }
        catch (RuntimeException exception)
        {
            throw new RuntimeException("Failed to send become admin request to the server.");
        }
    }

    /**
     * Sends a request to the server to create a new lobby with the provided parameters.
     *
     * @param lobbyName       the name of the lobby to be created
     * @param numPlayers      the maximum number of players allowed in the lobby
     * @param numRounds       the number of rounds to be played in the lobby
     * @param roundTime       the time limit in seconds for each round
     * @param minShrimpPounds the minimum amount of shrimp pounds required to win a round
     * @param maxShrimpPounds the maximum amount of shrimp pounds required to win a round
     * @throws RuntimeException if there is a failure to send the create lobby request to the server
     */
    public void sendCreateLobbyRequest(String lobbyName, int numPlayers, int numRounds,
                                       int roundTime, int minShrimpPounds, int maxShrimpPounds)
    {
        try
        {
            this.send(
                "CREATE_LOBBY " + lobbyName + " " + numPlayers + " " + numRounds + " " + roundTime
                + " " + minShrimpPounds + " " + maxShrimpPounds);
        }
        catch (RuntimeException exception)
        {
            throw new RuntimeException("Failed to send create lobby request to the server.");
        }
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
