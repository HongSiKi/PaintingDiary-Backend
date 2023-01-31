package com.paintingdiary.backend.service;

import com.paintingdiary.backend.exception.CommonException;
import com.paintingdiary.backend.model.dto.character.CharacterCreateRequestDTO;
import com.paintingdiary.backend.model.dto.character.CharacterUpdateRequestDTO;
import com.paintingdiary.backend.model.entity.Skill;
import com.paintingdiary.backend.model.entity.User;
import com.paintingdiary.backend.model.entity.UserCharacter;
import com.paintingdiary.backend.repository.CharacterRepository;
import com.paintingdiary.backend.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final SkillRepository skillRepository;

    public UserCharacter getCharacter(User user) {
        UserCharacter character = characterRepository.findByUser(user).orElse(null);

        if (character != null) {
            List<Skill> skillList = skillRepository.findAllByCharacter(character);
            character.setSkillList(skillList);
        }

        return character;
    }

    public Page<UserCharacter> getCharacterList(int page, int size) {
        Page<UserCharacter> characterPage = characterRepository.findAll(PageRequest.of(page, size));

        characterPage.getContent().forEach(character -> {
            List<Skill> skillList = skillRepository.findAllByCharacter(character);
            character.setSkillList(skillList);
        });

        return characterPage;
    }

    public void deleteCharacter(User user) {
        UserCharacter character = characterRepository.findByUser(user)
                .orElseThrow(() -> CommonException.ITEM_NOT_FOUND);

        List<Skill> skillList = skillRepository.findAllByCharacter(character);
        if (!skillList.isEmpty()) {
            skillRepository.deleteAll(skillList);
        }

        characterRepository.delete(character);
    }

    public UserCharacter createCharacter(User user, CharacterCreateRequestDTO createRequest) {
        // TODO: 추후 제약 조건 활성화
//        characterRepository.findByUser(user)
//                .ifPresent(ignored -> {
//                    throw CommonException.ALREADY_REGISTERED_CHARACTER;
//                });

//        characterRepository.findByNickname(createRequest.getNickname())
//                .ifPresent(ignored -> {
//                    throw CommonException.ALREADY_REGISTERED_NICKNAME;
//                });

        UserCharacter character = new UserCharacter();
        character.setUser(user);
        character.setNickname(createRequest.getNickname());
        character.setLink(createRequest.getLink());
        characterRepository.save(character);

        List<Skill> skillList = createRequest.getSkillList().stream()
                .map(skillDTO -> {
                    Skill skill = new Skill();
                    skill.setTitle(skillDTO.title());
                    skill.setDescription(skillDTO.description());
                    skill.setProgress(skillDTO.progress());
                    skill.setCharacter(character);

                    return skill;
                })
                .toList();


        if (!skillList.isEmpty()) {
            character.setSkillList(skillList);
            skillRepository.saveAll(skillList);
        }

        return character;
    }

    public UserCharacter updateCharacter(User user, CharacterUpdateRequestDTO updateRequest) {
        UserCharacter character = characterRepository.findByUser(user)
                .orElseThrow(() -> CommonException.ITEM_NOT_FOUND);

        if (updateRequest.getNickname() != null && !updateRequest.getNickname().isBlank()) {
            characterRepository.findByNickname(updateRequest.getNickname())
                    .ifPresent(ignored -> {
                        throw CommonException.ALREADY_REGISTERED_NICKNAME;
                    });

            character.setNickname(updateRequest.getNickname());
        }

        if (updateRequest.getLink() != null && !updateRequest.getLink().isBlank()) {
            character.setLink(updateRequest.getLink());
        }

        List<Skill> notDeletedSkillList = new ArrayList<>();
        List<Skill> existedSkillList = skillRepository.findAllByCharacter(character);
        List<Skill> newSkillList = updateRequest.getSkillList().stream()
                .map(skillDTO -> {
                    Skill skill = new Skill();
                    skill.setTitle(skillDTO.title());
                    skill.setDescription(skillDTO.description());
                    skill.setProgress(skillDTO.progress());
                    skill.setCharacter(character);

                    int idx = existedSkillList.indexOf(skill);
                    if (idx != -1) {
                        Skill existedSkill = existedSkillList.get(idx);
                        skill.setId(existedSkill.getId());
                        skill.setCreateDt(existedSkill.getCreateDt());

                        notDeletedSkillList.add(existedSkill);
                    }

                    return skill;
                })
                .toList();

        List<Skill> deleteSkillList = existedSkillList.stream()
                .filter(skill -> !notDeletedSkillList.contains(skill))
                .toList();

        if (!newSkillList.isEmpty()) {
            character.setSkillList(newSkillList);
            skillRepository.saveAll(newSkillList);
        }

        if (!deleteSkillList.isEmpty()) {
            skillRepository.deleteAll(deleteSkillList);
        }

        characterRepository.save(character);

        return character;
    }

    public boolean hasCharacter(User user) {
        return characterRepository.findByUser(user).isPresent();
    }

    public boolean isDuplicatedNickname(String nickname) {
        return characterRepository.findByNickname(nickname).isPresent();
    }
}
