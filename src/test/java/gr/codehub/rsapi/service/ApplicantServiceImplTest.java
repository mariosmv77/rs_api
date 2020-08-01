package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.ApplicantCreationException;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.ApplicantSkill;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.ApplicantRepo;
import gr.codehub.rsapi.repository.ApplicantSkillRepo;
import gr.codehub.rsapi.repository.SkillRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ApplicantServiceImplTest {
    @InjectMocks
    private ApplicantServiceImpl applicantServiceImpl;


    @Mock
    private ApplicantRepo applicantRepo;
    @Mock
    private SkillRepo skillRepo;
    @Mock
    private ApplicantSkillRepo applicantSkillRepo;

    @Test
    void getApplicants() {
    }

    @Test
    void addApplicant() throws ApplicantCreationException {


        Applicant applicant = new Applicant();
        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant);
        //given
        when(applicantRepo.save(applicant)).thenReturn(applicant);
        when(applicantRepo.findAll()).thenReturn(applicants);
        applicant.setFirstName("Maria");
        applicant.setLastName("Mariou");
        applicant.setAddress("Kanari 8888");
        applicant.setRegion("Athina");
        applicant.setEmail("abcdefg@gmail.gr");
        applicant.setDob(new Date());

        applicantServiceImpl.addApplicant(applicant);
       // List<Applicant> applicants1 = applicantServiceImpl.getApplicants();
     //   assertEquals(1, applicants1.size());
    }

    @Test
    void addApplicantInvalidEmailException() throws ApplicantCreationException {

        Applicant applicant = new Applicant();
        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant);
        //given
        when(applicantRepo.save(applicant)).thenReturn(applicant);
        when(applicantRepo.findAll()).thenReturn(applicants);
        applicant.setFirstName("Maria");
        applicant.setLastName("mariou");
        applicant.setAddress("Kanari 88");
        applicant.setRegion("Athina");
        applicant.setEmail("agmail.gr");
        applicant.setDob(new Date());

     //   List<Applicant> applicants1 = applicantServiceImpl.getApplicants();
        Assertions.assertThrows(ApplicantCreationException.class,
                () -> {
                    applicantServiceImpl.addApplicant(applicant);
                });

    }

    @Test
    void updateApplicant() {
    }

    @Test
    void deleteApplicant() {
    }

    @Test
    void getApplicant() {
    }

    @Test
    void getSelectedApplicants() {
    }

    @Test
    void readApplicants() {
    }

    @Test
    void addSkillToApplicant() throws ApplicantNotFoundException, SkillNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        Skill skill = new Skill();
        skill.setId(1);
        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant);
        skill.setLevels("mid");
        skill.setName("Java");
        List<ApplicantSkill> skills = new ArrayList<>();
        ApplicantSkill applicantSkill = new ApplicantSkill();
        applicantSkill.setSkill(skill);
        applicant.setApplicantSkills(skills);
        when(applicantRepo.findById((long) 1)).thenReturn(Optional.of(applicant));
        when(skillRepo.findById((long) 1)).thenReturn(Optional.of(skill));
        when(applicantSkillRepo.save(applicantSkill)).thenReturn(applicantSkill);
        applicantServiceImpl.addSkillToApplicant(1, skill.getId());
        assertEquals(applicantServiceImpl.getApplicant(1), applicant);
    }

    @Test
    void addSkillToApplicantTestApplicantNotFoundException() throws ApplicantNotFoundException, SkillNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        Skill skill = new Skill();
        skill.setId(1);
        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant);
        skill.setLevels("mid");
        skill.setName("Java");
        List<ApplicantSkill> skills = new ArrayList<>();
        ApplicantSkill applicantSkill = new ApplicantSkill();
        applicantSkill.setSkill(skill);
        applicant.setApplicantSkills(skills);
        when(applicantRepo.findById((long) 1)).thenReturn(Optional.of(applicant));
        when(skillRepo.findById((long) 1)).thenReturn(Optional.of(skill));
        when(applicantSkillRepo.save(applicantSkill)).thenReturn(applicantSkill);
        applicantServiceImpl.addSkillToApplicant(1, skill.getId());
        Assertions.assertThrows(ApplicantNotFoundException.class, () -> {
            applicantServiceImpl.addSkillToApplicant(2, skill.getId());
        });
    }
    @Test
    void addSkillToApplicantTestSkillNotFoundException() throws ApplicantNotFoundException, SkillNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        Skill skill = new Skill();
        skill.setId(1);
        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant);
        skill.setLevels("mid");
        skill.setName("Java");
        List<ApplicantSkill> skills = new ArrayList<>();
        ApplicantSkill applicantSkill = new ApplicantSkill();
        applicantSkill.setSkill(skill);
        applicant.setApplicantSkills(skills);
        when(applicantRepo.findById((long) 1)).thenReturn(Optional.of(applicant));
        when(skillRepo.findById((long) 1)).thenReturn(Optional.of(skill));
        when(applicantSkillRepo.save(applicantSkill)).thenReturn(applicantSkill);
        applicantServiceImpl.addSkillToApplicant(1, skill.getId());
        Assertions.assertThrows(SkillNotFoundException.class, () -> {
            applicantServiceImpl.addSkillToApplicant(1, 2);
        });
    }
}