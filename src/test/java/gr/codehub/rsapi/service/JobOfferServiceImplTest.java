package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.JobOfferAlreadyClosed;
import gr.codehub.rsapi.exception.JobOfferCreationException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.JobOfferSkill;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.JobOfferRepo;
import gr.codehub.rsapi.repository.JobOfferSkillRepo;
import gr.codehub.rsapi.repository.SkillRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class JobOfferServiceImplTest {

    @InjectMocks
    private JobOfferServiceImpl jobOfferServiceImpl;

    @Mock
    private JobOfferRepo jobOfferRepo;

    @Mock
    private SkillRepo skillRepo;

    @Mock
    private JobOfferSkillRepo jobOfferSkillRepo;


    @Test
    void getJobOffers() {
    }

    @Test
    void addJobOffer() {
        JobOffer jobOffer = new JobOffer();
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        //given
        when( jobOfferRepo.save(jobOffer)).thenReturn(jobOffer);
        when( jobOfferRepo.findAll()).thenReturn(jobOffers);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(false);
        jobOffer.setOfferDate(LocalDate.now());
        jobOfferServiceImpl.addJobOffer(jobOffer);
        List<JobOffer> jobOffers1 = jobOfferServiceImpl.getJobOffers();
        assertEquals(1,jobOffers1.size());
    }

    @Test
    void updateJobOffer() throws JobOfferNotFoundException {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        Optional<JobOffer> jobOfferOptional= Optional.of(jobOffer);
        //given
        when( jobOfferRepo.save(jobOffer)).thenReturn(jobOffer);
        when( jobOfferRepo.findById((long)1)).thenReturn(jobOfferOptional);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(false);
        jobOffer.setOfferDate(LocalDate.now());
        jobOfferServiceImpl.updateJobOffer(jobOffer,1);
        List<JobOffer> jobOffers1 = jobOfferServiceImpl.getJobOffers();
        assertEquals(jobOfferServiceImpl.getJobOffer(1),jobOffer);
    }

    @Test
    void updateJobOfferJobNotFoundException() {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        Optional<JobOffer> jobOfferOptional= Optional.of(jobOffer);
        when( jobOfferRepo.save(jobOffer)).thenReturn(jobOffer);
        when( jobOfferRepo.findById((long)1)).thenReturn(jobOfferOptional);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(false);
        jobOffer.setOfferDate(LocalDate.now());
        List<JobOffer> jobOffers1 = jobOfferServiceImpl.getJobOffers();
        Assertions.assertThrows(JobOfferNotFoundException.class, () -> {
            jobOfferServiceImpl.updateJobOffer(jobOffer,2);
        });
    }

    @Test
    void deleteJobOffer() throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        JobOffer jobOffer = new JobOffer();
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        when(jobOfferRepo.save(jobOffer)).thenReturn(jobOffer);
        when( jobOfferRepo.findById((long)1)).thenReturn(Optional.of(jobOffer));
        jobOfferServiceImpl.deleteJobOffer(1);
       assertEquals(jobOffers.get(0).isInactive(),true);
    }

    @Test
    void deleteJobOfferJobOfferNotFoundException() throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        JobOffer jobOffer = new JobOffer();
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        when(jobOfferRepo.save(jobOffer)).thenReturn(jobOffer);
        when( jobOfferRepo.findById((long)1)).thenReturn(Optional.of(jobOffer));
        jobOfferServiceImpl.deleteJobOffer(1);
        Assertions.assertThrows(JobOfferNotFoundException.class, () -> {
            jobOfferServiceImpl.deleteJobOffer(2);
        });
    }

    @Test
    void deleteJobOfferJobOfferAlreadyClosed() throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setInactive(true);
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        when(jobOfferRepo.save(jobOffer)).thenReturn(jobOffer);
        when( jobOfferRepo.findById((long)1)).thenReturn(Optional.of(jobOffer));
        Assertions.assertThrows(JobOfferAlreadyClosed.class, () -> {
            jobOfferServiceImpl.deleteJobOffer(1);
        });
    }


    @Test
    void getJobOffer() {
    }

    @Test
    void getSelectedJobOffers() {

    }

    @Test
    void addSkillToJobOffer() throws JobOfferNotFoundException, SkillNotFoundException {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        Skill skill = new Skill();
        skill.setId(1);
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        skill.setLevels("mid");
        skill.setName("Java");
        List<JobOfferSkill> skills = new ArrayList<>();
        JobOfferSkill jobOfferSkill =new JobOfferSkill();
        jobOfferSkill.setSkill(skill);
        jobOffer.setJobOfferSkills(skills);
        when( jobOfferRepo.findById((long)1)).thenReturn(Optional.of(jobOffer));
        when(skillRepo.findById((long)1)).thenReturn(Optional.of(skill));
        when(jobOfferSkillRepo.save(jobOfferSkill)).thenReturn(jobOfferSkill);
        jobOfferServiceImpl.addSkillToJobOffer(1,skill.getId());
        assertEquals(jobOfferServiceImpl.getJobOffer(1),jobOffer);
    }

    @Test
    void addSkillToJobOfferTestJobOfferNotFoundException() {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        Skill skill = new Skill();
        skill.setId(1);
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        skill.setLevels("mid");
        skill.setName("Java");
        List<JobOfferSkill> skills = new ArrayList<>();
        JobOfferSkill jobOfferSkill =new JobOfferSkill();
        jobOfferSkill.setSkill(skill);
        jobOffer.setJobOfferSkills(skills);
        when( jobOfferRepo.findById((long)1)).thenReturn(Optional.of(jobOffer));
        when(skillRepo.findById((long)1)).thenReturn(Optional.of(skill));
        when(jobOfferSkillRepo.save(jobOfferSkill)).thenReturn(jobOfferSkill);
        Assertions.assertThrows(JobOfferNotFoundException.class, () -> {
            jobOfferServiceImpl.addSkillToJobOffer(2,skill.getId());
        });
    }

    @Test
    void addSkillToJobOfferTestSkillNotFoundException()  {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        Skill skill = new Skill();
        skill.setId(1);
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        skill.setLevels("mid");
        skill.setName("Java");
        List<JobOfferSkill> skills = new ArrayList<>();
        JobOfferSkill jobOfferSkill =new JobOfferSkill();
        jobOfferSkill.setSkill(skill);
        jobOffer.setJobOfferSkills(skills);
        when( jobOfferRepo.findById((long)1)).thenReturn(Optional.of(jobOffer));
        when(skillRepo.findById((long)1)).thenReturn(Optional.of(skill));
        when(jobOfferSkillRepo.save(jobOfferSkill)).thenReturn(jobOfferSkill);
        Assertions.assertThrows(SkillNotFoundException.class, () -> {
            jobOfferServiceImpl.addSkillToJobOffer(1,2);
        });
    }
    @Test
    void readJobOffers() {
    }
}