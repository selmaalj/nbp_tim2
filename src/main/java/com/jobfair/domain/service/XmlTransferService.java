package com.jobfair.domain.service;

public interface XmlTransferService {
    String exportXml(String mode, String target);
    void importXml(String mode, String target, String xml);
}