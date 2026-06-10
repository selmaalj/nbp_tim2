package com.jobfair.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobfair.domain.service.XmlTransferService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.MultipartDocSample;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(ApiPaths.XML)
@ApiResourceDocumentation(order = 150, singularName = "xml transfer", pluralName = "xml transfers", sectionTitle = "XML", snippetPrefix = "xml-transfer", sampleId = "xml-transfer-1", description = "Import and export XML data.")
@Tag(name = "XML Import/Export")
public class XmlTransferController {

    private final XmlTransferService service;

    public XmlTransferController(XmlTransferService service) {
        this.service = service;
    }

    @GetMapping("/export")
        @EndpointDocumentation(order = 160, snippetId = "xml-transfer-export", displayName = "GET /xml/export", summary = "Export XML data", queryParameters = {
            @DocParameter(name = "mode", value = "TABLE"),
            @DocParameter(name = "target", value = "optional-target")
        }, binaryResponse = true)
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
        @EndpointDocumentation(order = 170, snippetId = "xml-transfer-import", displayName = "POST /xml/import", summary = "Import XML file", queryParameters = {
            @DocParameter(name = "mode", value = "TABLE"),
            @DocParameter(name = "target", value = "optional-target")
        }, multipart = @MultipartDocSample(enabled = true, fieldName = "file", fileName = "import.xml", contentType = MediaType.APPLICATION_XML_VALUE, content = "sample-xml"), expectedStatus = HttpStatus.OK)
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