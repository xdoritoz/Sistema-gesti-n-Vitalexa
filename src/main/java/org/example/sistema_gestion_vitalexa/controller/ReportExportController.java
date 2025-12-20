package org.example.sistema_gestion_vitalexa.controller;

import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.dto.*;
import org.example.sistema_gestion_vitalexa.service.ReportExportService;
import org.example.sistema_gestion_vitalexa.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports/export")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportExportController {

    private final ReportService reportService;
    private final ReportExportService exportService;

    // =============================================
    // EXPORTACIONES DE REPORTES GENERALES (ADMIN)
    // =============================================

    @GetMapping("/complete/pdf")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportCompleteReportPdf(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        ReportDTO report = reportService.getCompleteReport(startDate, endDate);
        byte[] pdfBytes = exportService.exportReportToPdf(report, startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment",
                "reporte_completo_" + LocalDate.now() + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/complete/excel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportCompleteReportExcel(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        ReportDTO report = reportService.getCompleteReport(startDate, endDate);
        byte[] excelBytes = exportService.exportReportToExcel(report, startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment",
                "reporte_completo_" + LocalDate.now() + ".xlsx");

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/complete/csv")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportCompleteReportCsv(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        ReportDTO report = reportService.getCompleteReport(startDate, endDate);
        byte[] csvBytes = exportService.exportReportToCsv(report, startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment",
                "reporte_completo_" + LocalDate.now() + ".csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    // =============================================
    // EXPORTACIONES ESPECÍFICAS POR ÁREA
    // =============================================

    @GetMapping("/sales/pdf")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportSalesReportPdf(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        byte[] pdfBytes = exportService.exportSalesReportToPdf(startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment",
                "reporte_ventas_" + LocalDate.now() + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/products/excel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportProductReportExcel() {
        byte[] excelBytes = exportService.exportProductReportToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment",
                "reporte_productos_" + LocalDate.now() + ".xlsx");

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/clients/csv")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportClientReportCsv() {
        byte[] csvBytes = exportService.exportClientReportToCsv();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment",
                "reporte_clientes_" + LocalDate.now() + ".csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}
