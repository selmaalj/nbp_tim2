package com.jobfair.application.serviceImpl;

import com.jobfair.domain.service.XmlTransferService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.sql.Clob;

@Service
public class XmlTransferServiceImpl implements XmlTransferService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public String exportXml(String mode, String target) {
        return entityManager.unwrap(Session.class).doReturningWork(connection -> {
            try (CallableStatement stmt = connection.prepareCall(
                    "{ call NBPT2.XML_TRANSFER_PKG.EXPORT_XML(?, ?, ?) }"
            )) {
                stmt.setString(1, mode);
                stmt.setString(2, target);
                stmt.registerOutParameter(3, java.sql.Types.CLOB);
                stmt.execute();

                Clob clob = stmt.getClob(3);
                return clob == null ? "" : clob.getSubString(1, (int) clob.length());
            }
        });
    }

    @Override
    @Transactional
    public void importXml(String mode, String target, String xml) {
        entityManager.unwrap(Session.class).doWork(connection -> {
            try (CallableStatement stmt = connection.prepareCall(
                    "{ call NBPT2.XML_TRANSFER_PKG.IMPORT_XML(?, ?, ?) }"
            )) {
                Clob clob = connection.createClob();
                clob.setString(1, xml);

                stmt.setString(1, mode);
                stmt.setString(2, target);
                stmt.setClob(3, clob);
                stmt.execute();
            }
        });
    }
}