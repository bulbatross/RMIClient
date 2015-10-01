package client; /**
 * Created by bulbatross on 2015-09-30.
 */
import common.ActionHandler;
import server.RunServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.*;
import java.rmi.server.*;


public class Client extends UnicastRemoteObject implements Notifiable
{
    static ActionHandler actionHandler = null; // Reference to remote server object


    public Client(ActionHandler server) throws RemoteException {
        super();
        this.actionHandler = server;
    }




    public static void main(String [] args) {
        //if(args.length != 1) {
          //  System.out.println(
            //        "usage: java Client <server_host>");
            //System.exit(0);
        //}

        try {
            //String url = "rmi://" + args[0] + "/ActionHandler"; //RunServer should implement ActionHandler
            actionHandler =
                    (ActionHandler) Naming.lookup("rmi://127.0.0.1/ActonHandler");
            Client client = new Client(actionHandler);

	       /* Register for callbacks at the auction server.
	        */
            //server.registerClient(client);

            client.runClient();
        }
        catch (NotBoundException nbe) {
            System.out.println(nbe.toString());
            System.out.println("ActionHandler is not available");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void runClient() throws RemoteException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        System.out.print("input: ");
        while (actionHandler!=null)
        {

            try {
                if(stdIn.ready()) {

                    userInput = stdIn.readLine();
                    if(userInput != null){
                        if (userInput.equals("/quit"))
                            break;
                        else {
                            if(userInput.equals("/help"))
                                System.out.println(actionHandler.getHelp());
                            //server.putMsgFromClient(userInput); //this server method should be synchronized
                            else
                                System.out.println(actionHandler.sendMessage());

                            System.out.print("input: ");
                        }


                    }
                }
            } catch (IOException e) {
                System.out.println("server closed");
                e.printStackTrace();
                break;
            }
        }

        System.out.println("Exiting...");
        // Important: Deregister for notifiation from server!
        //server.deRegisterClient(this);
        // Terminate the thread associated with rmi calls
        System.exit(0);
    }
    /* This method is used by the server to "call back"
     * to update clients. */
    @Override
    public void notifyNewMessage(String msg) throws RemoteException {
        System.out.println("echo: " + msg);
    }
}