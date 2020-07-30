package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.JobOfferAlreadyClosed;
import gr.codehub.rsapi.exception.JobOfferCreationException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.repository.JobOfferRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class JobOfferServiceImplTest {

//    @Autowired
    @InjectMocks
    private JobOfferServiceImpl jobOfferServiceImpl;

    @Mock
    private JobOfferRepo jobOfferRepo;

    @Test
    void getJobOffers() {
    }

    @Test
    void addJobOffer() throws JobOfferCreationException {
        JobOffer jobOffer = new JobOffer();
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        //given
        when( jobOfferRepo.save(jobOffer)).thenReturn(jobOffer);
        when( jobOfferRepo.findAll()).thenReturn(jobOffers);
        jobOffer.setCompany("Accenture");
        jobOffer.setRegion("Athens");
        jobOffer.setTitle("Junior Java Developer");
        jobOffer.setClosed(false);
        jobOffer.setOfferDate(new Date());
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
        jobOffer.setClosed(false);
        jobOffer.setOfferDate(new Date());
        jobOfferServiceImpl.updateJobOffer(jobOffer,1);
        List<JobOffer> jobOffers1 = jobOfferServiceImpl.getJobOffers();
        //assertArrayEquals(jobOfferServiceImpl.getJobOffer("1"),jobOffer);
    }

    private void assertArrayEquals(JobOffer jobOffer, JobOffer jobOffer1) {
    }

    @Test
    void deleteJobOffer() throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        JobOffer jobOffer = new JobOffer();
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(jobOffer);
        //given
        when(jobOfferRepo.save(jobOffer)).thenReturn(jobOffer);
        when( jobOfferRepo.findById((long)1)).thenReturn(Optional.of(jobOffer));
        jobOfferServiceImpl.deleteJobOffer(1);
       // assertArrayEquals1(jobOffers.get(0).getClosed(),true);
    }

    private void assertArrayEquals1(Boolean isClosed, boolean b) {
    }


    @Test
    void getJobOffer() {
    }

    @Test
    void getSelectedJobOffers() {
    }

    @Test
    void addSkillToJobOffer() {
    }

    @Test
    void readJobOffers() {
    }
}