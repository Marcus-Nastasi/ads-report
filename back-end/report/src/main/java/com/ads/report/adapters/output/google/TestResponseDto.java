package com.ads.report.adapters.output.google;

import java.io.Serializable;
import java.util.List;

public record TestResponseDto(String status, List<String> accessible_accounts) implements Serializable {}
