package gr.codehub.rsapi.utility;


import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.ApplicantRepo;
import gr.codehub.rsapi.repository.JobOfferRepo;
import gr.codehub.rsapi.repository.SkillRepo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderToList {
    public static List<Applicant> readFromExcelApplicant(String filename, ApplicantRepo applicantRepository) throws IOException, InvalidFormatException {
        List<Applicant> applicants = new ArrayList<>() ;
        File workbookFile = new File(filename);
        FileInputStream file = new FileInputStream(workbookFile);
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        boolean firstTime = true;
        for (Row row : sheet) {
            if (firstTime) {
                firstTime = false;
                continue;
            }
            applicants.add(new Applicant()
                    .setId(row.getCell(0).getRowIndex())
                    .setfName(row.getCell(0).getStringCellValue())
                    .setlName(row.getCell(1).getStringCellValue())
                    .setAddress(row.getCell(2).getStringCellValue())
                    .setRegion(row.getCell(3).getStringCellValue())
                    .setEmail(row.getCell(4).getStringCellValue()));
            applicantRepository.saveAll(applicants);
        }
        // Closing the workbook
        workbook.close();
        return applicants;
    }
    public static List<JobOffer> readFromExcelJobOffers(String filename, JobOfferRepo jobOfferRepository) throws IOException, InvalidFormatException {
        ArrayList<JobOffer> jobOffers = new ArrayList<>();
        File workbookFile = new File(filename);
        FileInputStream file = new FileInputStream(workbookFile);
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(1);
        boolean firstTime = true;
        for (Row row : sheet) {
            if (firstTime) {
                firstTime = false;
                continue;
            }
            jobOffers.add(new JobOffer()
                    .setCompany(row.getCell(0).getStringCellValue())
                    .setTitle(row.getCell(1).getStringCellValue())
                    .setRegion(row.getCell(2).getStringCellValue()));
            jobOfferRepository.saveAll(jobOffers);
        }
        // Closing the workbook
        workbook.close();
        return jobOffers;
    }
    public static List<Skill> readFromExcelSkills(String filename, SkillRepo skillRepository) throws IOException, InvalidFormatException {
        ArrayList<Skill> skills = new ArrayList<>();
        File workbookFile = new File(filename);
        FileInputStream file = new FileInputStream(workbookFile);
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(2);
        // DataFormatter dataFormatter = new DataFormatter();
        boolean firstTime = true;
        for (Row row : sheet) {
            if (firstTime) {
                firstTime = false;
                continue;
            }
            skills.add(new Skill()
                    .setName(row.getCell(0).getStringCellValue())
                    .setLevels(row.getCell(1).getStringCellValue()));
            skillRepository.saveAll(skills);
        }
        // Closing the workbook
        workbook.close();
        return skills;
    }
}

