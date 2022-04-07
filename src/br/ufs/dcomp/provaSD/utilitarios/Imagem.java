package br.ufs.dcomp.provaSD.utilitarios;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Imagem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private byte[] conteudo;
	
	public Imagem(String nome, byte[] conteudo) {
		this.nome = nome;
		this.conteudo = conteudo;
	}
	
	public static byte[] toByteArray(Imagem imagem) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out;
		out = new ObjectOutputStream(bos);
		out.writeObject(imagem);
		out.flush();
		byte[] retorno = bos.toByteArray();
		bos.close();
		
		return retorno;
	}
	
	public static Imagem toImagem(byte[] imagemBytes) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(imagemBytes);
		ObjectInputStream in = new ObjectInputStream(bis);
		Imagem imagem = (Imagem) in.readObject();
		in.close();
		return imagem;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public byte[] getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}
}
