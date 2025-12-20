package org.example.sistema_gestion_vitalexa.service;

import org.example.sistema_gestion_vitalexa.dto.*;
import java.time.LocalDate;

public interface ReportService {
    ReportDTO getCompleteReport(LocalDate startDate, LocalDate endDate);
    SalesReportDTO getSalesReport(LocalDate startDate, LocalDate endDate);
    ProductReportDTO getProductReport();
    VendorReportDTO getVendorReport(LocalDate startDate, LocalDate endDate);
    ClientReportDTO getClientReport();
}
