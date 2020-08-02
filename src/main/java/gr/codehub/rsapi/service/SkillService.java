package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Skill;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;

public interface SkillService {
    /**
     * finds all skills from the skill table on DB
     * @return  {@code List<Skill>}
     */
    List<Skill> getSkills();

    /**
     * adds a new specific skill on DB
     * @param skill
     * @return {@code Skill}
     */
    Skill addSkill(Skill skill);

    /**
     * Updated the skill with the specified ID
     * @param skill
     * @param skillId
     * @return {@code Skill}
     * @throws SkillNotFoundException
     */
    Skill updateSkill(Skill skill, long skillId) throws SkillNotFoundException;

    /**
     * Initialize the skill table in database with skills
     * @return {@code List<Skill>}
     * @throws IOException
     * @throws InvalidFormatException
     */
    List<Skill> readSkills() throws IOException,InvalidFormatException;

    /**
     * @param skillId
     * @return {@code true on success}
     * @throws SkillNotFoundException
     */
    boolean deleteSkill(long skillId) throws SkillNotFoundException;
}
