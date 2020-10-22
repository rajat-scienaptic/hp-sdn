package com.sdn.repository.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author RAJAT MEENA
 */
@Service
public class CustomRepositoryImpl implements CustomRepository {
    @Value("${scmgQuery}")
    protected String scmgQuery;
    @Value("${scmmQuery}")
    protected String scmmQuery;
    @Value("${stmmQuery}")
    protected String stmmQuery;
    @Value("${stmwQuery}")
    protected String stmwQuery;
    @Value("${updateSCMGSkuTypesQuery}")
    protected String updateSCMGSkuTypesQuery;
    @Value("${updateSCMMSkuTypesQuery}")
    protected String updateSCMMSkuTypesQuery;

    @PersistenceContext
    @Autowired
    protected EntityManager em;

    @Override
    @Transactional
    @Modifying
    public void pushDataFromSCMGToSDN() {
        em.createNativeQuery(scmgQuery).executeUpdate();
    }

    @Override
    @Transactional
    @Modifying
    public void pushDataFromSCMMToSDN() {
        em.createNativeQuery(scmmQuery).executeUpdate();
    }

    @Override
    @Transactional
    @Modifying
    public void pushDataFromSTMMToSDN() {
        em.createNativeQuery(stmmQuery).executeUpdate();
    }

    @Override
    @Transactional
    @Modifying
    public void pushDataFromSTMWToSDN() {
        em.createNativeQuery(stmwQuery).executeUpdate();
    }

    @Override
    @Transactional
    @Modifying
    public void updateSCMGSkuTypes() {
        em.createNativeQuery(updateSCMGSkuTypesQuery).executeUpdate();
    }

    @Override
    @Transactional
    @Modifying
    public void updateSCMMSkuTypes() {
        em.createNativeQuery(updateSCMMSkuTypesQuery).executeUpdate();
    }


    @Override
    @Transactional
    @Modifying
    public void bulkLoadDataToAllTables(String query) {
        em.createNativeQuery(query).executeUpdate();
    }

}
