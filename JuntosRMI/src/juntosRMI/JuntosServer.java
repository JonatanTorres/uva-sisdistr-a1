package juntosRMI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class JuntosServer {

    public static void main(String[] args) {
        try {
            // Iniciar o registro RMI na porta 1099
            LocateRegistry.createRegistry(1099);

            // Criar uma instância da implementação da interface Juntos
            JuntosImpl juntosImpl = new JuntosImpl();

            // Registrar a implementação no registro RMI com um nome
            Naming.rebind("Juntos", juntosImpl);

            System.out.println("Servidor do Jogo 'Juntos' está ativo.");
        } catch (RemoteException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
