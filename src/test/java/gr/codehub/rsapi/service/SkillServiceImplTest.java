package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.JobOffer;;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.SkillRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SkillServiceImplTest {

    @InjectMocks
    private SkillServiceImpl skillServiceImpl;

    @Mock
    private SkillRepo skillRepo;


    @Test
    void addSkill() {
        Skill skill = new Skill();
        skill.setId(1);
        skill.setName("Java");
        skill.setLevels("Mid");
        when( skillRepo.save(skill)).thenReturn(skill);
        Skill skill1 =skillServiceImpl.addSkill(skill);
        assertEquals(skill,skill1);
    }

    @Test
    void deleteSkillSkillNotFoundException(){
    }
}