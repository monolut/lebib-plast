package lebib.team.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lebib.team.dto.ProfileDto;
import lebib.team.entity.ProfileEntity;
import lebib.team.entity.UserEntity;
import lebib.team.exception.ProfileNotFoundException;
import lebib.team.mapper.ProfileMapper;
import lebib.team.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Transactional(readOnly = true)
@Service
public class ProfileService {

    @Value("${aws.bucket-name}")
    private String bucketName;
    private final AmazonS3 s3Client;

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Autowired
    public ProfileService(
            ProfileRepository profileRepository,
            ProfileMapper profileMapper,
            AmazonS3 s3Client
    ) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.s3Client = s3Client;
    }

    @Transactional
    public ProfileEntity createProfileForUser(UserEntity user) {
        ProfileEntity profile = new ProfileEntity();
        profile.setUser(user);
        return profile;
    }

    @Transactional
    public void deleteProfile(Long id) {
        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));

        profileRepository.deleteById(id);
    }

    @Transactional
    public ProfileDto updateProfileBio(Long id, String newBio) {
        ProfileEntity profileToUpdate = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));

        profileToUpdate.setBio(newBio);

        profileRepository.save(profileToUpdate);

        return profileMapper.toDto(profileToUpdate);
    }

    @Transactional
    public ProfileDto updateProfileAvatar(Long profileId, MultipartFile file) {
        ProfileEntity profileToUpdate = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));

        String avatarUrl = profileToUpdate.getAvatarUrl();

        if (avatarUrl != null) {
            deleteAvatarFromS3(avatarUrl);
        }

        String folder = "avatars/" + profileId + "/";
        String key = folder + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            s3Client.putObject(bucketName, key, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload avatar to S3", e);
        }

        avatarUrl = s3Client.getUrl(bucketName, key).toString();
        profileToUpdate.setAvatarUrl(avatarUrl);

        profileRepository.save(profileToUpdate);

        return profileMapper.toDto(profileToUpdate);
    }

    @Transactional
    public ProfileDto deleteProfileAvatar(Long profileId) {
        ProfileEntity profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));

        String avatarUrl = profile.getAvatarUrl();

        if (avatarUrl == null || avatarUrl.isBlank()) {
            return profileMapper.toDto(profile);
        }

        deleteAvatarFromS3(avatarUrl);

        profile.setAvatarUrl(null);
        profileRepository.save(profile);

        return profileMapper.toDto(profile);
    }

    private void deleteAvatarFromS3(String avatarUrl) {
        int index = avatarUrl.indexOf("avatars/");

        if (index == -1) {
            System.out.println("Invalid url " + avatarUrl);
            return;
        }

        String key = avatarUrl.substring(index);

        if (s3Client.doesObjectExist(bucketName, key)) {
            s3Client.deleteObject(bucketName, key);
            System.out.println("Deleted " + key);
        } else System.out.println("Avatar not found " + key);;
    }
}
