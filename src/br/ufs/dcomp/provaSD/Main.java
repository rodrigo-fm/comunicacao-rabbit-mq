package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import br.ufs.dcomp.provaSD.utilitarios.EnviarImagemColorida;

/*
 * OBS: � necess�rio criar o diret�rio "C:\imagens-sd" antes de inicializar a aplica��o,
 * junto com os outros diret�rios necess�rios, explicados nos coment�rios abaixo.
 */

public class Main {
	
	// para cada cliente, crie uma pasta "cliente-{numero}" no diret�rio C:\imagens-sd\
	// Ex: cliente-1, cliente-2. At� chegar na quantidade de Clientes escolhida
	// Coloque fotos distintas em cada um dos diret�rios dos clientes
	private final static int quantidadeClientes = 2;
	
	// para cada servidor, crie uma pasta "servidor-{numero}" no diret�rio C:\imagens-sd\
	// Ex: servidor-1, servidor-2. At� chegar na quantidade de servidores escolhida
	private final static int quantidadeServidores = 2;
	
	// N�o precisa criar nenhuma pasta para os conversores
	private final static int quantidadeConversores = 3;
	
	public static void main(String[] args) throws Exception {
		// Inicializa��o das Threads contendo a l�gica para armazenar as imagens de forma redundante no servidor
		for(int i = 1; i <= quantidadeServidores; i++) {
			Thread servidorArmazenamento = new Thread(new ThreadServidorArmazenamento("servidor-" + i));
			servidorArmazenamento.start();
		}
		
		// Apenas para impedir que as Threads iniciem fora de ordem
		Thread.sleep(1000);
		
		// Inicializa��o das Threads contendo a l�gica para converter uma imagem e enviar
		// via fanout para os servidores respons�veis por armazenar
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
				System.out.println("Nenhuma imagem encontrada no diret�rio C:\\imagens-sd\\cliente-" + i);
			} else {
				// Envio das imagens coloridas atrav�s de uma fila �nica
				System.out.println("Enviando imagens do diret�rio cliente-" + i);
				Thread clienteThread = new Thread(new ThreadEnviarImagemColorida(directoryListing)); 
				clienteThread.start();	
			}
		}
	}
}
