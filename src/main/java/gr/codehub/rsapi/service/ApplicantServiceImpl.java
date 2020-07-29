package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.ApplicantSkill;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.ApplicantRepo;
import gr.codehub.rsapi.repository.ApplicantSkillRepo;
import gr.codehub.rsapi.repository.SkillRepo;
import gr.codehub.rsapi.utility.FileReaderToList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ApplicantServiceImpl implements ApplicantService{


    private ApplicantRepo applicantRepo;
    private ApplicantSkillRepo applicantSkillRepo;
    private SkillRepo skillRepo;

    @Autowired
    public ApplicantServiceImpl(ApplicantRepo applicantRepo,ApplicantSkillRepo applicantSkillRepo, SkillRepo skillRepo) {
        this.applicantRepo = applicantRepo;
        this.applicantSkillRepo = applicantSkillRepo;
        this.skillRepo = skillRepo;

    }

    @Override
    public List<Applicant> getApplicants() {
        return applicantRepo.findAll();
    }

    @Override
    public Applicant addApplicant(Applicant applicant) {
        return applicantRepo.save(applicant);
    }

    @Override
    public Applicant updateApplicant(Applicant applicant, long applicantId) throws ApplicantNotFoundException {
        Applicant applicantInDb;
        Optional<Applicant> optionalApplicant = applicantRepo.findById(applicantId);
        if(optionalApplicant.isPresent()){
            applicantInDb = optionalApplicant.get();
            if(applicant.getAddress()!=null)
                applicantInDb.setAddress(applicant.getAddress());
            if(applicant.getRegion()!=null)
                applicantInDb.setRegion(applicant.getRegion());
            if(applicant.getEmail()!=null)
                applicantInDb.setEmail(applicant.getEmail());
            if(applicant.getDob()!=null)
                applicantInDb.setDob(applicant.getDob());
            applicantRepo.save(applicantInDb);
            return applicantInDb;
        }else throw new ApplicantNotFoundException("not such applicant exists");
    }

    @Override
    public Applicant deleteApplicant(long applicantIndex) throws  ApplicantNotFoundException{
        Applicant applicantInDb;
        Optional<Applicant> optionalApplicant = applicantRepo.findById(applicantIndex);
        if (optionalApplicant.isPresent()){
            applicantInDb = optionalApplicant.get();
            applicantInDb.setClosed(true);
            return applicantRepo.save(applicantInDb);


        }
        else throw new ApplicantNotFoundException("not such applicant exists");

    }

    @Override
    public Applicant getApplicant(long applicantId) throws ApplicantNotFoundException {
        Optional<Applicant> optionalApplicant= applicantRepo.findById(applicantId);
        if (optionalApplicant.isPresent()){
            Applicant appl = optionalApplicant.get();
            String a =appl.getApplicantSkills().get(1).getSkill().getName();
            System.out.println(a);
            return optionalApplicant.get();
        }
        else throw new ApplicantNotFoundException("not such applicant exists");
    }

    @Override
    public List<Applicant> getSelectedApplicants(String dob,
                                               String region,
                                               String name,
                                               Long applicantSkillId) throws ApplicantNotFoundException, ParseException {
        if (dob != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            LocalDate date = formatter.parse(dob).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return applicantRepo.findByDob(date).orElseThrow(() -> new ApplicantNotFoundException("Applicant not found"));
        }
        if (region != null)
            return applicantRepo.findByRegion(region).orElseThrow(() -> new ApplicantNotFoundException("Applicant not found"));
        if (name != null)
            return applicantRepo.findByFirstName(name).orElseThrow(() -> new ApplicantNotFoundException("Applicant not found"));
        if (applicantSkillId != null){
            List<Applicant> applicants = applicantRepo.findAll();
            List<Applicant> tempApplicants = new ArrayList<Applicant>();
            for (Applicant applicant : applicants) {

                List<ApplicantSkill> applicantSkills = applicant.getApplicantSkills();

                for (ApplicantSkill applicantSkill : applicant.getApplicantSkills()) {
                    if(applicantSkill.getSkill().getId()==applicantSkillId){
                        tempApplicants.add(applicant);
                    }
                    break;
                }

            }
            return tempApplicants;

        }
//            return applicantRepo.findByApplicantSkills(applicantSkillId).orElseThrow(() -> new ApplicantNotFoundException("Job offer not found"));

        return applicantRepo.findAll();
    }

    @Override
    public List<Applicant> readApplicants() throws IOException, InvalidFormatException {
        return FileReaderToList.readFromExcelApplicant("data.xlsx",applicantRepo, skillRepo, applicantSkillRepo);    }

    @Override
    public ApplicantSkill addSkillToApplicant(long applicantId, long skillId) throws ApplicantNotFoundException, SkillNotFoundException {
        Applicant applicant = applicantRepo.findById(applicantId)
                .orElseThrow(() -> new
                        ApplicantNotFoundException("Cannot find applicant"));

        Skill skill = skillRepo.findById(skillId).orElseThrow(() -> new
                SkillNotFoundException("Cannot find Skill"));

        ApplicantSkill applicantSkill = new ApplicantSkill();
        applicantSkill.setApplicant(applicant);
        applicantSkill.setSkill(skill);
        applicantSkillRepo.save(applicantSkill);
        applicant.getApplicantSkills().add(applicantSkill);
      //  applicantRepo.save(applicant);
        return applicantSkill;
    }
}
