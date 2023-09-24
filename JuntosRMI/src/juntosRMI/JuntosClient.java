package juntosRMI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class JuntosClient extends UnicastRemoteObject implements JuntosClientInterface {
	
	static JuntosInterface servidor;
	static JuntosClient cliente;
	static char letraAtual;

    protected JuntosClient() throws RemoteException {
    }

    public static void main(String[] args) {
        try {
            servidor = (JuntosInterface) Naming.lookup("rmi://localhost/Juntos");
            cliente = new JuntosClient();
            servidor.conectar(cliente);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void receberMensagem(String mensagem) throws RemoteException {
        System.out.println(mensagem);
    }

    @Override
    public void receberLetra(char letra) throws RemoteException {
    	System.out.println("A letra é: " + letra);
    	letraAtual = letra;
    	this.enviarPalavra();
    }
    
    private void enviarPalavra() throws RemoteException {
    	while(true) {
	    	System.out.println("Informe a palavra:");
	    	Scanner sc = new Scanner(System.in);
	    	String palavra = sc.next().toUpperCase();
	    	if (palavra.startsWith(String.valueOf(letraAtual))) {
	    		servidor.enviarPalavra(palavra, cliente);
	    		return;
	    	}
	    	System.out.println("Erro! A palavra deve iniciar com a letra " + letraAtual);
    	}
    }
}
