package org.example.sistema_gestion_vitalexa.dto;

public record ReportDTO(
        SalesReportDTO salesReport,
        ProductReportDTO productReport,
        VendorReportDTO vendorReport,
        ClientReportDTO clientReport
) {
}
