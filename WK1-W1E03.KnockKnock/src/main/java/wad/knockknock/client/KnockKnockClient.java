package wad.knockknock.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class KnockKnockClient {
    private final int port;

    public KnockKnockClient(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        // Luodaan yhteys palvelimelle
        Socket socket = new Socket("localhost", port);

        Scanner serverMessageScanner = new Scanner(socket.getInputStream());
        PrintWriter clientMessageWriter = new PrintWriter(
                socket.getOutputStream(), true);

        Scanner userInputScanner = new Scanner(System.in);

        // Luetaan viestejä palvelimelta
        while (serverMessageScanner.hasNextLine()) {
            // 1. lue viesti palvelimelta
            String message = serverMessageScanner.nextLine();
            // 2. tulosta palvelimen viesti standarditulostusvirtaan näkyville
            System.out.println(message);

            // 3. jos palvelimen viesti loppuu merkkijonon "Bye.", poistu toistolausekkeesta
            if (message.endsWith("Bye.")) {
                break;
            }

            // 4. pyydä käyttäjältä palvelimelle lähetettävää viestiä
            System.out.println("Type a message to be sent to the server:");            
            // 5. kirjoita lähetettävä viesti palvelimelle. Huom! Käytä println-metodia.
            clientMessageWriter.println(userInputScanner.nextLine());
        }
    }
}
