package com.sdn.repository.pipeline;

/**
 * @author RAJAT MEENA
 */
public interface CustomRepository {
    void pushDataFromSCMMToSDN();
    void pushDataFromSCMGToSDN();
    void pushDataFromSTMMToSDN();
    void pushDataFromSTMWToSDN();
    void updateSCMGSkuTypes();
    void updateSCMMSkuTypes();
    void bulkLoadDataToAllTables(String query);
}
