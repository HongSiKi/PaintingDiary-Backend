package com.paintingdiary.backend.controller;

import com.paintingdiary.backend.annotation.AuthResult;
import com.paintingdiary.backend.exception.CommonException;
import com.paintingdiary.backend.model.dto.BaseResponse;
import com.paintingdiary.backend.model.dto.PageDTO;
import com.paintingdiary.backend.model.dto.character.CharacterCreateRequestDTO;
import com.paintingdiary.backend.model.dto.character.CharacterDTO;
import com.paintingdiary.backend.model.dto.character.CharacterUpdateRequestDTO;
import com.paintingdiary.backend.model.entity.User;
import com.paintingdiary.backend.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/characters")
@RestController
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping
    public BaseResponse getCharacter(@RequestParam(defaultValue = "false") boolean me, @RequestParam(defaultValue = "0") int page, @AuthResult(isRequired = false) User user) {
        if (me) {
            if (user == null) {
                throw CommonException.UNAUTHORIZED;
            }

            CharacterDTO character = CharacterDTO.of(characterService.getCharacter(user));

            return BaseResponse.of(character);
        } else {
            if (page < 0) {
                throw CommonException.INVALID_PARAMETER;
            }

            final int characterSize = 25;
            PageDTO<CharacterDTO> result = PageDTO.of(characterService.getCharacterList(page, characterSize), page, characterSize, CharacterDTO::of);

            return BaseResponse.of(result);
        }
    }

    @PostMapping
    public BaseResponse createCharacter(@AuthResult User user, @Valid @RequestBody CharacterCreateRequestDTO createRequest) {

        CharacterDTO character = CharacterDTO.of(characterService.createCharacter(user, createRequest));

        return BaseResponse.of(character);
    }

    @PutMapping
    public BaseResponse updateCharacter(@AuthResult User user, @Valid @RequestBody CharacterUpdateRequestDTO updateRequest) {

        CharacterDTO character = CharacterDTO.of(characterService.updateCharacter(user, updateRequest));

        return BaseResponse.of(character);
    }

    @DeleteMapping
    public BaseResponse deleteCharacter(@AuthResult User user) {
        characterService.deleteCharacter(user);

        return BaseResponse.of();
    }

}
