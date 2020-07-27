package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.SkillRepo;
import gr.codehub.rsapi.utility.FileReaderToList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements  SkillService {


    private SkillRepo skillRepo;

    @Autowired
    public SkillServiceImpl(SkillRepo skillRepo){
        this.skillRepo = skillRepo;
    }

    @Override
    public List<Skill> getSkills() {
        return skillRepo.findAll();
    }


    @Override
    public Skill addSkill(Skill skill) {
        return skillRepo.save(skill);
    }

    @Override
    public Skill updateSkill(Skill skill, long skillId) throws SkillNotFoundException {
        Skill skillInDb;
        Optional<Skill> optionalSkill = skillRepo.findById(skillId);
        if(optionalSkill.isPresent()){
            skillInDb = optionalSkill.get();
            if(skill.getName()!=null){
                skillInDb.setName(skill.getName());
            }
            skillRepo.save(skillInDb);
            return skillInDb;
        }else throw new SkillNotFoundException("not such skill exists");

    }

    @Override
    public boolean deleteSkill(long skillIndex) throws SkillNotFoundException {
        Skill skillInDb;
        Optional<Skill> optionalSkill = skillRepo.findById(skillIndex);
        if (optionalSkill.isPresent()){
            skillInDb = optionalSkill.get();
            skillRepo.deleteById(skillIndex);
            return true;

        }
        else throw new SkillNotFoundException("not such skill exists");
    }

    @Override
    public List<Skill> readSkills() throws IOException, InvalidFormatException {
        return FileReaderToList.readFromExcelSkills("data.xlsx",skillRepo);
    }
}
