CREATE TABLE NBPT2.media (
    id VARCHAR2(36 CHAR) NOT NULL,
    url VARCHAR2(1024 CHAR) NOT NULL,
    width NUMBER(10),
    height NUMBER(10),
    size_bytes NUMBER(10),
    mime_type VARCHAR2(150 CHAR),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_media PRIMARY KEY (id),
    CONSTRAINT uk_media_url UNIQUE (url)
);

CREATE TABLE NBPT2.stat_boards (
    id VARCHAR2(36 CHAR) NOT NULL,
    slug VARCHAR2(150 CHAR) NOT NULL,
    title VARCHAR2(200 CHAR) NOT NULL,
    year NUMBER(4),
    description VARCHAR2(1000 CHAR),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_stat_boards PRIMARY KEY (id),
    CONSTRAINT uk_stat_board_slug UNIQUE (slug)
);

CREATE INDEX idx_stat_board_year ON NBPT2.stat_boards(year);

CREATE TABLE NBPT2.stats (
    id VARCHAR2(36 CHAR) NOT NULL,
    board_id VARCHAR2(36 CHAR) NOT NULL,
    label VARCHAR2(200 CHAR) NOT NULL,
    value_int NUMBER(10),
    value_text VARCHAR2(200 CHAR),
    plus NUMBER(1) DEFAULT 0 NOT NULL,
    sort NUMBER(10) DEFAULT 0 NOT NULL,
    icon VARCHAR2(150 CHAR),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_stats PRIMARY KEY (id),
    CONSTRAINT uk_stat_board_sort UNIQUE (board_id, sort),
    CONSTRAINT fk_stats_board FOREIGN KEY (board_id) REFERENCES NBPT2.stat_boards(id) ON DELETE CASCADE
);

CREATE INDEX idx_stat_board ON NBPT2.stats(board_id);

CREATE TABLE NBPT2.people (
    id VARCHAR2(36 CHAR) NOT NULL,
    first_name VARCHAR2(120 CHAR) NOT NULL,
    last_name VARCHAR2(120 CHAR) NOT NULL,
    email VARCHAR2(160 CHAR),
    phone VARCHAR2(50 CHAR),
    position VARCHAR2(120 CHAR),
    image_id VARCHAR2(36 CHAR),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_people PRIMARY KEY (id),
    CONSTRAINT uk_people_email UNIQUE (email),
    CONSTRAINT fk_people_image FOREIGN KEY (image_id) REFERENCES NBPT2.media(id) ON DELETE SET NULL
);

CREATE INDEX idx_people_last_name ON NBPT2.people(last_name);

CREATE TABLE NBPT2.committees (
    id VARCHAR2(36 CHAR) NOT NULL,
    year NUMBER(4) NOT NULL,
    name VARCHAR2(150 CHAR),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_committees PRIMARY KEY (id),
    CONSTRAINT uk_committee_year UNIQUE (year)
);

CREATE TABLE NBPT2.committee_members (
    id VARCHAR2(36 CHAR) NOT NULL,
    committee_id VARCHAR2(36 CHAR) NOT NULL,
    person_id VARCHAR2(36 CHAR) NOT NULL,
    role VARCHAR2(150 CHAR),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_committee_members PRIMARY KEY (id),
    CONSTRAINT uk_committee_member UNIQUE (committee_id, person_id),
    CONSTRAINT fk_committee_member_committee FOREIGN KEY (committee_id) REFERENCES NBPT2.committees(id) ON DELETE CASCADE,
    CONSTRAINT fk_committee_member_person FOREIGN KEY (person_id) REFERENCES NBPT2.people(id) ON DELETE CASCADE
);

CREATE TABLE NBPT2.organizations (
    id VARCHAR2(36 CHAR) NOT NULL,
    type VARCHAR2(50 CHAR) NOT NULL,
    name VARCHAR2(200 CHAR) NOT NULL,
    slug VARCHAR2(150 CHAR) NOT NULL,
    website VARCHAR2(250 CHAR),
    description VARCHAR2(2000 CHAR),
    logo_id VARCHAR2(36 CHAR),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_organizations PRIMARY KEY (id),
    CONSTRAINT uk_organization_type_slug UNIQUE (type, slug),
    CONSTRAINT fk_organization_logo FOREIGN KEY (logo_id) REFERENCES NBPT2.media(id) ON DELETE SET NULL
);

CREATE INDEX idx_organization_name ON NBPT2.organizations(name);

CREATE TABLE NBPT2.gallery_images (
    id VARCHAR2(36 CHAR) NOT NULL,
    organization_id VARCHAR2(36 CHAR) NOT NULL,
    media_id VARCHAR2(36 CHAR) NOT NULL,
    caption VARCHAR2(250 CHAR),
    display_order NUMBER(10),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_gallery_images PRIMARY KEY (id),
    CONSTRAINT fk_gallery_image_org FOREIGN KEY (organization_id) REFERENCES NBPT2.organizations(id) ON DELETE CASCADE,
    CONSTRAINT fk_gallery_image_media FOREIGN KEY (media_id) REFERENCES NBPT2.media(id) ON DELETE CASCADE
);

CREATE TABLE NBPT2.package_tiers (
    id VARCHAR2(36 CHAR) NOT NULL,
    name VARCHAR2(150 CHAR) NOT NULL,
    tier_code VARCHAR2(80 CHAR),
    description VARCHAR2(2000 CHAR),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_package_tiers PRIMARY KEY (id),
    CONSTRAINT uk_package_tier_name UNIQUE (name)
);

CREATE TABLE NBPT2.participations (
    id VARCHAR2(36 CHAR) NOT NULL,
    year NUMBER(4) NOT NULL,
    organization_id VARCHAR2(36 CHAR) NOT NULL,
    package_tier_id VARCHAR2(36 CHAR) NOT NULL,
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_participations PRIMARY KEY (id),
    CONSTRAINT uk_participation_org_year UNIQUE (organization_id, year),
    CONSTRAINT fk_participation_org FOREIGN KEY (organization_id) REFERENCES NBPT2.organizations(id) ON DELETE CASCADE,
    CONSTRAINT fk_participation_package_tier FOREIGN KEY (package_tier_id) REFERENCES NBPT2.package_tiers(id)
);

CREATE INDEX idx_participation_year ON NBPT2.participations(year);

CREATE TABLE NBPT2.articles (
    id VARCHAR2(36 CHAR) NOT NULL,
    title VARCHAR2(250 CHAR) NOT NULL,
    slug VARCHAR2(200 CHAR) NOT NULL,
    body CLOB NOT NULL,
    thumbnail_url VARCHAR2(1024 CHAR),
    draft NUMBER(1) DEFAULT 0 NOT NULL,
    published NUMBER(1) DEFAULT 0 NOT NULL,
    published_at TIMESTAMP(6),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_articles PRIMARY KEY (id),
    CONSTRAINT uk_article_slug UNIQUE (slug)
);

CREATE INDEX idx_articles_published ON NBPT2.articles(published);
CREATE INDEX idx_articles_published_at ON NBPT2.articles(published_at);

CREATE TABLE NBPT2.article_images (
    id VARCHAR2(36 CHAR) NOT NULL,
    article_id VARCHAR2(36 CHAR) NOT NULL,
    media_id VARCHAR2(36 CHAR) NOT NULL,
    caption VARCHAR2(250 CHAR),
    display_order NUMBER(10),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_article_images PRIMARY KEY (id),
    CONSTRAINT fk_article_image_article FOREIGN KEY (article_id) REFERENCES NBPT2.articles(id) ON DELETE CASCADE,
    CONSTRAINT fk_article_image_media FOREIGN KEY (media_id) REFERENCES NBPT2.media(id) ON DELETE CASCADE
);

CREATE TABLE NBPT2.jobs (
    id VARCHAR2(36 CHAR) NOT NULL,
    title VARCHAR2(200 CHAR) NOT NULL,
    slug VARCHAR2(200 CHAR) NOT NULL,
    description CLOB NOT NULL,
    apply_url VARCHAR2(1024 CHAR),
    apply_email VARCHAR2(160 CHAR),
    posted_at TIMESTAMP(6) NOT NULL,
    expires_at TIMESTAMP(6),
    organization_id VARCHAR2(36 CHAR) NOT NULL,
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_jobs PRIMARY KEY (id),
    CONSTRAINT uk_job_slug UNIQUE (slug),
    CONSTRAINT fk_job_organization FOREIGN KEY (organization_id) REFERENCES NBPT2.organizations(id) ON DELETE CASCADE
);

CREATE INDEX idx_job_title ON NBPT2.jobs(title);

CREATE TABLE NBPT2.job_tags (
    job_id VARCHAR2(36 CHAR) NOT NULL,
    tag_value VARCHAR2(100 CHAR) NOT NULL,
    CONSTRAINT pk_job_tags PRIMARY KEY (job_id, tag_value),
    CONSTRAINT fk_job_tags_job FOREIGN KEY (job_id) REFERENCES NBPT2.jobs(id) ON DELETE CASCADE
);

CREATE TABLE NBPT2.media_outlets (
    id VARCHAR2(36 CHAR) NOT NULL,
    name VARCHAR2(200 CHAR) NOT NULL,
    slug VARCHAR2(150 CHAR) NOT NULL,
    website VARCHAR2(250 CHAR),
    kind VARCHAR2(40 CHAR) NOT NULL,
    logo_id VARCHAR2(36 CHAR),
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_media_outlets PRIMARY KEY (id),
    CONSTRAINT uk_media_outlet_slug UNIQUE (slug),
    CONSTRAINT fk_media_outlet_logo FOREIGN KEY (logo_id) REFERENCES NBPT2.media(id) ON DELETE SET NULL
);

CREATE INDEX idx_media_outlet_name ON NBPT2.media_outlets(name);
CREATE INDEX idx_media_outlet_kind ON NBPT2.media_outlets(kind);

CREATE TABLE NBPT2.media_participations (
    id VARCHAR2(36 CHAR) NOT NULL,
    outlet_id VARCHAR2(36 CHAR) NOT NULL,
    year NUMBER(4) NOT NULL,
    tier VARCHAR2(40 CHAR) NOT NULL,
    created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
    updated_at TIMESTAMP(6),
    CONSTRAINT pk_media_participations PRIMARY KEY (id),
    CONSTRAINT uk_media_participation_outlet_year UNIQUE (outlet_id, year),
    CONSTRAINT fk_media_participation_outlet FOREIGN KEY (outlet_id) REFERENCES NBPT2.media_outlets(id) ON DELETE CASCADE
);

CREATE INDEX idx_media_participation_year_tier ON NBPT2.media_participations(year, tier);

-- CREATE TABLE NBPT2.examples (
--     id NUMBER(19) NOT NULL,
--     title VARCHAR2(150 CHAR) NOT NULL,
--     description VARCHAR2(2000 CHAR),
--     created_at TIMESTAMP(6) DEFAULT SYSTIMESTAMP NOT NULL,
--     updated_at TIMESTAMP(6),
--     CONSTRAINT pk_examples PRIMARY KEY (id)
-- );

-- CREATE SEQUENCE NBPT2.EXAMPLES_SEQ START WITH 1 INCREMENT BY 1 NOCACHE;
