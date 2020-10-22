package com.sdn.service.sdn;

import com.sdn.dto.sdn.SdnDataDTO;
import com.sdn.dto.sdn.SdnFilterRequestDTO;
import com.sdn.model.sdn.SdcViolations;
import com.sdn.model.sdn.SdnData;
import com.sdn.model.sdn.SdnDataChangeLogs;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface SdnCrudService {
    List<SdnData> getAllSdnData();

    List<SdnData> getAllSdnDataWithFilter(SdnFilterRequestDTO sdnFilterRequestDTO);

    Optional<SdnData> updateSdnData(int id, SdnDataDTO sdnDataDTO, String cookies) throws ParseException;

    List<SdnDataChangeLogs> archiveSdnData(int id);

    List<String> getRegions();

    List<String> getSkus(SdnFilterRequestDTO sdnFilterRequestDTO);

    void fillSDNData(MultipartFile file);

    List<SdcViolations> getSdcViolations();
}
