package org.example.sistema_gestion_vitalexa.service;

import org.example.sistema_gestion_vitalexa.dto.ReportDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface ReportExportService {

    byte[] exportReportToPdf(ReportDTO report, LocalDate startDate, LocalDate endDate);

    byte[] exportReportToExcel(ReportDTO report, LocalDate startDate, LocalDate endDate);

    byte[] exportReportToCsv(ReportDTO report, LocalDate startDate, LocalDate endDate);

    // Exportaciones específicas
    byte[] exportSalesReportToPdf(LocalDate startDate, LocalDate endDate);

    byte[] exportProductReportToExcel();

    byte[] exportClientReportToCsv();
}
