package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import br.ufs.dcomp.provaSD.utilitarios.EnviarImagemColorida;

/*
 * OBS: É necessário criar o diretório "C:\imagens-sd" antes de inicializar a aplicação,
 * junto com os outros diretórios necessários, explicados nos comentários abaixo.
 */

public class Main {
	
	// para cada cliente, crie uma pasta "cliente-{numero}" no diretório C:\imagens-sd\
	// Ex: cliente-1, cliente-2. Até chegar na quantidade de Clientes escolhida
	// Coloque fotos distintas em cada um dos diretórios dos clientes
	private final static int quantidadeClientes = 2;
	
	// para cada servidor, crie uma pasta "servidor-{numero}" no diretório C:\imagens-sd\
	// Ex: servidor-1, servidor-2. Até chegar na quantidade de servidores escolhida
	private final static int quantidadeServidores = 2;
	
	// Não precisa criar nenhuma pasta para os conversores
	private final static int quantidadeConversores = 3;
	
	public static void main(String[] args) throws Exception {
		// Inicialização das Threads contendo a lógica para armazenar as imagens de forma redundante no servidor
		for(int i = 1; i <= quantidadeServidores; i++) {
			Thread servidorArmazenamento = new Thread(new ThreadServidorArmazenamento("servidor-" + i));
			servidorArmazenamento.start();
		}
		
		// Apenas para impedir que as Threads iniciem fora de ordem
		Thread.sleep(1000);
		
		// Inicialização das Threads contendo a lógica para converter uma imagem e enviar
		// via fanout para os servidores responsáveis por armazenar
		for(int i = 1; i <= quantidadeConversores; i++) {
			Thread conversor = new Thread(new ThreadProcessarImagem("cliente-" + i));
			conversor.start();
		}

		// Apenas para impedir que as Threads iniciem fora de ordem
		Thread.sleep(1000);
		
		for(int i = 1; i <= quantidadeClientes; i++) {
			File diretorio = new File("C:\\imagens-sd\\cliente-"+ i +"\\");
			File[] directoryListing = diretorio.listFiles();
			
			if(directoryListing == null || directoryListing.length == 0) {
				System.out.println("Nenhuma imagem encontrada no diretório C:\\imagens-sd\\cliente-" + i);
			} else {
				// Envio das imagens coloridas através de uma fila única
				System.out.println("Enviando imagens do diretório cliente-" + i);
				Thread clienteThread = new Thread(new ThreadEnviarImagemColorida(directoryListing)); 
				clienteThread.start();	
			}
		}
	}
}
