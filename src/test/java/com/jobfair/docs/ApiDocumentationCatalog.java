package com.jobfair.docs;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

final class ApiDocumentationCatalog {

    private static final LocalDateTime SAMPLE_TIME = LocalDateTime.of(2026, 4, 14, 21, 30);

    private ApiDocumentationCatalog() {
    }

    static List<SuccessDocSpec> successSpecs() {
        List<SuccessDocSpec> specs = new ArrayList<>();

        specs.addAll(crudSpecs("articles", "/articles", "article-1", articleRequest(), support -> support.stubArticleCrud()));
        specs.add(json("articles-slug", key(HttpMethod.GET, "/articles/slug/{slug}"), HttpMethod.GET, "/articles/slug/jobfair-2026", null, null, HttpStatus.OK, false, null, support -> support.stubArticleCustom()));
        specs.add(json("articles-published", key(HttpMethod.GET, "/articles/published"), HttpMethod.GET, "/articles/published", null, null, HttpStatus.OK, false, null, support -> support.stubArticleCustom()));
        specs.add(json("articles-search", key(HttpMethod.GET, "/articles", "q"), HttpMethod.GET, "/articles?q=jobfair", null, null, HttpStatus.OK, false, null, support -> support.stubArticleCustom()));
        specs.add(json("articles-filter", key(HttpMethod.GET, "/articles/filter"), HttpMethod.GET, "/articles/filter?q=jobfair&published=true&draft=false&from=2026-04-01T10:00:00&to=2026-04-30T18:00:00", null, null, HttpStatus.OK, false, null, support -> support.stubArticleCustom()));

        specs.addAll(crudSpecs("article-images", "/article-images", "article-image-1", articleImageRequest(), support -> support.stubArticleImageCrud()));
        specs.add(json("article-images-by-article", key(HttpMethod.GET, "/article-images/article/{articleId}"), HttpMethod.GET, "/article-images/article/article-1", null, null, HttpStatus.OK, false, null, support -> support.stubArticleImageCustom()));
        specs.add(json("article-images-by-media", key(HttpMethod.GET, "/article-images/media/{mediaId}"), HttpMethod.GET, "/article-images/media/media-1", null, null, HttpStatus.OK, false, null, support -> support.stubArticleImageCustom()));
        specs.add(json("article-images-filter", key(HttpMethod.GET, "/article-images/filter"), HttpMethod.GET, "/article-images/filter?articleId=article-1&mediaId=media-1&q=banner&minSort=1&maxSort=3", null, null, HttpStatus.OK, false, null, support -> support.stubArticleImageCustom()));

        specs.addAll(crudSpecs("committees", "/committees", "committee-1", committeeRequest(), support -> support.stubCommitteeCrud()));
        specs.add(json("committees-year", key(HttpMethod.GET, "/committees/year/{year}"), HttpMethod.GET, "/committees/year/2026", null, null, HttpStatus.OK, false, null, support -> support.stubCommitteeCustom()));
        specs.add(json("committees-latest", key(HttpMethod.GET, "/committees/latest"), HttpMethod.GET, "/committees/latest", null, null, HttpStatus.OK, false, null, support -> support.stubCommitteeCustom()));
        specs.add(json("committees-search", key(HttpMethod.GET, "/committees", "q"), HttpMethod.GET, "/committees?q=main", null, null, HttpStatus.OK, false, null, support -> support.stubCommitteeCustom()));
        specs.add(json("committees-filter", key(HttpMethod.GET, "/committees/filter"), HttpMethod.GET, "/committees/filter?q=main&fromYear=2024&toYear=2026", null, null, HttpStatus.OK, false, null, support -> support.stubCommitteeCustom()));

        specs.addAll(crudSpecs("committee-members", "/committee-members", "committee-member-1", committeeMemberRequest(), support -> support.stubCommitteeMemberCrud()));
        specs.add(json("committee-members-by-committee", key(HttpMethod.GET, "/committee-members/committee/{committeeId}"), HttpMethod.GET, "/committee-members/committee/committee-1", null, null, HttpStatus.OK, false, null, support -> support.stubCommitteeMemberCustom()));
        specs.add(json("committee-members-by-person", key(HttpMethod.GET, "/committee-members/person/{personId}"), HttpMethod.GET, "/committee-members/person/person-1", null, null, HttpStatus.OK, false, null, support -> support.stubCommitteeMemberCustom()));
        specs.add(json("committee-members-filter", key(HttpMethod.GET, "/committee-members/filter"), HttpMethod.GET, "/committee-members/filter?committeeId=committee-1&personId=person-1&role=Lead", null, null, HttpStatus.OK, false, null, support -> support.stubCommitteeMemberCustom()));

        specs.addAll(crudSpecs("gallery-images", "/gallery-images", "gallery-image-1", galleryImageRequest(), support -> support.stubGalleryImageCrud()));
        specs.add(json("gallery-images-by-organization", key(HttpMethod.GET, "/gallery-images/organization/{organizationId}"), HttpMethod.GET, "/gallery-images/organization/organization-1", null, null, HttpStatus.OK, false, null, support -> support.stubGalleryImageCustom()));
        specs.add(json("gallery-images-by-media", key(HttpMethod.GET, "/gallery-images/media/{mediaId}"), HttpMethod.GET, "/gallery-images/media/media-1", null, null, HttpStatus.OK, false, null, support -> support.stubGalleryImageCustom()));
        specs.add(json("gallery-images-filter", key(HttpMethod.GET, "/gallery-images/filter"), HttpMethod.GET, "/gallery-images/filter?organizationId=organization-1&mediaId=media-1&q=booth&minSort=1&maxSort=3", null, null, HttpStatus.OK, false, null, support -> support.stubGalleryImageCustom()));

        specs.addAll(crudSpecs("jobs", "/jobs", "job-1", jobRequest(), support -> support.stubJobCrud()));
        specs.add(json("jobs-slug", key(HttpMethod.GET, "/jobs/slug/{slug}"), HttpMethod.GET, "/jobs/slug/java-developer", null, null, HttpStatus.OK, false, null, support -> support.stubJobCustom()));
        specs.add(json("jobs-active", key(HttpMethod.GET, "/jobs/active"), HttpMethod.GET, "/jobs/active", null, null, HttpStatus.OK, false, null, support -> support.stubJobCustom()));
        specs.add(json("jobs-search", key(HttpMethod.GET, "/jobs", "q"), HttpMethod.GET, "/jobs?q=java", null, null, HttpStatus.OK, false, null, support -> support.stubJobCustom()));
        specs.add(json("jobs-filter", key(HttpMethod.GET, "/jobs/filter"), HttpMethod.GET, "/jobs/filter?q=developer&postedAfter=2026-04-01T10:00:00&expiresBefore=2026-05-01T10:00:00&openOnly=true", null, null, HttpStatus.OK, false, null, support -> support.stubJobCustom()));

        specs.addAll(crudSpecs("media", "/media", "media-1", mediaRequest(), support -> support.stubMediaCrud()));
        specs.add(json("media-url", key(HttpMethod.GET, "/media/url"), HttpMethod.GET, "/media/url?value=https://cdn.jobfair.test/media.png", null, null, HttpStatus.OK, false, null, support -> support.stubMediaCustom()));
        specs.add(json("media-mime", key(HttpMethod.GET, "/media/mime"), HttpMethod.GET, "/media/mime?value=image/png", null, null, HttpStatus.OK, false, null, support -> support.stubMediaCustom()));
        specs.add(json("media-search", key(HttpMethod.GET, "/media", "q"), HttpMethod.GET, "/media?q=logo", null, null, HttpStatus.OK, false, null, support -> support.stubMediaCustom()));
        specs.add(json("media-filter", key(HttpMethod.GET, "/media/filter"), HttpMethod.GET, "/media/filter?q=logo&mimeType=image/png&minSize=1024&maxSize=8192", null, null, HttpStatus.OK, false, null, support -> support.stubMediaCustom()));

        specs.addAll(crudSpecs("media-outlets", "/media-outlets", "outlet-1", mediaOutletRequest(), support -> support.stubMediaOutletCrud()));
        specs.add(json("media-outlets-slug", key(HttpMethod.GET, "/media-outlets/slug/{slug}"), HttpMethod.GET, "/media-outlets/slug/daily-news", null, null, HttpStatus.OK, false, null, support -> support.stubMediaOutletCustom()));
        specs.add(json("media-outlets-kind", key(HttpMethod.GET, "/media-outlets/kind/{kind}"), HttpMethod.GET, "/media-outlets/kind/ONLINE", null, null, HttpStatus.OK, false, null, support -> support.stubMediaOutletCustom()));
        specs.add(json("media-outlets-search", key(HttpMethod.GET, "/media-outlets", "q"), HttpMethod.GET, "/media-outlets?q=daily", null, null, HttpStatus.OK, false, null, support -> support.stubMediaOutletCustom()));
        specs.add(json("media-outlets-filter", key(HttpMethod.GET, "/media-outlets/filter"), HttpMethod.GET, "/media-outlets/filter?q=daily&kind=ONLINE&hasWebsite=true", null, null, HttpStatus.OK, false, null, support -> support.stubMediaOutletCustom()));

        specs.addAll(crudSpecs("media-participations", "/media-participations", "media-participation-1", mediaParticipationRequest(), support -> support.stubMediaParticipationCrud()));
        specs.add(json("media-participations-year", key(HttpMethod.GET, "/media-participations/year/{year}"), HttpMethod.GET, "/media-participations/year/2026", null, null, HttpStatus.OK, false, null, support -> support.stubMediaParticipationCustom()));
        specs.add(json("media-participations-outlet", key(HttpMethod.GET, "/media-participations/outlet/{mediaOutletId}"), HttpMethod.GET, "/media-participations/outlet/outlet-1", null, null, HttpStatus.OK, false, null, support -> support.stubMediaParticipationCustom()));
        specs.add(json("media-participations-filter", key(HttpMethod.GET, "/media-participations/filter"), HttpMethod.GET, "/media-participations/filter?year=2026&tier=GOLD&mediaOutletId=outlet-1", null, null, HttpStatus.OK, false, null, support -> support.stubMediaParticipationCustom()));

        specs.addAll(crudSpecs("organizations", "/organizations", "organization-1", organizationRequest(), support -> support.stubOrganizationCrud()));
        specs.add(json("organizations-slug", key(HttpMethod.GET, "/organizations/slug/{slug}"), HttpMethod.GET, "/organizations/slug/tech-corp", null, null, HttpStatus.OK, false, null, support -> support.stubOrganizationCustom()));
        specs.add(json("organizations-type", key(HttpMethod.GET, "/organizations/type/{type}"), HttpMethod.GET, "/organizations/type/COMPANY", null, null, HttpStatus.OK, false, null, support -> support.stubOrganizationCustom()));
        specs.add(json("organizations-search", key(HttpMethod.GET, "/organizations", "q"), HttpMethod.GET, "/organizations?q=tech", null, null, HttpStatus.OK, false, null, support -> support.stubOrganizationCustom()));
        specs.add(json("organizations-filter", key(HttpMethod.GET, "/organizations/filter"), HttpMethod.GET, "/organizations/filter?q=tech&type=COMPANY&hasWebsite=true", null, null, HttpStatus.OK, false, null, support -> support.stubOrganizationCustom()));

        specs.addAll(crudSpecs("package-tiers", "/package-tiers", "package-tier-1", packageTierRequest(), support -> support.stubPackageTierCrud()));
        specs.add(json("package-tiers-code", key(HttpMethod.GET, "/package-tiers/code/{code}"), HttpMethod.GET, "/package-tiers/code/GOLD", null, null, HttpStatus.OK, false, null, support -> support.stubPackageTierCustom()));
        specs.add(json("package-tiers-search", key(HttpMethod.GET, "/package-tiers", "q"), HttpMethod.GET, "/package-tiers?q=gold", null, null, HttpStatus.OK, false, null, support -> support.stubPackageTierCustom()));
        specs.add(json("package-tiers-filter", key(HttpMethod.GET, "/package-tiers/filter"), HttpMethod.GET, "/package-tiers/filter?q=gold&code=GOLD", null, null, HttpStatus.OK, false, null, support -> support.stubPackageTierCustom()));

        specs.addAll(crudSpecs("participations", "/participations", "participation-1", participationRequest(), support -> support.stubParticipationCrud()));
        specs.add(json("participations-year", key(HttpMethod.GET, "/participations/year/{year}"), HttpMethod.GET, "/participations/year/2026", null, null, HttpStatus.OK, false, null, support -> support.stubParticipationCustom()));
        specs.add(json("participations-organization", key(HttpMethod.GET, "/participations/organization/{organizationId}"), HttpMethod.GET, "/participations/organization/organization-1", null, null, HttpStatus.OK, false, null, support -> support.stubParticipationCustom()));
        specs.add(json("participations-filter", key(HttpMethod.GET, "/participations/filter"), HttpMethod.GET, "/participations/filter?year=2026&organizationId=organization-1&packageTierId=package-tier-1", null, null, HttpStatus.OK, false, null, support -> support.stubParticipationCustom()));

        specs.addAll(crudSpecs("people", "/people", "person-1", personRequest(), support -> support.stubPersonCrud()));
        specs.add(json("people-email", key(HttpMethod.GET, "/people/email"), HttpMethod.GET, "/people/email?value=ana@jobfair.test", null, null, HttpStatus.OK, false, null, support -> support.stubPersonCustom()));
        specs.add(json("people-role", key(HttpMethod.GET, "/people/role"), HttpMethod.GET, "/people/role?value=Coordinator", null, null, HttpStatus.OK, false, null, support -> support.stubPersonCustom()));
        specs.add(json("people-search", key(HttpMethod.GET, "/people", "q"), HttpMethod.GET, "/people?q=ana", null, null, HttpStatus.OK, false, null, support -> support.stubPersonCustom()));
        specs.add(json("people-filter", key(HttpMethod.GET, "/people/filter"), HttpMethod.GET, "/people/filter?q=ana&role=Coordinator&hasEmail=true", null, null, HttpStatus.OK, false, null, support -> support.stubPersonCustom()));
        specs.add(multipart("people-upload-cv", key(HttpMethod.POST, "/people/{id}/cv"), HttpMethod.POST, "/people/person-1/cv", sampleCvFile(), HttpStatus.CREATED, support -> support.stubPersonCustom()));
        specs.add(json("people-get-cv", key(HttpMethod.GET, "/people/{id}/cv"), HttpMethod.GET, "/people/person-1/cv", null, null, HttpStatus.OK, true, null, support -> support.stubPersonCustom()));

        specs.addAll(crudSpecs("stat-boards", "/stat-boards", "stat-board-1", statBoardRequest(), support -> support.stubStatBoardCrud()));
        specs.add(json("stat-boards-slug", key(HttpMethod.GET, "/stat-boards/slug/{slug}"), HttpMethod.GET, "/stat-boards/slug/overview-2026", null, null, HttpStatus.OK, false, null, support -> support.stubStatBoardCustom()));
        specs.add(json("stat-boards-year", key(HttpMethod.GET, "/stat-boards/year/{year}"), HttpMethod.GET, "/stat-boards/year/2026", null, null, HttpStatus.OK, false, null, support -> support.stubStatBoardCustom()));
        specs.add(json("stat-boards-search", key(HttpMethod.GET, "/stat-boards", "q"), HttpMethod.GET, "/stat-boards?q=overview", null, null, HttpStatus.OK, false, null, support -> support.stubStatBoardCustom()));
        specs.add(json("stat-boards-filter", key(HttpMethod.GET, "/stat-boards/filter"), HttpMethod.GET, "/stat-boards/filter?q=overview&year=2026", null, null, HttpStatus.OK, false, null, support -> support.stubStatBoardCustom()));

        return List.copyOf(specs);
    }

    static Set<String> documentedMappings() {
        Set<String> mappings = new LinkedHashSet<>();
        for (SuccessDocSpec spec : successSpecs()) {
            mappings.add(spec.mappingKey());
        }
        return mappings;
    }

    private static List<SuccessDocSpec> crudSpecs(String resource, String basePath, String id, Object requestBody, java.util.function.Consumer<AbstractApiDocumentationTestSupport> stubber) {
        return List.of(
                json(resource + "-create", key(HttpMethod.POST, basePath), HttpMethod.POST, basePath, requestBody, MediaType.APPLICATION_JSON, HttpStatus.CREATED, false, null, stubber),
                json(resource + "-get-all", key(HttpMethod.GET, basePath), HttpMethod.GET, basePath, null, null, HttpStatus.OK, false, null, stubber),
                json(resource + "-get-by-id", key(HttpMethod.GET, basePath + "/{id}"), HttpMethod.GET, basePath + "/" + id, null, null, HttpStatus.OK, false, null, stubber),
                json(resource + "-update", key(HttpMethod.PUT, basePath + "/{id}"), HttpMethod.PUT, basePath + "/" + id, requestBody, MediaType.APPLICATION_JSON, HttpStatus.OK, false, null, stubber),
                json(resource + "-patch", key(HttpMethod.PATCH, basePath + "/{id}"), HttpMethod.PATCH, basePath + "/" + id, requestBody, MediaType.APPLICATION_JSON, HttpStatus.OK, false, null, stubber),
                json(resource + "-delete", key(HttpMethod.DELETE, basePath + "/{id}"), HttpMethod.DELETE, basePath + "/" + id, null, null, HttpStatus.OK, false, null, stubber)
        );
    }

    private static SuccessDocSpec json(
            String snippetId,
            String mappingKey,
            HttpMethod method,
            String url,
            Object requestBody,
            MediaType requestContentType,
            HttpStatus expectedStatus,
            boolean binaryResponse,
            MockMultipartFile multipartFile,
            java.util.function.Consumer<AbstractApiDocumentationTestSupport> stubber
    ) {
        return new SuccessDocSpec(snippetId, mappingKey, method, url, requestBody, requestContentType, multipartFile, expectedStatus, binaryResponse, stubber);
    }

    private static SuccessDocSpec multipart(
            String snippetId,
            String mappingKey,
            HttpMethod method,
            String url,
            MockMultipartFile multipartFile,
            HttpStatus expectedStatus,
            java.util.function.Consumer<AbstractApiDocumentationTestSupport> stubber
    ) {
        return new SuccessDocSpec(snippetId, mappingKey, method, url, null, MediaType.MULTIPART_FORM_DATA, multipartFile, expectedStatus, false, stubber);
    }

    private static String key(HttpMethod method, String path) {
        return method.name() + " " + path;
    }

    private static String key(HttpMethod method, String path, String params) {
        return key(method, path) + " [params=" + params + "]";
    }

    private static MockMultipartFile sampleCvFile() {
        return new MockMultipartFile("file", "resume.pdf", MediaType.APPLICATION_PDF_VALUE, "sample-pdf".getBytes(StandardCharsets.UTF_8));
    }

    private static ArticleRequest articleRequest() {
        return new ArticleRequest("JobFAIR 2026", "jobfair-2026", "Article body", "https://cdn.jobfair.test/article.png", false, true, SAMPLE_TIME);
    }

    private static ArticleImageRequest articleImageRequest() {
        return new ArticleImageRequest("article-1", "media-1", 1, "Main banner");
    }

    private static CommitteeRequest committeeRequest() {
        return new CommitteeRequest(2026, "Main committee");
    }

    private static CommitteeMemberRequest committeeMemberRequest() {
        return new CommitteeMemberRequest("committee-1", "person-1", "Lead");
    }

    private static GalleryImageRequest galleryImageRequest() {
        return new GalleryImageRequest("organization-1", "media-1", 1, "Booth photo");
    }

    private static JobRequest jobRequest() {
        return new JobRequest("Java Developer", "java-developer", "Build backend services", "https://jobs.jobfair.test/apply", "hr@jobfair.test", SAMPLE_TIME, SAMPLE_TIME.plusDays(30));
    }

    private static MediaRequest mediaRequest() {
        return new MediaRequest("https://cdn.jobfair.test/media.png");
    }

    private static MediaOutletRequest mediaOutletRequest() {
        return new MediaOutletRequest("Daily News", "daily-news", "https://dailynews.test", MediaKind.ONLINE);
    }

    private static MediaParticipationRequest mediaParticipationRequest() {
        return new MediaParticipationRequest("outlet-1", 2026, MediaTier.GOLD);
    }

    private static OrganizationRequest organizationRequest() {
        return new OrganizationRequest(OrganizationType.COMPANY, "Tech Corp", "tech-corp", "https://techcorp.test", "Main sponsor");
    }

    private static PackageTierRequest packageTierRequest() {
        return new PackageTierRequest("Gold package", "GOLD", "Top-tier participation package");
    }

    private static ParticipationRequest participationRequest() {
        return new ParticipationRequest("organization-1", "package-tier-1", 2026);
    }

    private static PersonRequest personRequest() {
        return new PersonRequest("Ana", "Anic", "ana@jobfair.test", "+38761111222", "Coordinator");
    }

    private static StatBoardRequest statBoardRequest() {
        return new StatBoardRequest("overview-2026", "Overview 2026", 2026, "Headline metrics");
    }
}
