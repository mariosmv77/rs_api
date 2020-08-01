package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MatchServiceImplTest {

    @InjectMocks
    private MatchServiceImpl matchServiceImpl;


    @Mock
    private ApplicantRepo applicantRepo;
    @Mock
    private MatchRepo matchRepo;
    @Mock
    private JobOfferRepo jobOfferRepo;


    @Test
    void addManuallyMatch() throws JobOfferAlreadyClosed, ApplicantAlreadyClosed, JobOfferNotFoundException, ApplicantNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        applicant.setFirstName("Maria");
        applicant.setLastName("Mariou");
        applicant.setAddress("Kanari 8888");
        applicant.setRegion("Athina");
        applicant.setEmail("abcdefg@gmail.gr");
        applicant.setDob(new Date());
        applicant.setInactive(false);
        when(applicantRepo.findById(applicant.getId())).thenReturn(Optional.of(applicant));
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(false);
        jobOffer.setOfferDate(LocalDate.now());
        when(jobOfferRepo.findById(jobOffer.getId())).thenReturn(Optional.of(jobOffer));

        Match match = matchServiceImpl.addManuallyMatch(1, 1);


        assertEquals(1, match.getApplicant().getId());
        assertEquals(1, match.getJobOffer().getId());


    }

    @Test
    void addManuallyMatchInvalidApplicant() throws JobOfferAlreadyClosed, ApplicantAlreadyClosed, JobOfferNotFoundException, ApplicantNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        applicant.setFirstName("Maria");
        applicant.setLastName("Mariou");
        applicant.setAddress("Kanari 8888");
        applicant.setRegion("Athina");
        applicant.setEmail("abcdefg@gmail.gr");
        applicant.setDob(new Date());
        applicant.setInactive(false);
        when(applicantRepo.findById(applicant.getId())).thenReturn(Optional.of(applicant));
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(false);
        jobOffer.setOfferDate(LocalDate.now());
        when(jobOfferRepo.findById(jobOffer.getId())).thenReturn(Optional.of(jobOffer));

        Assertions.assertThrows(ApplicantNotFoundException.class, () -> {
            matchServiceImpl.addManuallyMatch(1, 2);
        });

    }

    @Test
    void addManuallyMatchInvalidJobOffer() throws JobOfferAlreadyClosed, ApplicantAlreadyClosed, JobOfferNotFoundException, ApplicantNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        applicant.setFirstName("Maria");
        applicant.setLastName("Mariou");
        applicant.setAddress("Kanari 8888");
        applicant.setRegion("Athina");
        applicant.setEmail("abcdefg@gmail.gr");
        applicant.setDob(new Date());
        applicant.setInactive(false);
        when(applicantRepo.findById(applicant.getId())).thenReturn(Optional.of(applicant));
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(false);
        jobOffer.setOfferDate(LocalDate.now());
        when(jobOfferRepo.findById(jobOffer.getId())).thenReturn(Optional.of(jobOffer));

        Assertions.assertThrows(JobOfferNotFoundException.class, () -> {
            matchServiceImpl.addManuallyMatch(2, 1);
        });

    }

    @Test
    void addManuallyMatchJobOfferInactive() throws JobOfferAlreadyClosed, ApplicantAlreadyClosed, JobOfferNotFoundException, ApplicantNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        applicant.setFirstName("Maria");
        applicant.setLastName("Mariou");
        applicant.setAddress("Kanari 8888");
        applicant.setRegion("Athina");
        applicant.setEmail("abcdefg@gmail.gr");
        applicant.setDob(new Date());
        applicant.setInactive(false);
        when(applicantRepo.findById(applicant.getId())).thenReturn(Optional.of(applicant));
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(true);
        jobOffer.setOfferDate(LocalDate.now());
        when(jobOfferRepo.findById(jobOffer.getId())).thenReturn(Optional.of(jobOffer));

        Assertions.assertThrows(JobOfferAlreadyClosed.class, () -> {
            matchServiceImpl.addManuallyMatch(1, 1);
        });

    }


    @Test
    void addManuallyMatchApplicantInactive() throws JobOfferAlreadyClosed, ApplicantAlreadyClosed, JobOfferNotFoundException, ApplicantNotFoundException {
        Applicant applicant = new Applicant();
        applicant.setId(1);
        applicant.setFirstName("Maria");
        applicant.setLastName("Mariou");
        applicant.setAddress("Kanari 8888");
        applicant.setRegion("Athina");
        applicant.setEmail("abcdefg@gmail.gr");
        applicant.setDob(new Date());
        applicant.setInactive(true);
        when(applicantRepo.findById(applicant.getId())).thenReturn(Optional.of(applicant));
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(false);
        jobOffer.setOfferDate(LocalDate.now());
        when(jobOfferRepo.findById(jobOffer.getId())).thenReturn(Optional.of(jobOffer));

        Assertions.assertThrows(ApplicantAlreadyClosed.class, () -> {
            matchServiceImpl.addManuallyMatch(1, 1);
        });

    }


    @Test
    void addPartiallyMatch() throws JobOfferNotFoundException, JobOfferAlreadyClosed {

        Applicant applicant1 = new Applicant();
        applicant1.setId(1);
        applicant1.setInactive(false);

        Applicant applicant2 = new Applicant();
        applicant2.setId(2);
        applicant2.setInactive(false);

        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant1);
        applicants.add(applicant2);

        Skill skill1 = new Skill();
        skill1.setName("Java");
        skill1.setLevels("Senior");
        Skill skill2 = new Skill();
        skill2.setName("Spring");
        skill2.setLevels("Mid");
        Skill skill3 = new Skill();
        skill3.setName("Test");
        skill3.setLevels("Junior");

        ApplicantSkill applicantSkill1 = new ApplicantSkill();
        applicantSkill1.setSkill(skill1);
        ApplicantSkill applicantSkill2 = new ApplicantSkill();
        applicantSkill2.setSkill(skill2);
        ApplicantSkill applicantSkill3 = new ApplicantSkill();
        applicantSkill3.setSkill(skill3);

        List<ApplicantSkill> applicantSkills1 = new ArrayList<ApplicantSkill>();
        applicantSkills1.add(applicantSkill1);
        applicantSkills1.add(applicantSkill2);
        applicant1.setApplicantSkills(applicantSkills1);
        // applicant1 has skill1 and skill2

        List<ApplicantSkill> applicantSkills2 = new ArrayList<ApplicantSkill>();
        applicantSkills2.add(applicantSkill3);
        ApplicantSkill applicantSkill4 = new ApplicantSkill();
        applicantSkill4.setSkill(skill1);
        applicantSkills2.add(applicantSkill4);
        applicant2.setApplicantSkills(applicantSkills2);

        //applicant 2 has skill1 and skill3

        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(false);
        jobOffer.setOfferDate(LocalDate.now());
        JobOfferSkill jobOfferSkill1 = new JobOfferSkill();
        jobOfferSkill1.setSkill(skill1);
        JobOfferSkill jobOfferSkill2 = new JobOfferSkill();
        jobOfferSkill2.setSkill(skill2);
        JobOfferSkill jobOfferSkill3 = new JobOfferSkill();
        jobOfferSkill3.setSkill(skill3);
        List<JobOfferSkill> jobOfferSkills = new ArrayList<>();
        jobOfferSkills.add(jobOfferSkill1);
        jobOfferSkills.add(jobOfferSkill2);
        jobOfferSkills.add(jobOfferSkill3);
        jobOffer.setJobOfferSkills(jobOfferSkills);
        //joboffer requires skill1, skill2 and skill3

        when(jobOfferRepo.findById(jobOffer.getId())).thenReturn(Optional.of(jobOffer));
        when(applicantRepo.findAll()).thenReturn(applicants);


        List<Match> autoMatch = matchServiceImpl.addPartiallyMatch(1);
        assertEquals(2, autoMatch.size());

    }

    @Test
    void addPartiallyMatchInvalidJobOffer() throws JobOfferNotFoundException, JobOfferAlreadyClosed {

        Applicant applicant1 = new Applicant();
        applicant1.setId(1);
        applicant1.setInactive(false);

        Applicant applicant2 = new Applicant();
        applicant2.setId(2);
        applicant2.setInactive(false);

        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant1);
        applicants.add(applicant2);

        Skill skill1 = new Skill();
        skill1.setName("Java");
        skill1.setLevels("Senior");
        Skill skill2 = new Skill();
        skill2.setName("Spring");
        skill2.setLevels("Mid");
        Skill skill3 = new Skill();
        skill3.setName("Test");
        skill3.setLevels("Junior");

        ApplicantSkill applicantSkill1 = new ApplicantSkill();
        applicantSkill1.setSkill(skill1);
        ApplicantSkill applicantSkill2 = new ApplicantSkill();
        applicantSkill2.setSkill(skill2);
        ApplicantSkill applicantSkill3 = new ApplicantSkill();
        applicantSkill3.setSkill(skill3);

        List<ApplicantSkill> applicantSkills1 = new ArrayList<ApplicantSkill>();
        applicantSkills1.add(applicantSkill1);
        applicantSkills1.add(applicantSkill2);
        applicant1.setApplicantSkills(applicantSkills1);
        // applicant1 has skill1 and skill2

        List<ApplicantSkill> applicantSkills2 = new ArrayList<ApplicantSkill>();
        applicantSkills2.add(applicantSkill3);
        ApplicantSkill applicantSkill4 = new ApplicantSkill();
        applicantSkill4.setSkill(skill1);
        applicantSkills2.add(applicantSkill4);
        applicant2.setApplicantSkills(applicantSkills2);

        //applicant 2 has skill1 and skill3

        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(false);
        jobOffer.setOfferDate(LocalDate.now());
        JobOfferSkill jobOfferSkill1 = new JobOfferSkill();
        jobOfferSkill1.setSkill(skill1);
        JobOfferSkill jobOfferSkill2 = new JobOfferSkill();
        jobOfferSkill2.setSkill(skill2);
        JobOfferSkill jobOfferSkill3 = new JobOfferSkill();
        jobOfferSkill3.setSkill(skill3);
        List<JobOfferSkill> jobOfferSkills = new ArrayList<>();
        jobOfferSkills.add(jobOfferSkill1);
        jobOfferSkills.add(jobOfferSkill2);
        jobOfferSkills.add(jobOfferSkill3);
        jobOffer.setJobOfferSkills(jobOfferSkills);
        //joboffer requires skill1, skill2 and skill3
        when(jobOfferRepo.findById(jobOffer.getId())).thenReturn(Optional.of(jobOffer));
        when(applicantRepo.findAll()).thenReturn(applicants);


        Assertions.assertThrows(JobOfferNotFoundException.class, () -> {
                    matchServiceImpl.addPartiallyMatch(2);
                }
        );


    }


    @Test
    void addPartiallyMatchInactiveJobOffer() throws JobOfferNotFoundException, JobOfferAlreadyClosed {

        Applicant applicant1 = new Applicant();
        applicant1.setId(1);
        applicant1.setInactive(false);

        Applicant applicant2 = new Applicant();
        applicant2.setId(2);
        applicant2.setInactive(false);

        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant1);
        applicants.add(applicant2);

        Skill skill1 = new Skill();
        skill1.setName("Java");
        skill1.setLevels("Senior");
        Skill skill2 = new Skill();
        skill2.setName("Spring");
        skill2.setLevels("Mid");
        Skill skill3 = new Skill();
        skill3.setName("Test");
        skill3.setLevels("Junior");

        ApplicantSkill applicantSkill1 = new ApplicantSkill();
        applicantSkill1.setSkill(skill1);
        ApplicantSkill applicantSkill2 = new ApplicantSkill();
        applicantSkill2.setSkill(skill2);
        ApplicantSkill applicantSkill3 = new ApplicantSkill();
        applicantSkill3.setSkill(skill3);

        List<ApplicantSkill> applicantSkills1 = new ArrayList<ApplicantSkill>();
        applicantSkills1.add(applicantSkill1);
        applicantSkills1.add(applicantSkill2);
        applicant1.setApplicantSkills(applicantSkills1);
        // applicant1 has skill1 and skill2

        List<ApplicantSkill> applicantSkills2 = new ArrayList<ApplicantSkill>();
        applicantSkills2.add(applicantSkill3);
        ApplicantSkill applicantSkill4 = new ApplicantSkill();
        applicantSkill4.setSkill(skill1);
        applicantSkills2.add(applicantSkill4);
        applicant2.setApplicantSkills(applicantSkills2);

        //applicant 2 has skill1 and skill3

        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(true);
        jobOffer.setOfferDate(LocalDate.now());
        JobOfferSkill jobOfferSkill1 = new JobOfferSkill();
        jobOfferSkill1.setSkill(skill1);
        JobOfferSkill jobOfferSkill2 = new JobOfferSkill();
        jobOfferSkill2.setSkill(skill2);
        JobOfferSkill jobOfferSkill3 = new JobOfferSkill();
        jobOfferSkill3.setSkill(skill3);
        List<JobOfferSkill> jobOfferSkills = new ArrayList<>();
        jobOfferSkills.add(jobOfferSkill1);
        jobOfferSkills.add(jobOfferSkill2);
        jobOfferSkills.add(jobOfferSkill3);
        jobOffer.setJobOfferSkills(jobOfferSkills);
        //joboffer requires skill1, skill2 and skill3

        when(jobOfferRepo.findById(jobOffer.getId())).thenReturn(Optional.of(jobOffer));
        when(applicantRepo.findAll()).thenReturn(applicants);


        Assertions.assertThrows(JobOfferAlreadyClosed.class, () -> {
                    matchServiceImpl.addPartiallyMatch(1);
                }
        );


    }


    @Test
    void addAutomaticMatch() throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        Applicant applicant1 = new Applicant();
        applicant1.setId(1);
        applicant1.setInactive(false);

        Applicant applicant2 = new Applicant();
        applicant2.setId(2);
        applicant2.setInactive(false);

        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant1);
        applicants.add(applicant2);

        Skill skill1 = new Skill();
        skill1.setName("Java");
        skill1.setLevels("Senior");
        Skill skill2 = new Skill();
        skill2.setName("Spring");
        skill2.setLevels("Mid");
        Skill skill3 = new Skill();
        skill3.setName("Test");
        skill3.setLevels("Junior");

        ApplicantSkill applicantSkill1 = new ApplicantSkill();
        applicantSkill1.setSkill(skill1);
        ApplicantSkill applicantSkill2 = new ApplicantSkill();
        applicantSkill2.setSkill(skill2);
        ApplicantSkill applicantSkill3 = new ApplicantSkill();
        applicantSkill3.setSkill(skill3);

        List<ApplicantSkill> applicantSkills1 = new ArrayList<ApplicantSkill>();
        applicantSkills1.add(applicantSkill1);
        applicantSkills1.add(applicantSkill2);
        applicant1.setApplicantSkills(applicantSkills1);
        // applicant1 has skill1 and skill2

        List<ApplicantSkill> applicantSkills2 = new ArrayList<ApplicantSkill>();
        applicantSkills2.add(applicantSkill3);
        ApplicantSkill applicantSkill4 = new ApplicantSkill();
        applicantSkill4.setSkill(skill1);
        ApplicantSkill applicantSkill5 = new ApplicantSkill();
        applicantSkill5.setSkill(skill2);
        applicantSkills2.add(applicantSkill4);
        applicantSkills2.add(applicantSkill5);
        applicant2.setApplicantSkills(applicantSkills2);

        //applicant 2 has skill1, skill2 and skill3


        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(false);
        jobOffer.setOfferDate(LocalDate.now());
        JobOfferSkill jobOfferSkill1 = new JobOfferSkill();
        jobOfferSkill1.setSkill(skill1);
        JobOfferSkill jobOfferSkill2 = new JobOfferSkill();
        jobOfferSkill2.setSkill(skill2);
        JobOfferSkill jobOfferSkill3 = new JobOfferSkill();
        jobOfferSkill3.setSkill(skill3);
        List<JobOfferSkill> jobOfferSkills = new ArrayList<>();
        jobOfferSkills.add(jobOfferSkill1);
        jobOfferSkills.add(jobOfferSkill2);
        jobOfferSkills.add(jobOfferSkill3);
        jobOffer.setJobOfferSkills(jobOfferSkills);
        //joboffer requires skill1, skill2 and skill3

        when(jobOfferRepo.findById(jobOffer.getId())).thenReturn(Optional.of(jobOffer));
        when(applicantRepo.findAll()).thenReturn(applicants);


        List<Match> autoMatch = matchServiceImpl.addAutomaticMatch(1);
        assertEquals(1, autoMatch.size());
    }

    @Test
    void addAutomaticMatchInvalidJobOffer() throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        Applicant applicant1 = new Applicant();
        applicant1.setId(1);
        applicant1.setInactive(false);

        Applicant applicant2 = new Applicant();
        applicant2.setId(2);
        applicant2.setInactive(false);

        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant1);
        applicants.add(applicant2);

        Skill skill1 = new Skill();
        skill1.setName("Java");
        skill1.setLevels("Senior");
        Skill skill2 = new Skill();
        skill2.setName("Spring");
        skill2.setLevels("Mid");
        Skill skill3 = new Skill();
        skill3.setName("Test");
        skill3.setLevels("Junior");

        ApplicantSkill applicantSkill1 = new ApplicantSkill();
        applicantSkill1.setSkill(skill1);
        ApplicantSkill applicantSkill2 = new ApplicantSkill();
        applicantSkill2.setSkill(skill2);
        ApplicantSkill applicantSkill3 = new ApplicantSkill();
        applicantSkill3.setSkill(skill3);

        List<ApplicantSkill> applicantSkills1 = new ArrayList<ApplicantSkill>();
        applicantSkills1.add(applicantSkill1);
        applicantSkills1.add(applicantSkill2);
        applicant1.setApplicantSkills(applicantSkills1);
        // applicant1 has skill1 and skill2

        List<ApplicantSkill> applicantSkills2 = new ArrayList<ApplicantSkill>();
        applicantSkills2.add(applicantSkill3);
        ApplicantSkill applicantSkill4 = new ApplicantSkill();
        applicantSkill4.setSkill(skill1);
        ApplicantSkill applicantSkill5 = new ApplicantSkill();
        applicantSkill5.setSkill(skill2);
        applicantSkills2.add(applicantSkill4);
        applicantSkills2.add(applicantSkill5);
        applicant2.setApplicantSkills(applicantSkills2);

        //applicant 2 has skill1, skill2 and skill3

        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(false);
        jobOffer.setOfferDate(LocalDate.now());
        JobOfferSkill jobOfferSkill1 = new JobOfferSkill();
        jobOfferSkill1.setSkill(skill1);
        JobOfferSkill jobOfferSkill2 = new JobOfferSkill();
        jobOfferSkill2.setSkill(skill2);
        JobOfferSkill jobOfferSkill3 = new JobOfferSkill();
        jobOfferSkill3.setSkill(skill3);
        List<JobOfferSkill> jobOfferSkills = new ArrayList<>();
        jobOfferSkills.add(jobOfferSkill1);
        jobOfferSkills.add(jobOfferSkill2);
        jobOfferSkills.add(jobOfferSkill3);
        jobOffer.setJobOfferSkills(jobOfferSkills);
        //joboffer requires skill1, skill2 and skill3
        when(jobOfferRepo.findById(jobOffer.getId())).thenReturn(Optional.of(jobOffer));
        when(applicantRepo.findAll()).thenReturn(applicants);


        Assertions.assertThrows(JobOfferNotFoundException.class, () -> {
            matchServiceImpl.addAutomaticMatch(2);
        });
    }

    @Test
    void addAutomaticMatchInactiveJobOffer() throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        Applicant applicant1 = new Applicant();
        applicant1.setId(1);
        applicant1.setInactive(false);

        Applicant applicant2 = new Applicant();
        applicant2.setId(2);
        applicant2.setInactive(false);

        List<Applicant> applicants = new ArrayList<>();
        applicants.add(applicant1);
        applicants.add(applicant2);

        Skill skill1 = new Skill();
        skill1.setName("Java");
        skill1.setLevels("Senior");
        Skill skill2 = new Skill();
        skill2.setName("Spring");
        skill2.setLevels("Mid");
        Skill skill3 = new Skill();
        skill3.setName("Test");
        skill3.setLevels("Junior");

        ApplicantSkill applicantSkill1 = new ApplicantSkill();
        applicantSkill1.setSkill(skill1);
        ApplicantSkill applicantSkill2 = new ApplicantSkill();
        applicantSkill2.setSkill(skill2);
        ApplicantSkill applicantSkill3 = new ApplicantSkill();
        applicantSkill3.setSkill(skill3);

        List<ApplicantSkill> applicantSkills1 = new ArrayList<ApplicantSkill>();
        applicantSkills1.add(applicantSkill1);
        applicantSkills1.add(applicantSkill2);
        applicant1.setApplicantSkills(applicantSkills1);
        // applicant1 has skill1 and skill2

        List<ApplicantSkill> applicantSkills2 = new ArrayList<ApplicantSkill>();
        applicantSkills2.add(applicantSkill3);
        ApplicantSkill applicantSkill4 = new ApplicantSkill();
        applicantSkill4.setSkill(skill1);
        ApplicantSkill applicantSkill5 = new ApplicantSkill();
        applicantSkill5.setSkill(skill2);
        applicantSkills2.add(applicantSkill4);
        applicantSkills2.add(applicantSkill5);
        applicant2.setApplicantSkills(applicantSkills2);

        //applicant 2 has skill1, skill2 and skill3

        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setInactive(true);
        jobOffer.setOfferDate(LocalDate.now());
        JobOfferSkill jobOfferSkill1 = new JobOfferSkill();
        jobOfferSkill1.setSkill(skill1);
        JobOfferSkill jobOfferSkill2 = new JobOfferSkill();
        jobOfferSkill2.setSkill(skill2);
        JobOfferSkill jobOfferSkill3 = new JobOfferSkill();
        jobOfferSkill3.setSkill(skill3);
        List<JobOfferSkill> jobOfferSkills = new ArrayList<>();
        jobOfferSkills.add(jobOfferSkill1);
        jobOfferSkills.add(jobOfferSkill2);
        jobOfferSkills.add(jobOfferSkill3);
        jobOffer.setJobOfferSkills(jobOfferSkills);
        //joboffer requires skill1, skill2 and skill3

        when(jobOfferRepo.findById(jobOffer.getId())).thenReturn(Optional.of(jobOffer));
        when(applicantRepo.findAll()).thenReturn(applicants);


        Assertions.assertThrows(JobOfferAlreadyClosed.class, () -> {
            matchServiceImpl.addAutomaticMatch(1);
        });
    }

    @Test
    void getMatch() throws MatchNotFoundException {
        Match match = new Match();
        match.setId(1);
        when(matchRepo.findById(match.getId())).thenReturn(Optional.of(match));
        assertEquals(1, matchServiceImpl.getMatch(1).getId());
    }

    @Test
    void getMatchInvalidId() throws MatchNotFoundException {
        Match match = new Match();
        match.setId(1);
        when(matchRepo.findById(match.getId())).thenReturn(Optional.of(match));
        Assertions.assertThrows(MatchNotFoundException.class, () -> {
            matchServiceImpl.getMatch(2);
        });
    }


    @Test
    void getMatches() {
    }

    @Test
    void finalizeMatch() throws JobOfferAlreadyClosed, MatchAlreadyFinalized, ApplicantAlreadyClosed, MatchNotFoundException {

        Match match = new Match();
        match.setId(1);
        Applicant applicant = new Applicant();
        match.setApplicant(applicant);
        JobOffer jobOffer = new JobOffer();
        match.setJobOffer(jobOffer);
        when(matchRepo.findById(match.getId())).thenReturn(Optional.of(match));
        matchServiceImpl.finalizeMatch(1);
        assertEquals(true, match.isFinalized());
    }

    @Test
    void finalizeMatchError() throws JobOfferAlreadyClosed, MatchAlreadyFinalized, ApplicantAlreadyClosed, MatchNotFoundException {

        Match match = new Match();
        match.setId(1);
        Applicant applicant = new Applicant();
        match.setApplicant(applicant);
        JobOffer jobOffer = new JobOffer();
        match.setJobOffer(jobOffer);
        match.setFinalized(true);
        when(matchRepo.findById(match.getId())).thenReturn(Optional.of(match));
        Assertions.assertThrows(MatchAlreadyFinalized.class, () -> {
            matchServiceImpl.finalizeMatch(1);
        });
    }

    @Test
    void finalizeMatchInactiveJob() throws JobOfferAlreadyClosed, MatchAlreadyFinalized, ApplicantAlreadyClosed, MatchNotFoundException {

        Match match = new Match();
        match.setId(1);
        Applicant applicant = new Applicant();
        match.setApplicant(applicant);
        JobOffer jobOffer = new JobOffer();
        jobOffer.setInactive(true);
        match.setJobOffer(jobOffer);
        when(matchRepo.findById(match.getId())).thenReturn(Optional.of(match));
        Assertions.assertThrows(JobOfferAlreadyClosed.class, () -> {
            matchServiceImpl.finalizeMatch(1);
        });
    }

    @Test
    void finalizeMatchInactiveApplicant() throws JobOfferAlreadyClosed, MatchAlreadyFinalized, ApplicantAlreadyClosed, MatchNotFoundException {

        Match match = new Match();
        match.setId(1);
        Applicant applicant = new Applicant();
        applicant.setInactive(true);
        match.setApplicant(applicant);
        JobOffer jobOffer = new JobOffer();
        match.setJobOffer(jobOffer);
        when(matchRepo.findById(match.getId())).thenReturn(Optional.of(match));
        Assertions.assertThrows(ApplicantAlreadyClosed.class, () -> {
            matchServiceImpl.finalizeMatch(1);
        });
    }



}