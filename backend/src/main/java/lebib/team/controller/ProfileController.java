package lebib.team.controller;

import lebib.team.dto.ProfileDto;
import lebib.team.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user/profiles/{profileId}")
@CrossOrigin("*")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(@PathVariable Long profileId) {
        profileService.deleteProfile(profileId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/avatar")
    public ResponseEntity<Void> deleteProfileAvatar(@PathVariable Long profileId) {
        profileService.deleteProfileAvatar(profileId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/bio")
    public ResponseEntity<ProfileDto> updateProfileBio(
            @PathVariable Long profileId,
            @RequestParam String newBio
    ) {
        return ResponseEntity.ok(profileService.updateProfileBio(profileId, newBio));
    }

    @PutMapping("/avatar")
    public ResponseEntity<ProfileDto> updateProfileAvatar(
            @PathVariable Long profileId,
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(profileService.updateProfileAvatar(profileId, file));
    }
}
