package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entities.Jogo;
import entities.enums.KeyJogo;
import view.SubMenu;

public class Main {

	public static final int randLoto = 25;
	public static final int randMega = 60;
	public static final int randQuina = 80;

	public static Map<KeyJogo, int[]> jogosNumeros = new HashMap<KeyJogo, int[]>();

	public static Map<KeyJogo, List<Jogo>> jogosMap = new HashMap<KeyJogo, List<Jogo>>();
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		boolean continuar = true;
		instanciaJogos();

		do {
			System.out.println("\n\n1 - Mega" + "\n2 - Quina" + "\n3 - Loto" + "\n9 - Sair" + "\n\nDigite sua opção:");
			switch (sc.nextInt()) {
			case 1:
				SubMenu.submenu(KeyJogo.MEGA);
				break;

			case 2:
				SubMenu.submenu(KeyJogo.QUINA);
				break;

			case 3:
				SubMenu.submenu(KeyJogo.LOTO);
				break;
			case 9:
				continuar = false;
				break;

			default:
				System.out.println("Não encontrado");
			}
		} while (continuar);

		sc.close();
	}

	public static void instanciaJogos() {

		String[] jogosTipos = { "LOTO", "MEGA", "QUINA" };
		int[] numerosPossiveis = { randLoto, randMega, randQuina };

		for (int i = 0; i < 3; i++) {
			int[] modelo = new int[numerosPossiveis[i]];
			for (int j = 0; j < numerosPossiveis[i]; j++) {
				modelo[j] = j + 1;
			}
			jogosNumeros.put(KeyJogo.valueOf(jogosTipos[i]), modelo);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String caminho = System.getProperty("user.dir");
		String[] nomesArquivos = { "\\lotofacil.csv", "\\mega.csv", "\\quina.csv" };

		for (int i = 0; i < nomesArquivos.length; i++) {
			jogosMap.put(KeyJogo.valueOf(jogosTipos[i]), new ArrayList<Jogo>());
			try (BufferedReader br = new BufferedReader(new FileReader(caminho + nomesArquivos[i]))) {

				String linhaAtual = br.readLine();

				while (linhaAtual != null) {

					Jogo jogo = new Jogo();

					String[] dadosDaLinhaSeparados = linhaAtual.split(",");

					jogo.setId(Long.parseLong(dadosDaLinhaSeparados[0]));
					try {
						jogo.setDate(sdf.parse(dadosDaLinhaSeparados[1]));
					} catch (ParseException e) {
						e.printStackTrace();
					}

					for (int j = 2; j < dadosDaLinhaSeparados.length; j++) {
						jogo.addNumber(dadosDaLinhaSeparados[j]);
					}

					jogosMap.get(KeyJogo.valueOf(jogosTipos[i])).add(jogo);

					linhaAtual = br.readLine();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
