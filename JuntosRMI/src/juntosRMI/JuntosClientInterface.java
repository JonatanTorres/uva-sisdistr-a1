package juntosRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JuntosClientInterface extends Remote {
    void receberMensagem(String mensagem) throws RemoteException;
    void receberLetra(char letra) throws RemoteException;
}