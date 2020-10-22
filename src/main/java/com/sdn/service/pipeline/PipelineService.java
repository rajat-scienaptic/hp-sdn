package com.sdn.service.pipeline;

public interface PipelineService {
    void pushDataToSdn();
    void bulkLoadDataToAllTables(String directoryPath);
}
