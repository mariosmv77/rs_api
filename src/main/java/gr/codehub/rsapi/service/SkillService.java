package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Skill;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;

public interface SkillService {
    List<Skill> getSkills();
    Skill addSkill(Skill skill);
    Skill updateSkill(Skill skill, long skillId) throws SkillNotFoundException;
    List<Skill> readSkills() throws IOException,InvalidFormatException;
    boolean deleteSkill(long skillIndex) throws SkillNotFoundException;
}
