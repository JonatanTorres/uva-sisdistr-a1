package juntosRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JuntosInterface extends Remote {
    void conectar(JuntosClientInterface cliente) throws RemoteException;
    void enviarPalavra(String palavra, JuntosClientInterface cliente) throws RemoteException;
}