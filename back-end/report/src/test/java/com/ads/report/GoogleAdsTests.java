package com.ads.report;

import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.manager.ManagerAccountInfo;
import com.ads.report.infrastructure.gateway.GoogleAdsRepoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GoogleAdsTests {

    @Mock
    private GoogleAdsRepoGateway googleAdsRepoGateway;
    @InjectMocks
    private GoogleAdsUseCase googleAdsUseCase;

    // Campaign metrics objects and lists
    CampaignMetrics campaignMetrics1 = new CampaignMetrics(
        "2025-01-12",
        "MONDAY",
        4232301839L,
        "campaignName",
        "adGroupName",
        "ENABLED",
        10000L,
        200L,
        22.4,
        4d,
        0.4d,
        0.12d,
        13.456d
    );
    CampaignMetrics campaignMetrics2 = new CampaignMetrics(
        "2025-01-13",
        "TUESDAY",
        3094808323L,
        "campaignName2",
        "adGroupName2",
        "ENABLED",
        30000L,
        600L,
        12.4,
        4d,
        0.05d,
        0.01d,
        12.456d
    );
    List<CampaignMetrics> campaignMetrics = List.of(campaignMetrics1, campaignMetrics2);

    // Manager account information object.
    ManagerAccountInfo managerAccountInfo = new ManagerAccountInfo(
        "id",
        "name",
        "currency",
        "timeZone",
        true,
        "status",
        true,
        true,
        "trackingUrlTemplate",
        "finalUrlSuffix",
        321897L,
        "conversionTrackingStatus"
    );

    /**
     *
     * Testing get campaigns.
     *
     */
    @Test
    void getCampaignMetrics() {
        // Mocking interface's method 'getCampaignMetrics' to return campaignMetrics global variable.
        when(googleAdsRepoGateway.getCampaignMetrics(anyString(), anyString(), anyString(), anyBoolean())).thenReturn(campaignMetrics);

        // Tests if the call throws an exception.
        assertDoesNotThrow(() -> googleAdsUseCase.getCampaignMetrics("1234", "2025-01-01", "2025-01-31", true));
        // Tests if the call's response for campaign id of the first object equals to the campaignMetrics1's id.
        assertEquals(
            campaignMetrics1.getCampaignId(),
            googleAdsUseCase.getCampaignMetrics("1234", "2025-01-01", "2025-01-31", true).getFirst().getCampaignId()
        );
        // Tests if the call's response for day of week of the second object equals to the campaignMetrics2's day of week.
        assertEquals(
            campaignMetrics2.getDayOfWeek(),
            googleAdsUseCase.getCampaignMetrics("1234", "2025-01-01", "2025-01-31", true).get(1).getDayOfWeek()
        );
        // Tests if response's campaign id of the first object is null.
        assertNotNull(googleAdsUseCase.getCampaignMetrics("1234", "2025-01-01", "2025-01-31", true).getFirst().getCampaignId());

        // Verifies how many times 'getCampaignMetrics' was called.
        verify(googleAdsRepoGateway, times(4)).getCampaignMetrics(anyString(), anyString(), anyString(), anyBoolean());
    }

    /**
     *
     * Testing 'testConnection'.
     *
     */
    @Test
    void testConnection() {
        // Mocking interface's method 'testConnection' to return a list of strings.
        when(googleAdsRepoGateway.testConnection()).thenReturn(List.of("status", "ok"));

        // Tests if the call throws an exception.
        assertDoesNotThrow(() -> googleAdsUseCase.testConnection());
        // Tests if the call's second index response equals to the 'ok'.
        assertEquals("ok", googleAdsUseCase.testConnection().get(1));
        // Tests if the call's first index response equals to the 'status'.
        assertEquals("status", googleAdsUseCase.testConnection().get(0));
        // Tests if response's campaign id of the first object is null.
        assertNotNull(googleAdsUseCase.testConnection());

        // Verifies how many times 'getCampaignMetrics' was called.
        verify(googleAdsRepoGateway, times(4)).testConnection();
    }

    /**
     *
     * Testing 'getManagerAccount' method.
     *
     */
    @Test
    void getManagerAccount() {
        // Mocking interface's method 'testConnection' to return a list of strings.
        when(googleAdsRepoGateway.getManagerAccount(anyString())).thenReturn(managerAccountInfo);

        // Tests if the call throws an exception.
        assertDoesNotThrow(() -> googleAdsUseCase.getManagerAccount("123"));
        // Tests if the call's second index response equals to the 'ok'.
        assertTrue(googleAdsUseCase.getManagerAccount("123").isManager());
        // Tests if the call's first index response equals to the 'status'.
        assertEquals(managerAccountInfo.getName(), googleAdsUseCase.getManagerAccount("123").getName());
        // Tests if response's campaign id of the first object is null.
        assertNotNull(googleAdsUseCase.getManagerAccount("123"));

        // Verifies how many times 'getCampaignMetrics' was called.
        verify(googleAdsRepoGateway, times(4)).getManagerAccount(anyString());
    }
}
