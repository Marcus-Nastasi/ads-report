package com.ads.report.adapters.mappers;

import com.ads.report.adapters.output.google.TestResponseDto;

import java.util.List;

public class GoogleAdsDtoMapper {

    public TestResponseDto mapToResponse(List<String> googleAdsRows) {
        return new TestResponseDto(googleAdsRows.getFirst(), googleAdsRows.subList(1, googleAdsRows.size()));
    }
}
