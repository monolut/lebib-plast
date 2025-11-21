package lebib.team.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lebib.team.dto.ProfileDto;
import lebib.team.entity.ProfileEntity;
import lebib.team.entity.UserEntity;
import lebib.team.exception.ProfileNotFoundException;
import lebib.team.mapper.ProfileMapper;
import lebib.team.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Mock
    private AmazonS3 s3Client;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(profileService, "bucketName", "test-bucket");
    }

    @Test
    void createProfileForUser_success() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ProfileEntity result = profileService.createProfileForUser(user);

        assertEquals(user, result.getUser());
    }

    @Test
    void deleteProfile_success() {
        ProfileEntity profile = new ProfileEntity();
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));

        profileService.deleteProfile(1L);

        verify(profileRepository).deleteById(1L);
    }

    @Test
    void deleteProfile_notFound() {
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProfileNotFoundException.class, () -> profileService.deleteProfile(1L));
    }

    @Test
    void updateProfileBio_success() {
        ProfileEntity profile = new ProfileEntity();
        ProfileDto dto = new ProfileDto();

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(profileMapper.toDto(profile)).thenReturn(dto);

        ProfileDto result = profileService.updateProfileBio(1L, "Hello");

        assertNotNull(result);
        assertEquals("Hello", profile.getBio());
        verify(profileRepository).save(profile);
    }

    @Test
    void updateProfileBio_notFound() {
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProfileNotFoundException.class, () -> profileService.updateProfileBio(1L, "Bio"));
    }

    @Test
    void updateProfileAvatar_success() throws IOException {
        ProfileEntity profile = new ProfileEntity();
        profile.setId(1L);
        profile.setAvatarUrl(null);

        ProfileDto dto = new ProfileDto();

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(file.getOriginalFilename()).thenReturn("avatar.png");
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(10L);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3}));

        when(s3Client.getUrl("test-bucket", "avatars/1/avatar.png"))
                .thenReturn(new java.net.URL("http://s3/test/avatars/1/avatar.png"));

        when(profileMapper.toDto(profile)).thenReturn(dto);
        when(profileRepository.save(profile)).thenReturn(profile);

        ProfileDto result = profileService.updateProfileAvatar(1L, file);

        assertNotNull(result);
        assertEquals("http://s3/test/avatars/1/avatar.png", profile.getAvatarUrl());
        verify(s3Client).putObject(eq("test-bucket"), eq("avatars/1/avatar.png"), any(), any(ObjectMetadata.class));
        verify(profileRepository).save(profile);
    }

    @Test
    void updateProfileAvatar_deleteOldAvatar() throws IOException {
        ProfileEntity profile = new ProfileEntity();
        profile.setId(1L);
        profile.setAvatarUrl("https://some.com/avatars/1/old.png");

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(file.getOriginalFilename()).thenReturn("new.png");
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(5L);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1}));

        when(s3Client.doesObjectExist(any(), any())).thenReturn(true);
        when(s3Client.getUrl("test-bucket", "avatars/1/new.png"))
                .thenReturn(new java.net.URL("http://s3/test/avatars/1/new.png"));

        when(profileMapper.toDto(any())).thenReturn(new ProfileDto());

        profileService.updateProfileAvatar(1L, file);

        verify(s3Client).deleteObject("test-bucket", "avatars/1/old.png");
        verify(s3Client).putObject(eq("test-bucket"), eq("avatars/1/new.png"), any(), any(ObjectMetadata.class));
    }

    @Test
    void updateProfileAvatar_profileNotFound() {
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProfileNotFoundException.class, () -> profileService.updateProfileAvatar(1L, file));
    }

    @Test
    void deleteProfileAvatar_success() {
        ProfileEntity profile = new ProfileEntity();
        profile.setId(1L);
        profile.setAvatarUrl("https://aws/avatars/1/img.png");

        ProfileDto dto = new ProfileDto();

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(profileMapper.toDto(profile)).thenReturn(dto);
        when(s3Client.doesObjectExist(any(), any())).thenReturn(true);

        ProfileDto result = profileService.deleteProfileAvatar(1L);

        assertNull(profile.getAvatarUrl());
        verify(s3Client).deleteObject(eq("test-bucket"), eq("avatars/1/img.png"));
        verify(profileRepository).save(profile);
        assertNotNull(result);
    }

    @Test
    void deleteProfileAvatar_noAvatar() {
        ProfileEntity profile = new ProfileEntity();
        profile.setId(1L);
        profile.setAvatarUrl(null);

        ProfileDto dto = new ProfileDto();

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(profileMapper.toDto(profile)).thenReturn(dto);

        ProfileDto result = profileService.deleteProfileAvatar(1L);

        assertNotNull(result);
        assertNull(profile.getAvatarUrl());
        verify(profileRepository, never()).save(any());
    }

    @Test
    void deleteProfileAvatar_profileNotFound() {
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProfileNotFoundException.class, () -> profileService.deleteProfileAvatar(1L));
    }
}
