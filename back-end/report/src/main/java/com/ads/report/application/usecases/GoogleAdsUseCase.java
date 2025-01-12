package com.ads.report.application.usecases;

import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v17.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleAdsUseCase {

    @Autowired
    private GoogleAdsClient googleAdsClient;

    public List<GoogleAdsRow> getCampaignMetrics() throws RuntimeException {
        List<GoogleAdsRow> googleAdsRows = new ArrayList<>();
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            String query = """
                SELECT campaign.id, campaign.name, metrics.impressions, metrics.clicks, metrics.cost_micros
                FROM campaign
                WHERE segments.date DURING LAST_7_DAYS
            """;
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId("1585076333")
                .setQuery(query)
                .build();
            client.search(request).iterateAll().forEach(googleAdsRows::add);
            return googleAdsRows;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar métricas: " + e.getMessage());
        }
    }

    public List<String> testConnection() throws RuntimeException {
        final List<String> response = new ArrayList<>();
        try {
            // Cria o cliente do serviço CustomerService
            try (CustomerServiceClient customerServiceClient = googleAdsClient.getLatestVersion().createCustomerServiceClient()) {
                // Cria a requisição
                ListAccessibleCustomersRequest request = ListAccessibleCustomersRequest.newBuilder().build();

                // Chama a API para listar os clientes acessíveis
                ListAccessibleCustomersResponse listed = customerServiceClient.listAccessibleCustomers(request);

                // Verifica se há clientes retornados
                if (!listed.getResourceNamesList().isEmpty()) {
                    response.add("Conexão bem-sucedida. Contas acessíveis:");
                    response.addAll(listed.getResourceNamesList());
                } else {
                    response.add("Conexão estabelecida, mas nenhuma conta acessível encontrada.");
                }
                return response;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar a conexão com a MCC: " + e.getMessage());
        }
    }
}
