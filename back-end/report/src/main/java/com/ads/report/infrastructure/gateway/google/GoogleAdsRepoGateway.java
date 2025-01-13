package com.ads.report.infrastructure.gateway.google;

import com.ads.report.application.gateway.google.GoogleAdsGateway;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v17.services.CustomerServiceClient;
import com.google.ads.googleads.v17.services.ListAccessibleCustomersRequest;
import com.google.ads.googleads.v17.services.ListAccessibleCustomersResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GoogleAdsRepoGateway implements GoogleAdsGateway {

    @Autowired
    private GoogleAdsClient googleAdsClient;

    @Override
    public List<String> testConnection() throws RuntimeException {
        final List<String> response = new ArrayList<>();
//        try {
            // Create the CustomerService client
        try (CustomerServiceClient customerServiceClient = googleAdsClient.getLatestVersion().createCustomerServiceClient()) {
            // Creates request
            ListAccessibleCustomersRequest request = ListAccessibleCustomersRequest.newBuilder().build();

            // Call the API to list the accessible clients
            ListAccessibleCustomersResponse listed = customerServiceClient.listAccessibleCustomers(request);

            response.add("Successful connection.");
            response.addAll(listed.getResourceNamesList());
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error while connecting with MCC: " + e.getMessage());
        }
    }

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
}
