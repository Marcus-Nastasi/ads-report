package com.ads.report;

import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v17.resources.Customer;
import com.google.ads.googleads.v17.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.net.URL;

@SpringBootApplication
public class ReportApplication {

	public static void main(String[] args) {
//		try {
//			GoogleAdsClient googleAdsClient = GoogleAdsClient.newBuilder()
//					.fromPropertiesFile(new File("ads.properties"))
//					.build();

//			URL resource = ReportApplication.class.getClassLoader().getResource("ads.properties");
//			if (resource == null) throw new IllegalArgumentException("Arquivo ads.properties não encontrado no classpath");
//			GoogleAdsClient googleAdsClient = GoogleAdsClient.newBuilder()
//					.fromPropertiesFile(new File(resource.getFile()))
//					.setLoginCustomerId(1585076333L)
//					.build();
//
//			try (CustomerServiceClient customerServiceClient = googleAdsClient.getLatestVersion().createCustomerServiceClient()) {
//				Customer testCustomer = Customer.newBuilder()
//						.setDescriptiveName("Test Account " + System.currentTimeMillis())
//						.setCurrencyCode("BRL")
//						.setTimeZone("America/Sao_Paulo")
//						.setTestAccount(true) // Define como conta de teste
//						.build();
//
//				CustomerOperation operation = CustomerOperation.newBuilder().setUpdate(testCustomer).build();
//				MutateCustomerRequest request = MutateCustomerRequest.newBuilder().setOperation(operation).build();
//
//				MutateCustomerResponse response = customerServiceClient.mutateCustomer(request);
//				System.out.printf("Conta de teste criada com sucesso: %s%n", response.getResult().getResourceName());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			// Inicializa o cliente do Google Ads a partir do arquivo de propriedades
//			URL resource = ReportApplication.class.getClassLoader().getResource("ads.properties");
//			if (resource == null) throw new IllegalArgumentException("Arquivo ads.properties não encontrado no classpath");
//			GoogleAdsClient googleAdsClient = GoogleAdsClient.newBuilder()
//					.fromPropertiesFile(new File(resource.getFile()))
//					.build();
//
//			// Cria o cliente do serviço CustomerService
//			try (CustomerServiceClient customerServiceClient = googleAdsClient.getLatestVersion().createCustomerServiceClient()) {
//				// Cria a requisição
//				ListAccessibleCustomersRequest request = ListAccessibleCustomersRequest.newBuilder().build();
//
//				// Chama a API para listar os clientes acessíveis
//				ListAccessibleCustomersResponse response = customerServiceClient.listAccessibleCustomers(request);
//
//				// Verifica se há clientes retornados
//				if (!response.getResourceNamesList().isEmpty()) {
//					System.out.println("Conexão bem-sucedida. Contas acessíveis:");
//					response.getResourceNamesList().forEach(System.out::println);
//				} else {
//					System.out.println("Conexão estabelecida, mas nenhuma conta acessível encontrada.");
//				}
//			}
//		} catch (Exception e) {
//			System.err.println("Erro ao verificar a conexão com a MCC: " + e.getMessage());
//			e.printStackTrace();
//		}
		SpringApplication.run(ReportApplication.class, args);
	}
}
