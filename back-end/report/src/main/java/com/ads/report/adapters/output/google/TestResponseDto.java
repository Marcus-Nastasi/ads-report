package com.ads.report.adapters.output.google;

import java.util.List;

public record TestResponseDto(String status, List<String> accessible_accounts) {}
