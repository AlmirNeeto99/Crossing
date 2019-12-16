package Model;

import java.net.Socket;
import java.util.List;

/**
 *
 * @author Almir
 */
public class Client {
    
    private Socket socket;
    
    public void connect(){
        
    }
    
    public void notify_all(List<Client> clients){
        for(Client client : clients){
            client.send();
        }
    }
    
    public void send(){}
    
}
