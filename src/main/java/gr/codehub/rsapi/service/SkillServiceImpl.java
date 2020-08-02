package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.SkillRepo;
import gr.codehub.rsapi.utility.FileReaderToList;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class SkillServiceImpl implements SkillService {
    private SkillRepo skillRepo;

    @Autowired
    public SkillServiceImpl(SkillRepo skillRepo){
        this.skillRepo = skillRepo;
    }

    @Override
    public List<Skill> getSkills() {
        log.info("\nEnter getSkills method");
        log.info("\nExits getSkills method after returns all skills");
        return skillRepo.findAll();
    }

    @Override
    public Skill addSkill(Skill skill) {
        log.info("\nEnter addSkill method");
        log.info("\nExits addSkill method after added skill with name: " + skill.getName());
        return skillRepo.save(skill);
    }

    @Override
    public Skill updateSkill(Skill skill, long skillId) throws SkillNotFoundException {
        log.info("\nEnter updateApplicant method");
        Skill skillInDb;
        Optional<Skill> optionalSkill = skillRepo.findById(skillId);
        if(optionalSkill.isPresent()){
            skillInDb = optionalSkill.get();
            if(skill.getName()!=null){
                skillInDb.setName(skill.getName());
            }
            skillRepo.save(skillInDb);
            log.info("\nExits updateApplicant, after update a skill with SkillId : " + skillId);
            return skillInDb;
        }else throw new SkillNotFoundException("not such skill exists");
    }

    @Override
    public boolean deleteSkill(long skillId) throws SkillNotFoundException {
        log.info("\nEnter deleteSkill");
        Optional<Skill> optionalSkill = skillRepo.findById(skillId);
        if (optionalSkill.isPresent()) {
            skillRepo.deleteById(skillId);
            log.info("\nExits deleteSkill,after deleting skill with the index: " + skillId);
            return true;
        }
        else throw new SkillNotFoundException("not such skill exists");
    }

    @Override
    public List<Skill> readSkills() throws IOException, InvalidFormatException {
        log.info("\nStart readSkills From Excel File");
        log.info("\nExits readSkills From Excel File after successfully read it");
        return FileReaderToList.readFromExcelSkills("data.xlsx", skillRepo);
    }
}
