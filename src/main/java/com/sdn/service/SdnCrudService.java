package com.sdn.service;

import com.sdn.dto.FilterRequestDTO;
import com.sdn.dto.SdnDataDTO;
import com.sdn.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface SdnCrudService {
 List<SdnData> getAllSdnData();
 List<SdnData> getAllSdnDataWithFilter(FilterRequestDTO filterRequestDTO);
 Optional<SdnData> updateSdnData(int id, SdnDataDTO sdnDataDTO, String cookies) throws ParseException;
 List<SdnDataChangeLogs> archiveSdnData(int id);
 List<Country> getCountries();
 List<SKU> getSku(int id);
 void fillSDNData(MultipartFile file);
 List<SdcViolations> getSdcViolations();
}
