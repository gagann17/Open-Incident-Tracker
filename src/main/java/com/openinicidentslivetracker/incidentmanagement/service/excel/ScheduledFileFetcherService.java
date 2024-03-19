package com.openinicidentslivetracker.incidentmanagement.service.excel;

import com.openinicidentslivetracker.incidentmanagement.model.IncidentDetails;
import com.openinicidentslivetracker.incidentmanagement.model.OpenIncidents;
import com.openinicidentslivetracker.incidentmanagement.repository.IncidentDetailsRepository;
import com.openinicidentslivetracker.incidentmanagement.repository.OpenIncidentsRepository;
import com.openinicidentslivetracker.incidentmanagement.service.ClosedIncidentService;
import com.openinicidentslivetracker.incidentmanagement.service.IncidentDetailsService;
import com.openinicidentslivetracker.incidentmanagement.service.OpenIncidentService;
import com.openinicidentslivetracker.incidentmanagement.service.request.OpenIncidentsRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduledFileFetcherService {
//    private static final String EXCEL_FILE_PATH = "C:\\Users\\ASUS\\Documents\\Incidents\\Open_Incidents.xlsx";

    private final IncidentDetailsService incidentDetailsService;

    private final ClosedIncidentService closedIncidentService;

    private final OpenIncidentService openIncidentService;



//    private boolean incidentsExist(List<String> incidentNumbers){
//        return incidentDetailsRepository.existsByIncidentNumberIn(incidentNumbers);
//    }
    public ResponseEntity<?> saveIncidentDetails(MultipartFile file) {
        List<IncidentDetails> incidentDetailsList = null;
        List<OpenIncidents> openIncidentsList = null;
//        List<String> incidentList = null;
        try {
//            Workbook workbook = WorkbookFactory.create(new FileInputStream(EXCEL_FILE_PATH));
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

//            incidentList = new ArrayList<>();
            incidentDetailsList = new ArrayList<>();
            openIncidentsList = new ArrayList<>();

            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

//            int lastRowNum = sheet.getLastRowNum();

            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();

//                CellType cellType = row.getCell(0).getCellType();
                if (row.getPhysicalNumberOfCells() > 0) {
                    IncidentDetails incidentDetails = IncidentDetails.builder()
                            .incidentNumber(row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue())
                            .state(row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue())
                            .priority(Integer.valueOf((int) row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getNumericCellValue()))
                            .assignmentGroup(row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue())
                            .openedAt(Timestamp.valueOf(row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getLocalDateTimeCellValue()))
                            .shortDescription(row.getCell(5, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()).build();
                    //saving incident details
                    incidentDetailsList.add(incidentDetails);

                    OpenIncidents openIncidents = OpenIncidents.builder().
                            incidentNumber(row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()).
                            assignmentGroup(row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue()).
                            priority(Integer.valueOf((int) row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getNumericCellValue())).
                            build();

                    //saving incident number in the list
//                    incidentList.add(row.getCell(0).getStringCellValue());
                    openIncidentsList.add(openIncidents);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        closedIncidentService.closeAndDeleteIncidents(incidentDetailsList,openIncidentsList);

        incidentDetailsService.saveIncidentDetails(incidentDetailsList);

        openIncidentService.saveOpenIncidents(openIncidentsList);

        return new ResponseEntity<>("Operations completed successfully", HttpStatus.CREATED);
    }
}
