package com.jobfair.docs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.reset;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jobfair.api.controller.ArticleController;
import com.jobfair.api.controller.ArticleImageController;
import com.jobfair.api.controller.CommitteeController;
import com.jobfair.api.controller.CommitteeMemberController;
import com.jobfair.api.controller.GalleryImageController;
import com.jobfair.api.controller.JobController;
import com.jobfair.api.controller.MediaController;
import com.jobfair.api.controller.MediaOutletController;
import com.jobfair.api.controller.MediaParticipationController;
import com.jobfair.api.controller.OrganizationController;
import com.jobfair.api.controller.PackageTierController;
import com.jobfair.api.controller.ParticipationController;
import com.jobfair.api.controller.PersonController;
import com.jobfair.api.controller.StatBoardController;
import com.jobfair.api.dto.request.ArticleImageRequest;
import com.jobfair.api.dto.request.ArticleRequest;
import com.jobfair.api.dto.request.CommitteeMemberRequest;
import com.jobfair.api.dto.request.CommitteeRequest;
import com.jobfair.api.dto.request.GalleryImageRequest;
import com.jobfair.api.dto.request.JobRequest;
import com.jobfair.api.dto.request.MediaOutletRequest;
import com.jobfair.api.dto.request.MediaParticipationRequest;
import com.jobfair.api.dto.request.MediaRequest;
import com.jobfair.api.dto.request.OrganizationRequest;
import com.jobfair.api.dto.request.PackageTierRequest;
import com.jobfair.api.dto.request.ParticipationRequest;
import com.jobfair.api.dto.request.PersonRequest;
import com.jobfair.api.dto.request.StatBoardRequest;
import com.jobfair.api.dto.response.ArticleImageResponse;
import com.jobfair.api.dto.response.ArticleResponse;
import com.jobfair.api.dto.response.CommitteeMemberResponse;
import com.jobfair.api.dto.response.CommitteeResponse;
import com.jobfair.api.dto.response.GalleryImageResponse;
import com.jobfair.api.dto.response.JobResponse;
import com.jobfair.api.dto.response.MediaOutletResponse;
import com.jobfair.api.dto.response.MediaParticipationResponse;
import com.jobfair.api.dto.response.MediaResponse;
import com.jobfair.api.dto.response.OrganizationResponse;
import com.jobfair.api.dto.response.PackageTierResponse;
import com.jobfair.api.dto.response.ParticipationResponse;
import com.jobfair.api.dto.response.PersonCvFileResponse;
import com.jobfair.api.dto.response.PersonResponse;
import com.jobfair.api.dto.response.StatBoardResponse;
import com.jobfair.domain.model.enums.MediaKind;
import com.jobfair.domain.model.enums.MediaTier;
import com.jobfair.domain.model.enums.OrganizationType;
import com.jobfair.domain.service.ArticleImageService;
import com.jobfair.domain.service.ArticleService;
import com.jobfair.domain.service.CommitteeMemberService;
import com.jobfair.domain.service.CommitteeService;
import com.jobfair.domain.service.GalleryImageService;
import com.jobfair.domain.service.JobService;
import com.jobfair.domain.service.MediaOutletService;
import com.jobfair.domain.service.MediaParticipationService;
import com.jobfair.domain.service.MediaService;
import com.jobfair.domain.service.OrganizationService;
import com.jobfair.domain.service.PackageTierService;
import com.jobfair.domain.service.ParticipationService;
import com.jobfair.domain.service.PersonService;
import com.jobfair.domain.service.StatBoardService;
import com.jobfair.shared.exception.GlobalExceptionHandler;

@WebMvcTest(controllers = {
        ArticleController.class,
        ArticleImageController.class,
        CommitteeController.class,
        CommitteeMemberController.class,
        GalleryImageController.class,
        JobController.class,
        MediaController.class,
        MediaOutletController.class,
        MediaParticipationController.class,
        OrganizationController.class,
        PackageTierController.class,
        ParticipationController.class,
        PersonController.class,
        StatBoardController.class
})
@AutoConfigureMockMvc
@Import(GlobalExceptionHandler.class)
abstract class AbstractApiDocumentationTestSupport {

    protected static final LocalDateTime SAMPLE_TIME = LocalDateTime.of(2026, 4, 14, 21, 30);

    @org.springframework.beans.factory.annotation.Autowired
    protected MockMvc mockMvc;

    @org.springframework.beans.factory.annotation.Autowired
    protected com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @MockitoBean
    protected ArticleService articleService;

    @MockitoBean
    protected ArticleImageService articleImageService;

    @MockitoBean
    protected CommitteeService committeeService;

    @MockitoBean
    protected CommitteeMemberService committeeMemberService;

    @MockitoBean
    protected GalleryImageService galleryImageService;

    @MockitoBean
    protected JobService jobService;

    @MockitoBean
    protected MediaService mediaService;

    @MockitoBean
    protected MediaOutletService mediaOutletService;

    @MockitoBean
    protected MediaParticipationService mediaParticipationService;

    @MockitoBean
    protected OrganizationService organizationService;

    @MockitoBean
    protected PackageTierService packageTierService;

    @MockitoBean
    protected ParticipationService participationService;

    @MockitoBean
    protected PersonService personService;

    @MockitoBean
    protected StatBoardService statBoardService;

    @BeforeEach
    void resetMocks() {
        reset(
                articleService,
                articleImageService,
                committeeService,
                committeeMemberService,
                galleryImageService,
                jobService,
                mediaService,
                mediaOutletService,
                mediaParticipationService,
                organizationService,
                packageTierService,
                participationService,
                personService,
                statBoardService
        );
        stubAllSuccessResponses();
    }

    private void stubAllSuccessResponses() {
        stubArticleCrud();
        stubArticleCustom();
        stubArticleImageCrud();
        stubArticleImageCustom();
        stubCommitteeCrud();
        stubCommitteeCustom();
        stubCommitteeMemberCrud();
        stubCommitteeMemberCustom();
        stubGalleryImageCrud();
        stubGalleryImageCustom();
        stubJobCrud();
        stubJobCustom();
        stubMediaCrud();
        stubMediaCustom();
        stubMediaOutletCrud();
        stubMediaOutletCustom();
        stubMediaParticipationCrud();
        stubMediaParticipationCustom();
        stubOrganizationCrud();
        stubOrganizationCustom();
        stubPackageTierCrud();
        stubPackageTierCustom();
        stubParticipationCrud();
        stubParticipationCustom();
        stubPersonCrud();
        stubPersonCustom();
        stubStatBoardCrud();
        stubStatBoardCustom();
    }

    protected ArticleResponse articleResponse() {
        return new ArticleResponse("article-1", "JobFAIR 2026", "jobfair-2026", "Article body", "https://cdn.jobfair.test/article.png", false, true, SAMPLE_TIME, SAMPLE_TIME);
    }

    protected ArticleImageResponse articleImageResponse() {
        return new ArticleImageResponse("article-image-1", "article-1", "media-1", 1, "Main banner", SAMPLE_TIME, SAMPLE_TIME);
    }

    protected CommitteeResponse committeeResponse() {
        return new CommitteeResponse("committee-1", 2026, "Main committee", SAMPLE_TIME, SAMPLE_TIME);
    }

    protected CommitteeMemberResponse committeeMemberResponse() {
        return new CommitteeMemberResponse("committee-member-1", "committee-1", "person-1", "Lead", SAMPLE_TIME, SAMPLE_TIME);
    }

    protected GalleryImageResponse galleryImageResponse() {
        return new GalleryImageResponse("gallery-image-1", "organization-1", "media-1", 1, "Booth photo", SAMPLE_TIME, SAMPLE_TIME);
    }

    protected JobResponse jobResponse() {
        return new JobResponse("job-1", "Java Developer", "java-developer", "Build backend services", "https://jobs.jobfair.test/apply", "hr@jobfair.test", SAMPLE_TIME, SAMPLE_TIME.plusDays(30), SAMPLE_TIME);
    }

    protected MediaResponse mediaResponse() {
        return new MediaResponse("media-1", "https://cdn.jobfair.test/media.png", 1920, 1080, 512000, "image/png", SAMPLE_TIME);
    }

    protected MediaOutletResponse mediaOutletResponse() {
        return new MediaOutletResponse("outlet-1", "Daily News", "daily-news", "https://dailynews.test", MediaKind.ONLINE, SAMPLE_TIME);
    }

    protected MediaParticipationResponse mediaParticipationResponse() {
        return new MediaParticipationResponse("media-participation-1", "outlet-1", 2026, MediaTier.GOLD, SAMPLE_TIME, SAMPLE_TIME);
    }

    protected OrganizationResponse organizationResponse() {
        return new OrganizationResponse("organization-1", OrganizationType.COMPANY, "Tech Corp", "tech-corp", "https://techcorp.test", "Main sponsor", SAMPLE_TIME);
    }

    protected PackageTierResponse packageTierResponse() {
        return new PackageTierResponse("package-tier-1", "Gold package", "GOLD", "Top-tier participation package", SAMPLE_TIME, SAMPLE_TIME);
    }

    protected ParticipationResponse participationResponse() {
        return new ParticipationResponse("participation-1", "organization-1", "package-tier-1", 2026, SAMPLE_TIME, SAMPLE_TIME);
    }

    protected PersonResponse personResponse() {
        return new PersonResponse("person-1", "Ana", "Anic", "ana@jobfair.test", "+38761111222", "Coordinator", SAMPLE_TIME);
    }

    protected PersonCvFileResponse personCvFileResponse() {
        return new PersonCvFileResponse("pdf-content".getBytes(StandardCharsets.UTF_8), "resume.pdf", MediaType.APPLICATION_PDF_VALUE);
    }

    protected StatBoardResponse statBoardResponse() {
        return new StatBoardResponse("stat-board-1", "overview-2026", "Overview 2026", 2026, "Headline metrics", SAMPLE_TIME);
    }

    protected void stubArticleCrud() {
        lenient().when(articleService.create(any(ArticleRequest.class))).thenReturn(articleResponse());
        lenient().when(articleService.getAll()).thenReturn(List.of(articleResponse()));
        lenient().when(articleService.getById(anyString())).thenReturn(articleResponse());
        lenient().when(articleService.update(anyString(), any(ArticleRequest.class))).thenReturn(articleResponse());
        lenient().when(articleService.patch(anyString(), any(ArticleRequest.class))).thenReturn(articleResponse());
        doNothing().when(articleService).delete(anyString());
    }

    protected void stubArticleCustom() {
        lenient().when(articleService.getBySlug(anyString())).thenReturn(articleResponse());
        lenient().when(articleService.getPublished()).thenReturn(List.of(articleResponse()));
        lenient().when(articleService.search(anyString())).thenReturn(List.of(articleResponse()));
        lenient().when(articleService.filter(any(), any(), any(), any(), any())).thenReturn(List.of(articleResponse()));
    }

    protected void stubArticleImageCrud() {
        lenient().when(articleImageService.create(any(ArticleImageRequest.class))).thenReturn(articleImageResponse());
        lenient().when(articleImageService.getAll()).thenReturn(List.of(articleImageResponse()));
        lenient().when(articleImageService.getById(anyString())).thenReturn(articleImageResponse());
        lenient().when(articleImageService.update(anyString(), any(ArticleImageRequest.class))).thenReturn(articleImageResponse());
        lenient().when(articleImageService.patch(anyString(), any(ArticleImageRequest.class))).thenReturn(articleImageResponse());
        doNothing().when(articleImageService).delete(anyString());
    }

    protected void stubArticleImageCustom() {
        lenient().when(articleImageService.getByArticleId(anyString())).thenReturn(List.of(articleImageResponse()));
        lenient().when(articleImageService.getByMediaId(anyString())).thenReturn(List.of(articleImageResponse()));
        lenient().when(articleImageService.filter(any(), any(), any(), any(), any())).thenReturn(List.of(articleImageResponse()));
    }

    protected void stubCommitteeCrud() {
        lenient().when(committeeService.create(any(CommitteeRequest.class))).thenReturn(committeeResponse());
        lenient().when(committeeService.getAll()).thenReturn(List.of(committeeResponse()));
        lenient().when(committeeService.getById(anyString())).thenReturn(committeeResponse());
        lenient().when(committeeService.update(anyString(), any(CommitteeRequest.class))).thenReturn(committeeResponse());
        lenient().when(committeeService.patch(anyString(), any(CommitteeRequest.class))).thenReturn(committeeResponse());
        doNothing().when(committeeService).delete(anyString());
    }

    protected void stubCommitteeCustom() {
        lenient().when(committeeService.getByYear(anyInt())).thenReturn(committeeResponse());
        lenient().when(committeeService.getLatest()).thenReturn(committeeResponse());
        lenient().when(committeeService.search(anyString())).thenReturn(List.of(committeeResponse()));
        lenient().when(committeeService.filter(any(), any(), any())).thenReturn(List.of(committeeResponse()));
    }

    protected void stubCommitteeMemberCrud() {
        lenient().when(committeeMemberService.create(any(CommitteeMemberRequest.class))).thenReturn(committeeMemberResponse());
        lenient().when(committeeMemberService.getAll()).thenReturn(List.of(committeeMemberResponse()));
        lenient().when(committeeMemberService.getById(anyString())).thenReturn(committeeMemberResponse());
        lenient().when(committeeMemberService.update(anyString(), any(CommitteeMemberRequest.class))).thenReturn(committeeMemberResponse());
        lenient().when(committeeMemberService.patch(anyString(), any(CommitteeMemberRequest.class))).thenReturn(committeeMemberResponse());
        doNothing().when(committeeMemberService).delete(anyString());
    }

    protected void stubCommitteeMemberCustom() {
        lenient().when(committeeMemberService.getByCommitteeId(anyString())).thenReturn(List.of(committeeMemberResponse()));
        lenient().when(committeeMemberService.getByPersonId(anyString())).thenReturn(List.of(committeeMemberResponse()));
        lenient().when(committeeMemberService.filter(any(), any(), any())).thenReturn(List.of(committeeMemberResponse()));
    }

    protected void stubGalleryImageCrud() {
        lenient().when(galleryImageService.create(any(GalleryImageRequest.class))).thenReturn(galleryImageResponse());
        lenient().when(galleryImageService.getAll()).thenReturn(List.of(galleryImageResponse()));
        lenient().when(galleryImageService.getById(anyString())).thenReturn(galleryImageResponse());
        lenient().when(galleryImageService.update(anyString(), any(GalleryImageRequest.class))).thenReturn(galleryImageResponse());
        lenient().when(galleryImageService.patch(anyString(), any(GalleryImageRequest.class))).thenReturn(galleryImageResponse());
        doNothing().when(galleryImageService).delete(anyString());
    }

    protected void stubGalleryImageCustom() {
        lenient().when(galleryImageService.getByOrganizationId(anyString())).thenReturn(List.of(galleryImageResponse()));
        lenient().when(galleryImageService.getByMediaId(anyString())).thenReturn(List.of(galleryImageResponse()));
        lenient().when(galleryImageService.filter(any(), any(), any(), any(), any())).thenReturn(List.of(galleryImageResponse()));
    }

    protected void stubJobCrud() {
        lenient().when(jobService.create(any(JobRequest.class))).thenReturn(jobResponse());
        lenient().when(jobService.getAll()).thenReturn(List.of(jobResponse()));
        lenient().when(jobService.getById(anyString())).thenReturn(jobResponse());
        lenient().when(jobService.update(anyString(), any(JobRequest.class))).thenReturn(jobResponse());
        lenient().when(jobService.patch(anyString(), any(JobRequest.class))).thenReturn(jobResponse());
        doNothing().when(jobService).delete(anyString());
    }

    protected void stubJobCustom() {
        lenient().when(jobService.getBySlug(anyString())).thenReturn(jobResponse());
        lenient().when(jobService.getActive()).thenReturn(List.of(jobResponse()));
        lenient().when(jobService.search(anyString())).thenReturn(List.of(jobResponse()));
        lenient().when(jobService.filter(any(), any(), any(), anyBoolean())).thenReturn(List.of(jobResponse()));
    }

    protected void stubMediaCrud() {
        lenient().when(mediaService.create(any(MediaRequest.class))).thenReturn(mediaResponse());
        lenient().when(mediaService.getAll()).thenReturn(List.of(mediaResponse()));
        lenient().when(mediaService.getById(anyString())).thenReturn(mediaResponse());
        lenient().when(mediaService.update(anyString(), any(MediaRequest.class))).thenReturn(mediaResponse());
        lenient().when(mediaService.patch(anyString(), any(MediaRequest.class))).thenReturn(mediaResponse());
        doNothing().when(mediaService).delete(anyString());
    }

    protected void stubMediaCustom() {
        lenient().when(mediaService.getByUrl(anyString())).thenReturn(mediaResponse());
        lenient().when(mediaService.getByMimeType(anyString())).thenReturn(List.of(mediaResponse()));
        lenient().when(mediaService.search(anyString())).thenReturn(List.of(mediaResponse()));
        lenient().when(mediaService.filter(any(), any(), any(), any())).thenReturn(List.of(mediaResponse()));
    }

    protected void stubMediaOutletCrud() {
        lenient().when(mediaOutletService.create(any(MediaOutletRequest.class))).thenReturn(mediaOutletResponse());
        lenient().when(mediaOutletService.getAll()).thenReturn(List.of(mediaOutletResponse()));
        lenient().when(mediaOutletService.getById(anyString())).thenReturn(mediaOutletResponse());
        lenient().when(mediaOutletService.update(anyString(), any(MediaOutletRequest.class))).thenReturn(mediaOutletResponse());
        lenient().when(mediaOutletService.patch(anyString(), any(MediaOutletRequest.class))).thenReturn(mediaOutletResponse());
        doNothing().when(mediaOutletService).delete(anyString());
    }

    protected void stubMediaOutletCustom() {
        lenient().when(mediaOutletService.getBySlug(anyString())).thenReturn(mediaOutletResponse());
        lenient().when(mediaOutletService.getByKind(any(MediaKind.class))).thenReturn(List.of(mediaOutletResponse()));
        lenient().when(mediaOutletService.search(anyString())).thenReturn(List.of(mediaOutletResponse()));
        lenient().when(mediaOutletService.filter(any(), any(), any())).thenReturn(List.of(mediaOutletResponse()));
    }

    protected void stubMediaParticipationCrud() {
        lenient().when(mediaParticipationService.create(any(MediaParticipationRequest.class))).thenReturn(mediaParticipationResponse());
        lenient().when(mediaParticipationService.getAll()).thenReturn(List.of(mediaParticipationResponse()));
        lenient().when(mediaParticipationService.getById(anyString())).thenReturn(mediaParticipationResponse());
        lenient().when(mediaParticipationService.update(anyString(), any(MediaParticipationRequest.class))).thenReturn(mediaParticipationResponse());
        lenient().when(mediaParticipationService.patch(anyString(), any(MediaParticipationRequest.class))).thenReturn(mediaParticipationResponse());
        doNothing().when(mediaParticipationService).delete(anyString());
    }

    protected void stubMediaParticipationCustom() {
        lenient().when(mediaParticipationService.getByYear(anyInt())).thenReturn(List.of(mediaParticipationResponse()));
        lenient().when(mediaParticipationService.getByMediaOutletId(anyString())).thenReturn(List.of(mediaParticipationResponse()));
        lenient().when(mediaParticipationService.filter(any(), any(), any())).thenReturn(List.of(mediaParticipationResponse()));
    }

    protected void stubOrganizationCrud() {
        lenient().when(organizationService.create(any(OrganizationRequest.class))).thenReturn(organizationResponse());
        lenient().when(organizationService.getAll()).thenReturn(List.of(organizationResponse()));
        lenient().when(organizationService.getById(anyString())).thenReturn(organizationResponse());
        lenient().when(organizationService.update(anyString(), any(OrganizationRequest.class))).thenReturn(organizationResponse());
        lenient().when(organizationService.patch(anyString(), any(OrganizationRequest.class))).thenReturn(organizationResponse());
        doNothing().when(organizationService).delete(anyString());
    }

    protected void stubOrganizationCustom() {
        lenient().when(organizationService.getBySlug(anyString())).thenReturn(organizationResponse());
        lenient().when(organizationService.getByType(any(OrganizationType.class))).thenReturn(List.of(organizationResponse()));
        lenient().when(organizationService.search(anyString())).thenReturn(List.of(organizationResponse()));
        lenient().when(organizationService.filter(any(), any(), any())).thenReturn(List.of(organizationResponse()));
    }

    protected void stubPackageTierCrud() {
        lenient().when(packageTierService.create(any(PackageTierRequest.class))).thenReturn(packageTierResponse());
        lenient().when(packageTierService.getAll()).thenReturn(List.of(packageTierResponse()));
        lenient().when(packageTierService.getById(anyString())).thenReturn(packageTierResponse());
        lenient().when(packageTierService.update(anyString(), any(PackageTierRequest.class))).thenReturn(packageTierResponse());
        lenient().when(packageTierService.patch(anyString(), any(PackageTierRequest.class))).thenReturn(packageTierResponse());
        doNothing().when(packageTierService).delete(anyString());
    }

    protected void stubPackageTierCustom() {
        lenient().when(packageTierService.getByCode(anyString())).thenReturn(packageTierResponse());
        lenient().when(packageTierService.search(anyString())).thenReturn(List.of(packageTierResponse()));
        lenient().when(packageTierService.filter(any(), any())).thenReturn(List.of(packageTierResponse()));
    }

    protected void stubParticipationCrud() {
        lenient().when(participationService.create(any(ParticipationRequest.class))).thenReturn(participationResponse());
        lenient().when(participationService.getAll()).thenReturn(List.of(participationResponse()));
        lenient().when(participationService.getById(anyString())).thenReturn(participationResponse());
        lenient().when(participationService.update(anyString(), any(ParticipationRequest.class))).thenReturn(participationResponse());
        lenient().when(participationService.patch(anyString(), any(ParticipationRequest.class))).thenReturn(participationResponse());
        doNothing().when(participationService).delete(anyString());
    }

    protected void stubParticipationCustom() {
        lenient().when(participationService.getByYear(anyInt())).thenReturn(List.of(participationResponse()));
        lenient().when(participationService.getByOrganizationId(anyString())).thenReturn(List.of(participationResponse()));
        lenient().when(participationService.filter(any(), any(), any())).thenReturn(List.of(participationResponse()));
    }

    protected void stubPersonCrud() {
        lenient().when(personService.create(any(PersonRequest.class))).thenReturn(personResponse());
        lenient().when(personService.getAll()).thenReturn(List.of(personResponse()));
        lenient().when(personService.getById(anyString())).thenReturn(personResponse());
        lenient().when(personService.update(anyString(), any(PersonRequest.class))).thenReturn(personResponse());
        lenient().when(personService.patch(anyString(), any(PersonRequest.class))).thenReturn(personResponse());
        doNothing().when(personService).delete(anyString());
    }

    protected void stubPersonCustom() {
        lenient().when(personService.getByEmail(anyString())).thenReturn(personResponse());
        lenient().when(personService.getByPosition(anyString())).thenReturn(List.of(personResponse()));
        lenient().when(personService.search(anyString())).thenReturn(List.of(personResponse()));
        lenient().when(personService.filter(any(), any(), any())).thenReturn(List.of(personResponse()));
        doNothing().when(personService).uploadCv(anyString(), any());
        lenient().when(personService.getCv(anyString())).thenReturn(personCvFileResponse());
    }

    protected void stubStatBoardCrud() {
        lenient().when(statBoardService.create(any(StatBoardRequest.class))).thenReturn(statBoardResponse());
        lenient().when(statBoardService.getAll()).thenReturn(List.of(statBoardResponse()));
        lenient().when(statBoardService.getById(anyString())).thenReturn(statBoardResponse());
        lenient().when(statBoardService.update(anyString(), any(StatBoardRequest.class))).thenReturn(statBoardResponse());
        lenient().when(statBoardService.patch(anyString(), any(StatBoardRequest.class))).thenReturn(statBoardResponse());
        doNothing().when(statBoardService).delete(anyString());
    }

    protected void stubStatBoardCustom() {
        lenient().when(statBoardService.getBySlug(anyString())).thenReturn(statBoardResponse());
        lenient().when(statBoardService.getByYear(anyInt())).thenReturn(List.of(statBoardResponse()));
        lenient().when(statBoardService.search(anyString())).thenReturn(List.of(statBoardResponse()));
        lenient().when(statBoardService.filter(any(), any())).thenReturn(List.of(statBoardResponse()));
    }
}
