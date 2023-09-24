package juntosRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JuntosImpl extends UnicastRemoteObject implements JuntosInterface {

	private JuntosClientInterface jogador1;
    private JuntosClientInterface jogador2;
    private char letraAtual;
    private String palavra1;
    private String palavra2;
    private ExecutorService executorService;

    public JuntosImpl() throws RemoteException {
        super();
        jogador1 = null;
        jogador2 = null;
        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public void conectar(JuntosClientInterface cliente) throws RemoteException {
    	if (jogador1 == null) {
            jogador1 = cliente;
            enviarMensagem("Você é o jogador 1. Aguardando o jogador 2...", jogador1);
        } else if (jogador2 == null) {
            jogador2 = cliente;
            enviarMensagem("Você é o jogador 2.", jogador2);
            enviarMensagemTodos("Iniciando partida...");
            gerarLetra();
            iniciarJogoAsync(jogador1);
            iniciarJogoAsync(jogador2);
        } else {
            // Número máximo de jogadores superado
            cliente.receberMensagem("O jogo já está em andamento. Aguarde sua vez.");
        }
    }
    
    @Override
    public void enviarPalavra(String palavra, JuntosClientInterface cliente) throws RemoteException {
    	System.out.println("Recebi a palavra " + palavra);
    	if (palavra1 == null) {
    		palavra1 = palavra;
    		enviarMensagem("Aguardando o outro jogador...", cliente);
    	} else {
    		palavra2 = palavra;
    		gerarResultado();
    	}
    }

	private void iniciarJogoAsync(JuntosClientInterface cliente) throws RemoteException {
		executorService.execute(() -> {
			try {
	    		enviarMensagem("O jogo começou!", cliente);
	    		enviarLetra(cliente);
			} catch (RemoteException e) {
	            e.printStackTrace();
	        }
		});
	}
	
	private void gerarLetra() throws RemoteException {
		// Gerar uma letra aleatória de 'A' a 'Z'
        Random random = new Random();
        char letraAleatoria = (char) (random.nextInt(26) + 'A');
        letraAtual = letraAleatoria;
	}
    
    private void enviarLetra(JuntosClientInterface cliente) throws RemoteException {
        // Notificar todos os jogadores sobre a letra gerada
        cliente.receberLetra(letraAtual);
    }
    
    private void gerarResultado()  throws RemoteException {
    	System.out.println("palavra1: " + palavra1);
    	System.out.println("palavra2: " + palavra1);
    	if (palavra1.equals(palavra2)) {
    		enviarMensagemTodos("Parabéns, vocês venceram! A palavra é: " + palavra1);
    		enviarMensagemTodos("Obrigado por jogar!");
    	} else {
    		enviarMensagemTodos("Vocês perderam. As palavras não coincidem!");
    	}
    	
    }

    private void enviarMensagem(String mensagem, JuntosClientInterface cliente) throws RemoteException {
        cliente.receberMensagem(mensagem);
    }
    
    private void enviarMensagemTodos(String mensagem) throws RemoteException {
    	this.jogador1.receberMensagem(mensagem);
    	this.jogador2.receberMensagem(mensagem);
    }

}
