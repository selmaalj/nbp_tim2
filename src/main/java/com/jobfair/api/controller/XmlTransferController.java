package com.jobfair.api.controller;

import com.jobfair.domain.service.XmlTransferService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiPaths.XML)
@Tag(name = "XML Import/Export")
public class XmlTransferController {

    private final XmlTransferService service;

    public XmlTransferController(XmlTransferService service) {
        this.service = service;
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportXml(
            @RequestParam String mode,     // TABLE, MODULE, ALL
            @RequestParam(required = false) String target
    ) {
        String xml = service.exportXml(mode, target);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"jobfair_export_" + mode.toLowerCase() + ".xml\"")
                .contentType(MediaType.APPLICATION_XML)
                .body(xml.getBytes());
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importXml(
            @RequestParam String mode,
            @RequestParam(required = false) String target,
            @RequestPart MultipartFile file
    ) throws Exception {
        String xml = new String(file.getBytes());
        service.importXml(mode, target, xml);
        return ResponseEntity.ok("XML import uspješno završen.");
    }
}