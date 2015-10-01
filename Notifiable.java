package client; /**
 * Created by bulbatross on 2015-09-30.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Notifiable extends Remote {

    public void notifyNewMessage(String msg) throws RemoteException;
}