package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.ApplicantAlreadyClosed;
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
    void addApplicant() throws ApplicantCreationException {


        Applicant applicant = new Applicant();
        applicant.setFirstName("Maria");
        applicant.setLastName("Mariou");
        applicant.setAddress("Kanari 8888");
        applicant.setRegion("Athina");
        applicant.setEmail("abcdefg@gmail.gr");
        applicant.setDob(new Date());
        when(applicantRepo.save(applicant)).thenReturn(applicant);
        Applicant app1 = applicantServiceImpl.addApplicant(applicant);
        assertNotNull(app1);
        assertEquals(applicant.getFirstName(), app1.getFirstName());
    }

    @Test
    void addApplicantInvalidEmailException() throws ApplicantCreationException {

        Applicant applicant = new Applicant();
        applicant.setFirstName("Maria");
        applicant.setLastName("mariou");
        applicant.setAddress("Kanari 88");
        applicant.setRegion("Athina");
        applicant.setEmail("agmail.gr");
        applicant.setDob(new Date());
        when(applicantRepo.save(applicant)).thenReturn(applicant);

        Assertions.assertThrows(ApplicantCreationException.class,
                () -> {Applicant applicant1= applicantServiceImpl.addApplicant(applicant);
                });

    }

    @Test
    void addApplicantNullFields() throws ApplicantCreationException{
        Applicant applicant = new Applicant();
        applicant.setFirstName("Maria");
        applicant.setLastName("mariou");
        applicant.setAddress("Kanari 88");
        applicant.setRegion("Athina");
        applicant.setEmail("agmail.gr");
        when(applicantRepo.save(applicant)).thenReturn(applicant);
        Assertions.assertThrows(ApplicantCreationException.class,
                () ->{Applicant applicant1 = applicantServiceImpl.addApplicant(applicant);
        });
    }

    @Test
    void updateApplicant() {

    }

    @Test
    void deleteApplicant() throws ApplicantNotFoundException, ApplicantAlreadyClosed {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        when(applicantRepo.save(applicant)).thenReturn(applicant);
        when(applicantRepo.findById(applicant.getId())).thenReturn(Optional.of(applicant));
        applicantServiceImpl.deleteApplicant(1);
        assertEquals(applicantServiceImpl.getApplicant(1).isInactive(),true);
    }

    @Test
    void deleteApplicantInvalidId() throws ApplicantNotFoundException, ApplicantAlreadyClosed {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        when(applicantRepo.save(applicant)).thenReturn(applicant);
        when(applicantRepo.findById(applicant.getId())).thenReturn(Optional.of(applicant));
        Assertions.assertThrows(ApplicantNotFoundException.class, () ->{applicantServiceImpl.deleteApplicant(2);

        });

    }

    @Test
    void deleteApplicantAlreadyClosed() throws ApplicantNotFoundException, ApplicantAlreadyClosed {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        applicant.setInactive(true);
        when(applicantRepo.save(applicant)).thenReturn(applicant);
        when(applicantRepo.findById(applicant.getId())).thenReturn(Optional.of(applicant));
        Assertions.assertThrows(ApplicantAlreadyClosed.class, () ->{applicantServiceImpl.deleteApplicant(1);

        });

    }




    @Test
    void getApplicant() throws ApplicantNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        applicant.setFirstName("vagelis");
        when(applicantRepo.findById(applicant.getId())).thenReturn(Optional.of(applicant));
        assertEquals("vagelis",applicantServiceImpl.getApplicant(1).getFirstName());
    }

    @Test
    void getApplicantInvalidId() throws ApplicantNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        applicant.setFirstName("vagelis");
        when(applicantRepo.findById(applicant.getId())).thenReturn(Optional.of(applicant));
        Assertions.assertThrows(ApplicantNotFoundException.class,
                () ->{applicantServiceImpl.getApplicant(2);}
        );

    }



    @Test
    void getSelectedApplicants() {
    }



    @Test
    void addSkillToApplicant() throws ApplicantNotFoundException, SkillNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        applicant.setInactive(false);
        applicant.setFirstName("afasf");
        applicant.setLastName("weggwg");
        applicant.setRegion("afja");
        applicant.setAddress("ssgdsg");
        applicant.setDob(new Date());
        applicant.setEmail("anslganl@kjskgmil");
        Skill skill = new Skill();
        skill.setId(1);
        skill.setLevels("Senior");
        skill.setName("Spring");
        when(applicantRepo.findById((long) 1)).thenReturn(Optional.of(applicant));
        when(skillRepo.findById((long) 1)).thenReturn(Optional.of(skill));
        ApplicantSkill applicantSkill = applicantServiceImpl.addSkillToApplicant(1,1);
        when(applicantSkillRepo.save(applicantSkill)).thenReturn(applicantSkill);
        assertEquals(1,applicantSkill.getApplicant().getId());
        assertEquals(1,applicantSkill.getSkill().getId());

    }

    @Test
    void addSkillToApplicantTestApplicantNotFoundException() throws ApplicantNotFoundException, SkillNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        applicant.setAddress("Athinas 20");
        applicant.setRegion("Salonika");
        applicant.setFirstName("Dimitris");
        applicant.setLastName("Dimitrioy");
        Skill skill = new Skill();
        skill.setId(1);
        skill.setLevels("Junior");
        skill.setName("Python");
        when(applicantRepo.findById((long) 1)).thenReturn(Optional.of(applicant));
        when(skillRepo.findById((long) 1)).thenReturn(Optional.of(skill));
        ApplicantSkill applicantSkill =applicantServiceImpl.addSkillToApplicant(1, skill.getId());
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
        skill.setLevels("Senior");
        skill.setName("Python");
        when(applicantRepo.findById((long) 1)).thenReturn(Optional.of(applicant));
        when(skillRepo.findById((long) 1)).thenReturn(Optional.of(skill));
        ApplicantSkill applicantSkill =applicantServiceImpl.addSkillToApplicant(1, skill.getId());
        Assertions.assertThrows(SkillNotFoundException.class, () -> {
            applicantServiceImpl.addSkillToApplicant(1, 2);
        });
    }
}