package com.jobfair.docs;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

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
import com.jobfair.domain.model.enums.MediaKind;
import com.jobfair.domain.model.enums.MediaTier;
import com.jobfair.domain.model.enums.OrganizationType;
import com.jobfair.shared.docs.MultipartDocSample;

final class ApiDocumentationFixtures {

    private static final LocalDateTime SAMPLE_TIME = LocalDateTime.of(2026, 4, 14, 21, 30);
    private static final Map<Class<?>, Object> REQUEST_BODIES = Map.ofEntries(
            Map.entry(ArticleRequest.class, new ArticleRequest("JobFAIR 2026", "jobfair-2026", "Article body", "https://cdn.jobfair.test/article.png", false, true, SAMPLE_TIME)),
            Map.entry(ArticleImageRequest.class, new ArticleImageRequest("article-1", "media-1", 1, "Main banner")),
            Map.entry(CommitteeRequest.class, new CommitteeRequest(2026, "Main committee")),
            Map.entry(CommitteeMemberRequest.class, new CommitteeMemberRequest("committee-1", "person-1", "Lead")),
            Map.entry(GalleryImageRequest.class, new GalleryImageRequest("organization-1", "media-1", 1, "Booth photo")),
            Map.entry(JobRequest.class, new JobRequest("Java Developer", "java-developer", "Build backend services", "https://jobs.jobfair.test/apply", "hr@jobfair.test", SAMPLE_TIME, SAMPLE_TIME.plusDays(30))),
            Map.entry(MediaRequest.class, new MediaRequest("https://cdn.jobfair.test/media.png")),
            Map.entry(MediaOutletRequest.class, new MediaOutletRequest("Daily News", "daily-news", "https://dailynews.test", MediaKind.ONLINE)),
            Map.entry(MediaParticipationRequest.class, new MediaParticipationRequest("outlet-1", 2026, MediaTier.GOLD)),
            Map.entry(OrganizationRequest.class, new OrganizationRequest(OrganizationType.COMPANY, "Tech Corp", "tech-corp", "https://techcorp.test", "Main sponsor")),
            Map.entry(PackageTierRequest.class, new PackageTierRequest("Gold package", "GOLD", "Top-tier participation package")),
            Map.entry(ParticipationRequest.class, new ParticipationRequest("organization-1", "package-tier-1", 2026)),
            Map.entry(PersonRequest.class, new PersonRequest("Ana", "Anic", "ana@jobfair.test", "+38761111222", "Coordinator")),
            Map.entry(StatBoardRequest.class, new StatBoardRequest("overview-2026", "Overview 2026", 2026, "Headline metrics"))
    );

    private ApiDocumentationFixtures() {
    }

    static Object requestBody(Class<?> requestType) {
        Object body = REQUEST_BODIES.get(requestType);
        if (body == null) {
            throw new IllegalStateException("No documentation request fixture registered for " + requestType.getName());
        }
        return body;
    }

    static MockMultipartFile multipartFile(MultipartDocSample sample) {
        if (!sample.enabled()) {
            return null;
        }
        String contentType = sample.contentType().isBlank() ? MediaType.APPLICATION_OCTET_STREAM_VALUE : sample.contentType();
        return new MockMultipartFile(
                sample.fieldName(),
                sample.fileName(),
                contentType,
                sample.content().getBytes(StandardCharsets.UTF_8)
        );
    }
}
