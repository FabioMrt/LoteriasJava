package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import application.Main;
import entities.Jogo;
import entities.enums.KeyJogo;

public class DadosJogos {

	private KeyJogo jogoKey;

	public DadosJogos(KeyJogo jogoKey) {
		this.jogoKey = jogoKey;
	}

	public void topFive() {

		Map<String, Integer> numeroOcorrencia = processaDados();

		Stream<Map.Entry<String, Integer>> top5 = numeroOcorrencia.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(5);

		System.out.println("5 numeros que mais sairam na " + jogoKey + " (numero - ocorrencia): ");
		top5.forEach(System.out::println);

	}

	public void lastFive() {

		Map<String, Integer> numeroOcorrencia = processaDados();

		Stream<Map.Entry<String, Integer>> ultimos5 = numeroOcorrencia.entrySet().stream()
				.sorted(Map.Entry.comparingByValue()).limit(5);

		System.out.println("5 numeros que menos sairam na " + jogoKey + " (numero - ocorrencia): ");
		ultimos5.forEach(System.out::println);
	}

	private Map<String, Integer> processaDados() {
		Map<String, Integer> numeroOcorrencia = new HashMap<String, Integer>();

		for (Jogo jogo : Main.jogosMap.get(jogoKey)) {
			for (String jogosNumeros : jogo.getNumeros()) {
				if (numeroOcorrencia.containsKey(jogosNumeros)) {
					numeroOcorrencia.put(jogosNumeros, numeroOcorrencia.get(jogosNumeros) + 1);
				} else {
					numeroOcorrencia.put(jogosNumeros, 1);
				}
			}
		}

		return numeroOcorrencia;
	}

	public void gerarRandomico() {

		List<String> numeros = new ArrayList<String>();

		for (int i = 0; i < Main.jogosMap.get(jogoKey).get(0).getNumeros().size(); i++) {
			String numero = Integer.toString(Main.jogosNumeros.get(jogoKey)[new Random()
					.nextInt((Main.jogosNumeros.get(jogoKey).length - 1) + 1)]);
			if (numero.length() == 1) {
				numero = "0" + numero;
			}
			numeros.add(numero);
		}

		realizarJogo(numeros);
	}

	public void realizarJogo(List<String> numeros) {

		Integer totalNumerosAcertados = 0;

		for (Jogo jogo : Main.jogosMap.get(jogoKey)) {
			Map<String, Integer> numeroOcorrencia = new HashMap<String, Integer>();
			for (String numero : jogo.getNumeros()) {
				for (int i = 0; i < numeros.size(); i++) {
					if (numero.equals(numeros.get(i))) {
						totalNumerosAcertados += 1;
						if (numeroOcorrencia.containsKey(numero)) {
							numeroOcorrencia.put(numero, numeroOcorrencia.get(numero) + 1);
						} else {
							numeroOcorrencia.put(numero, 1);
						}
					}
				}
			}
			if (!numeroOcorrencia.isEmpty()) {
				System.out.println(
						"================================================================================================");
				System.out.println("\n\nJogo que o usuario acertaria numeros:\n");
				System.out.println(jogo);
				System.out.println("\nNumero e repeticoes do numero no jogo:");
				System.out.println(numeroOcorrencia);
				int totalAcertoJogo = 0;
				for (Integer n : numeroOcorrencia.values()) {
					totalAcertoJogo += n;
				}
				System.out.println("\nNumero total de acertos no jogo: " + totalAcertoJogo);
				System.out.println(
						"\n\n================================================================================================");
			}
		}
		System.out.println("\n\nTotal de acertos:");
		System.out.println(totalNumerosAcertados);
		System.out.println("Numeros jogados: " + numeros);
		System.out.println(
				"\n\n================================================================================================");

	}

}
