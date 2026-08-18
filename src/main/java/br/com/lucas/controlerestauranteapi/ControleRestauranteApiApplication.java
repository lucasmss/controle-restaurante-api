package br.com.lucas.controlerestauranteapi;

import br.com.lucas.controlerestauranteapi.entity.Mesa;
import br.com.lucas.controlerestauranteapi.repository.MesaRepository;
import br.com.lucas.controlerestauranteapi.service.MesaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ControleRestauranteApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(ControleRestauranteApiApplication.class, args);

//		Scanner sc = new Scanner(System.in);
//		String resposta;
//
//		System.out.println("Bem-vindo/os Deseja escolher uma mesa?");
//		resposta = sc.next();
//
//		System.out.println(resposta);
//
//		if(resposta.equals("sim")){
//			System.out.println("Mesas disponíveis: " );
//		}

	}
}
