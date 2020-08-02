package gr.codehub.rsapi.controller.centralrepo;

import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.SkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Slf4j
public class SkillController {
    private SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("skill")
    public List<Skill> getSkills() {
        log.info("\nGET REQUEST: Calling getSkills method");
        return skillService.getSkills();
    }

    @PostMapping("skill")
    public Skill addSkill(@RequestBody Skill skill) {
        log.info("\nPOST REQUEST: Calling addSkill method");
        return skillService.addSkill(skill);
    }

    @PutMapping("skill/{id}")
    public Skill updateSkill(@RequestBody Skill skill, @PathVariable long id) throws SkillNotFoundException {
        log.info("\nPUT REQUEST:Calling updateSkill method");
        return skillService.updateSkill(skill, id);
    }

    @DeleteMapping("skill/{id}")
    public Boolean deleteSkill(@PathVariable long id) throws SkillNotFoundException {
        log.info("\nDELETE REQUEST: Clling deleteSkill method");
        return skillService.deleteSkill(id);
    }

}
