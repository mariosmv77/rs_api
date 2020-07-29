package gr.codehub.rsapi.utility;


import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.*;
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
    public static List<Applicant> readFromExcelApplicant(String filename, ApplicantRepo applicantRepository, SkillRepo skillRepo, ApplicantSkillRepo applicantSkillRepo) throws IOException, InvalidFormatException {
        List<Applicant> applicants = new ArrayList<>() ;
        File workbookFile = new File(filename);
        FileInputStream file = new FileInputStream(workbookFile);
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        boolean firstTime = true;
        for (Row row : sheet) {
            int cellNumbers = row.getPhysicalNumberOfCells();
            if (firstTime) {
                firstTime = false;
                continue;
            }
            Applicant tempApplicant = new Applicant();
            applicants.add(tempApplicant
                    .setfnamecustom(row.getCell(0).getStringCellValue())
                    .setlNameCustom(row.getCell(1).getStringCellValue())
                    .setAddressCust(row.getCell(2).getStringCellValue())
                    .setRegionCust(row.getCell(3).getStringCellValue())
                    .setEmailCust(row.getCell(4).getStringCellValue())
                    .setDobCust(row.getCell(5).getDateCellValue()));
            tempApplicant.setApplicantSkills(new ArrayList<ApplicantSkill>());
            tempApplicant = applicantRepository.save(tempApplicant);

            List<Skill> skills = skillRepo.findAll();

            for (int i = 7; i<cellNumbers; i++) {
                   for (Skill skill: skills) {
                       System.out.println(row.getCell(i).getStringCellValue());
                       System.out.println(row.getCell(6).getStringCellValue());
                   if (skill.getName().equals(row.getCell(i).getStringCellValue()) && skill.getLevels().equals(row.getCell(6).getStringCellValue())) {
                       System.out.println("!!!!!!skill.getName()"+ skill.getName() +"row.getCell(i).getStringCellValue()" +row.getCell(i).getStringCellValue());
                       ApplicantSkill applicantSkill = new ApplicantSkill();
                       applicantSkill.setApplicant(tempApplicant);
                       applicantSkill.setSkill(skill);
                       applicantSkillRepo.save(applicantSkill);
                       tempApplicant.getApplicantSkills().add(applicantSkill);
                   }
               }
            }

            applicantRepository.saveAll(applicants);

        }
        // Closing the workbook
        workbook.close();
        return applicants;
    }
    public static List<JobOffer> readFromExcelJobOffers(
            String filename, JobOfferRepo jobOfferRepository, SkillRepo skillRepo,
            JobOfferSkillRepo jobOfferSkillRepo) throws IOException, InvalidFormatException {
        ArrayList<JobOffer> jobOffers = new ArrayList<>();
        File workbookFile = new File(filename);
        FileInputStream file = new FileInputStream(workbookFile);
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(1);
        boolean firstTime = true;
        for (Row row : sheet) {
            int cellNumbers = row.getPhysicalNumberOfCells();
            if (firstTime) {
                firstTime = false;
                continue;
            }
            JobOffer tempJobOffer = new JobOffer();
            jobOffers.add(tempJobOffer
                    .setCompanyCust(row.getCell(0).getStringCellValue())
                    .setTitleCust(row.getCell(1).getStringCellValue())
                    .setRegionCust(row.getCell(2).getStringCellValue())
                    .setOfferDatecust(row.getCell(3).getDateCellValue()));

            tempJobOffer.setJobOfferSkills(new ArrayList<>());
            tempJobOffer =jobOfferRepository.save(tempJobOffer);

            List<Skill> skills = skillRepo.findAll();

            for (int i = 5; i<cellNumbers; i++) {
                for (Skill skill: skills) {
                    System.out.println(row.getCell(i).getStringCellValue());
                    System.out.println(row.getCell(4).getStringCellValue());
                    if (skill.getName().equals(row.getCell(i).getStringCellValue()) && skill.getLevels().equals(row.getCell(4).getStringCellValue())) {
                        System.out.println("!!!!!!skill.getName()" + skill.getName() + "row.getCell(i).getStringCellValue()" + row.getCell(i).getStringCellValue());
                        JobOfferSkill jobOfferSkill = new JobOfferSkill();
                        jobOfferSkill.setJobOffer(tempJobOffer);
                        jobOfferSkill.setSkill(skill);
                        jobOfferSkillRepo.save(jobOfferSkill);
                        tempJobOffer.getJobOfferSkills().add(jobOfferSkill);
                    }
                }
            }

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

