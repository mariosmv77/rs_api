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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class FileReaderToList {
    /**
     * Initializes the database table of applicant
     * @param filename
     * @param applicantRepository
     * @param skillRepo
     * @param applicantSkillRepo
     * @return {@code List<Applicant>}
     * @throws IOException
     * @throws InvalidFormatException
     */
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
                   if (skill.getName().equals(row.getCell(i).getStringCellValue()) && skill.getLevels().equals(row.getCell(6).getStringCellValue())) {
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

    /**
     * Initializes the database table of jobOffer
     * @param filename
     * @param jobOfferRepository
     * @param skillRepo
     * @param jobOfferSkillRepo
     * @return {@code List<JobOffer>}
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<JobOffer> readFromExcelJobOffers(String filename, JobOfferRepo jobOfferRepository,SkillRepo skillRepo, JobOfferSkillRepo jobOfferSkillRepo) throws IOException, InvalidFormatException {
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
                    .setOfferDatecust(row.getCell(3).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

            tempJobOffer.setJobOfferSkills(new ArrayList<>());
            tempJobOffer =jobOfferRepository.save(tempJobOffer);

            List<Skill> skills = skillRepo.findAll();

            for (int i = 5; i<cellNumbers; i++) {
                for (Skill skill: skills) {
                if (skill.getName().equals(row.getCell(i).getStringCellValue()) && skill.getLevels().equals(row.getCell(4).getStringCellValue())) {
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

    /**
     * Initializes the database table of Skills
     * @param filename
     * @param skillRepository
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<Skill> readFromExcelSkills(String filename, SkillRepo skillRepository) throws IOException, InvalidFormatException {
        ArrayList<Skill> skills = new ArrayList<>();
        File workbookFile = new File(filename);
        FileInputStream file = new FileInputStream(workbookFile);
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(2);
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

