-- Seed data: 10 rows per table

-- 1) MEDIA
INSERT INTO NBPT2.media (id, url, width, height, size_bytes, mime_type)
VALUES ('00000000-0000-0000-0000-000000000001', 'https://example.com/media/1.jpg', 800, 600, 150000, 'image/jpeg');
INSERT INTO NBPT2.media (id, url, width, height, size_bytes, mime_type)
VALUES ('00000000-0000-0000-0000-000000000002', 'https://example.com/media/2.jpg', 1024, 768, 200000, 'image/jpeg');
INSERT INTO NBPT2.media (id, url, width, height, size_bytes, mime_type)
VALUES ('00000000-0000-0000-0000-000000000003', 'https://example.com/media/3.jpg', 640, 480, 120000, 'image/jpeg');
INSERT INTO NBPT2.media (id, url, width, height, size_bytes, mime_type)
VALUES ('00000000-0000-0000-0000-000000000004', 'https://example.com/media/4.jpg', 1920, 1080, 500000, 'image/jpeg');
INSERT INTO NBPT2.media (id, url, width, height, size_bytes, mime_type)
VALUES ('00000000-0000-0000-0000-000000000005', 'https://example.com/media/5.jpg', 1280, 720, 300000, 'image/jpeg');
INSERT INTO NBPT2.media (id, url, width, height, size_bytes, mime_type)
VALUES ('00000000-0000-0000-0000-000000000006', 'https://example.com/media/6.png', 800, 600, 180000, 'image/png');
INSERT INTO NBPT2.media (id, url, width, height, size_bytes, mime_type)
VALUES ('00000000-0000-0000-0000-000000000007', 'https://example.com/media/7.png', 1024, 768, 220000, 'image/png');
INSERT INTO NBPT2.media (id, url, width, height, size_bytes, mime_type)
VALUES ('00000000-0000-0000-0000-000000000008', 'https://example.com/media/8.png', 640, 480, 90000, 'image/png');
INSERT INTO NBPT2.media (id, url, width, height, size_bytes, mime_type)
VALUES ('00000000-0000-0000-0000-000000000009', 'https://example.com/media/9.svg', 512, 512, 50000, 'image/svg+xml');
INSERT INTO NBPT2.media (id, url, width, height, size_bytes, mime_type)
VALUES ('00000000-0000-0000-0000-00000000000a', 'https://example.com/media/10.svg', 256, 256, 40000, 'image/svg+xml');

-- 2) STAT_BOARDS
INSERT INTO NBPT2.stat_boards (id, slug, title, year, description)
VALUES ('10000000-0000-0000-0000-000000000001', 'stats-2017', 'JobFAIR Stats 2017', 2017, 'Statistics overview for 2017');
INSERT INTO NBPT2.stat_boards (id, slug, title, year, description)
VALUES ('10000000-0000-0000-0000-000000000002', 'stats-2018', 'JobFAIR Stats 2018', 2018, 'Statistics overview for 2018');
INSERT INTO NBPT2.stat_boards (id, slug, title, year, description)
VALUES ('10000000-0000-0000-0000-000000000003', 'stats-2019', 'JobFAIR Stats 2019', 2019, 'Statistics overview for 2019');
INSERT INTO NBPT2.stat_boards (id, slug, title, year, description)
VALUES ('10000000-0000-0000-0000-000000000004', 'stats-2020', 'JobFAIR Stats 2020', 2020, 'Statistics overview for 2020');
INSERT INTO NBPT2.stat_boards (id, slug, title, year, description)
VALUES ('10000000-0000-0000-0000-000000000005', 'stats-2021', 'JobFAIR Stats 2021', 2021, 'Statistics overview for 2021');
INSERT INTO NBPT2.stat_boards (id, slug, title, year, description)
VALUES ('10000000-0000-0000-0000-000000000006', 'stats-2022', 'JobFAIR Stats 2022', 2022, 'Statistics overview for 2022');
INSERT INTO NBPT2.stat_boards (id, slug, title, year, description)
VALUES ('10000000-0000-0000-0000-000000000007', 'stats-2023', 'JobFAIR Stats 2023', 2023, 'Statistics overview for 2023');
INSERT INTO NBPT2.stat_boards (id, slug, title, year, description)
VALUES ('10000000-0000-0000-0000-000000000008', 'stats-2024', 'JobFAIR Stats 2024', 2024, 'Statistics overview for 2024');
INSERT INTO NBPT2.stat_boards (id, slug, title, year, description)
VALUES ('10000000-0000-0000-0000-000000000009', 'stats-2025', 'JobFAIR Stats 2025', 2025, 'Statistics overview for 2025');
INSERT INTO NBPT2.stat_boards (id, slug, title, year, description)
VALUES ('10000000-0000-0000-0000-00000000000a', 'stats-2026', 'JobFAIR Stats 2026', 2026, 'Statistics overview for 2026');

-- 3) PEOPLE
INSERT INTO NBPT2.people (id, first_name, last_name, email, phone, position, image_id)
VALUES ('20000000-0000-0000-0000-000000000001', 'Ana', 'Ivic', 'person01@example.com', '+385111111', 'Organizer', '00000000-0000-0000-0000-000000000001');
INSERT INTO NBPT2.people (id, first_name, last_name, email, phone, position, image_id)
VALUES ('20000000-0000-0000-0000-000000000002', 'Marko', 'Horvat', 'person02@example.com', '+385111112', 'Coordinator', '00000000-0000-0000-0000-000000000002');
INSERT INTO NBPT2.people (id, first_name, last_name, email, phone, position, image_id)
VALUES ('20000000-0000-0000-0000-000000000003', 'Ivana', 'Kovac', 'person03@example.com', '+385111113', 'Volunteer', NULL);
INSERT INTO NBPT2.people (id, first_name, last_name, email, phone, position, image_id)
VALUES ('20000000-0000-0000-0000-000000000004', 'Petar', 'Novak', 'person04@example.com', '+385111114', 'Volunteer', NULL);
INSERT INTO NBPT2.people (id, first_name, last_name, email, phone, position, image_id)
VALUES ('20000000-0000-0000-0000-000000000005', 'Lucija', 'Jovic', 'person05@example.com', '+385111115', 'Designer', '00000000-0000-0000-0000-000000000003');
INSERT INTO NBPT2.people (id, first_name, last_name, email, phone, position, image_id)
VALUES ('20000000-0000-0000-0000-000000000006', 'Tomislav', 'Maric', 'person06@example.com', '+385111116', 'Developer', '00000000-0000-0000-0000-000000000004');
INSERT INTO NBPT2.people (id, first_name, last_name, email, phone, position, image_id)
VALUES ('20000000-0000-0000-0000-000000000007', 'Sara', 'Boric', 'person07@example.com', '+385111117', 'PR Manager', '00000000-0000-0000-0000-000000000005');
INSERT INTO NBPT2.people (id, first_name, last_name, email, phone, position, image_id)
VALUES ('20000000-0000-0000-0000-000000000008', 'Filip', 'Zoric', 'person08@example.com', '+385111118', 'Analyst', NULL);
INSERT INTO NBPT2.people (id, first_name, last_name, email, phone, position, image_id)
VALUES ('20000000-0000-0000-0000-000000000009', 'Maja', 'Radic', 'person09@example.com', '+385111119', 'Marketing', '00000000-0000-0000-0000-000000000006');
INSERT INTO NBPT2.people (id, first_name, last_name, email, phone, position, image_id)
VALUES ('20000000-0000-0000-0000-00000000000a', 'Karlo', 'Rusic', 'person10@example.com', '+385111120', 'Coordinator', '00000000-0000-0000-0000-000000000007');

-- 4) COMMITTEES
INSERT INTO NBPT2.committees (id, year, name)
VALUES ('30000000-0000-0000-0000-000000000001', 2017, 'Organizing Committee 2017');
INSERT INTO NBPT2.committees (id, year, name)
VALUES ('30000000-0000-0000-0000-000000000002', 2018, 'Organizing Committee 2018');
INSERT INTO NBPT2.committees (id, year, name)
VALUES ('30000000-0000-0000-0000-000000000003', 2019, 'Organizing Committee 2019');
INSERT INTO NBPT2.committees (id, year, name)
VALUES ('30000000-0000-0000-0000-000000000004', 2020, 'Organizing Committee 2020');
INSERT INTO NBPT2.committees (id, year, name)
VALUES ('30000000-0000-0000-0000-000000000005', 2021, 'Organizing Committee 2021');
INSERT INTO NBPT2.committees (id, year, name)
VALUES ('30000000-0000-0000-0000-000000000006', 2022, 'Organizing Committee 2022');
INSERT INTO NBPT2.committees (id, year, name)
VALUES ('30000000-0000-0000-0000-000000000007', 2023, 'Organizing Committee 2023');
INSERT INTO NBPT2.committees (id, year, name)
VALUES ('30000000-0000-0000-0000-000000000008', 2024, 'Organizing Committee 2024');
INSERT INTO NBPT2.committees (id, year, name)
VALUES ('30000000-0000-0000-0000-000000000009', 2025, 'Organizing Committee 2025');
INSERT INTO NBPT2.committees (id, year, name)
VALUES ('30000000-0000-0000-0000-00000000000a', 2026, 'Organizing Committee 2026');

-- 5) ORGANIZATIONS
INSERT INTO NBPT2.organizations (id, type, name, slug, website, description, logo_id)
VALUES ('40000000-0000-0000-0000-000000000001', 'COMPANY', 'Alpha Corp', 'alpha-corp', 'https://alpha.example.com', 'Tech company Alpha', '00000000-0000-0000-0000-000000000001');
INSERT INTO NBPT2.organizations (id, type, name, slug, website, description, logo_id)
VALUES ('40000000-0000-0000-0000-000000000002', 'COMPANY', 'Beta Solutions', 'beta-solutions', 'https://beta.example.com', 'Consulting company Beta', '00000000-0000-0000-0000-000000000002');
INSERT INTO NBPT2.organizations (id, type, name, slug, website, description, logo_id)
VALUES ('40000000-0000-0000-0000-000000000003', 'COMPANY', 'Gamma Innovations', 'gamma-innovations', 'https://gamma.example.com', 'Startup Gamma', '00000000-0000-0000-0000-000000000003');
INSERT INTO NBPT2.organizations (id, type, name, slug, website, description, logo_id)
VALUES ('40000000-0000-0000-0000-000000000004', 'COMPANY', 'Delta Systems', 'delta-systems', 'https://delta.example.com', 'Engineering firm Delta', '00000000-0000-0000-0000-000000000004');
INSERT INTO NBPT2.organizations (id, type, name, slug, website, description, logo_id)
VALUES ('40000000-0000-0000-0000-000000000005', 'COMPANY', 'Epsilon Labs', 'epsilon-labs', 'https://epsilon.example.com', 'Research lab Epsilon', '00000000-0000-0000-0000-000000000005');
INSERT INTO NBPT2.organizations (id, type, name, slug, website, description, logo_id)
VALUES ('40000000-0000-0000-0000-000000000006', 'INSTITUTION', 'Faculty of Engineering', 'foe', 'https://uni.example.com/foe', 'Engineering faculty', NULL);
INSERT INTO NBPT2.organizations (id, type, name, slug, website, description, logo_id)
VALUES ('40000000-0000-0000-0000-000000000007', 'INSTITUTION', 'Faculty of Economics', 'foe-econ', 'https://uni.example.com/econ', 'Economics faculty', NULL);
INSERT INTO NBPT2.organizations (id, type, name, slug, website, description, logo_id)
VALUES ('40000000-0000-0000-0000-000000000008', 'INSTITUTION', 'City of Zagreb', 'city-zagreb', 'https://zagreb.example.com', 'City administration', '00000000-0000-0000-0000-000000000006');
INSERT INTO NBPT2.organizations (id, type, name, slug, website, description, logo_id)
VALUES ('40000000-0000-0000-0000-000000000009', 'INSTITUTION', 'Chamber of Commerce', 'chamber-commerce', 'https://chamber.example.com', 'Chamber of Commerce', '00000000-0000-0000-0000-000000000007');
INSERT INTO NBPT2.organizations (id, type, name, slug, website, description, logo_id)
VALUES ('40000000-0000-0000-0000-00000000000a', 'ASSOCIATION', 'Tech Community', 'tech-community', 'https://community.example.com', 'Tech community partner', NULL);

-- 6) PACKAGE_TIERS
INSERT INTO NBPT2.package_tiers (id, name, tier_code, description)
VALUES ('50000000-0000-0000-0000-000000000001', 'Bronze', 'BRZ', 'Bronze sponsorship package');
INSERT INTO NBPT2.package_tiers (id, name, tier_code, description)
VALUES ('50000000-0000-0000-0000-000000000002', 'Silver', 'SLV', 'Silver sponsorship package');
INSERT INTO NBPT2.package_tiers (id, name, tier_code, description)
VALUES ('50000000-0000-0000-0000-000000000003', 'Gold', 'GLD', 'Gold sponsorship package');
INSERT INTO NBPT2.package_tiers (id, name, tier_code, description)
VALUES ('50000000-0000-0000-0000-000000000004', 'Platinum', 'PLT', 'Platinum sponsorship package');
INSERT INTO NBPT2.package_tiers (id, name, tier_code, description)
VALUES ('50000000-0000-0000-0000-000000000005', 'Partner', 'PRT', 'Partner package');
INSERT INTO NBPT2.package_tiers (id, name, tier_code, description)
VALUES ('50000000-0000-0000-0000-000000000006', 'Exhibitor', 'EXH', 'Exhibitor package');
INSERT INTO NBPT2.package_tiers (id, name, tier_code, description)
VALUES ('50000000-0000-0000-0000-000000000007', 'Startup', 'STP', 'Startup package');
INSERT INTO NBPT2.package_tiers (id, name, tier_code, description)
VALUES ('50000000-0000-0000-0000-000000000008', 'Premium', 'PRM', 'Premium sponsorship package');
INSERT INTO NBPT2.package_tiers (id, name, tier_code, description)
VALUES ('50000000-0000-0000-0000-000000000009', 'Standard', 'STD', 'Standard package');
INSERT INTO NBPT2.package_tiers (id, name, tier_code, description)
VALUES ('50000000-0000-0000-0000-00000000000a', 'Media', 'MED', 'Media partnership package');

-- 7) ARTICLES
INSERT INTO NBPT2.articles (id, title, slug, body, thumbnail_url, draft, published)
VALUES ('60000000-0000-0000-0000-000000000001', 'Welcome to JobFAIR', 'welcome-jobfair', 'JobFAIR is the leading career fair.', 'https://example.com/thumbs/art1.jpg', 0, 1);
INSERT INTO NBPT2.articles (id, title, slug, body, thumbnail_url, draft, published)
VALUES ('60000000-0000-0000-0000-000000000002', 'How to Prepare', 'how-to-prepare', 'Tips for preparing for JobFAIR.', 'https://example.com/thumbs/art2.jpg', 0, 1);
INSERT INTO NBPT2.articles (id, title, slug, body, thumbnail_url, draft, published)
VALUES ('60000000-0000-0000-0000-000000000003', 'Exhibitor Guide', 'exhibitor-guide', 'Guide for exhibitors.', 'https://example.com/thumbs/art3.jpg', 0, 1);
INSERT INTO NBPT2.articles (id, title, slug, body, thumbnail_url, draft, published)
VALUES ('60000000-0000-0000-0000-000000000004', 'Student Guide', 'student-guide', 'Guide for students.', 'https://example.com/thumbs/art4.jpg', 0, 1);
INSERT INTO NBPT2.articles (id, title, slug, body, thumbnail_url, draft, published)
VALUES ('60000000-0000-0000-0000-000000000005', 'Highlights', 'highlights', 'Highlights from previous fairs.', 'https://example.com/thumbs/art5.jpg', 0, 1);
INSERT INTO NBPT2.articles (id, title, slug, body, thumbnail_url, draft, published)
VALUES ('60000000-0000-0000-0000-000000000006', 'Workshops', 'workshops', 'Information about workshops.', 'https://example.com/thumbs/art6.jpg', 0, 0);
INSERT INTO NBPT2.articles (id, title, slug, body, thumbnail_url, draft, published)
VALUES ('60000000-0000-0000-0000-000000000007', 'Keynotes', 'keynotes', 'Information about keynotes.', 'https://example.com/thumbs/art7.jpg', 0, 0);
INSERT INTO NBPT2.articles (id, title, slug, body, thumbnail_url, draft, published)
VALUES ('60000000-0000-0000-0000-000000000008', 'Networking', 'networking', 'Networking opportunities.', 'https://example.com/thumbs/art8.jpg', 0, 1);
INSERT INTO NBPT2.articles (id, title, slug, body, thumbnail_url, draft, published)
VALUES ('60000000-0000-0000-0000-000000000009', 'Partners', 'partners', 'Overview of partners.', 'https://example.com/thumbs/art9.jpg', 0, 1);
INSERT INTO NBPT2.articles (id, title, slug, body, thumbnail_url, draft, published)
VALUES ('60000000-0000-0000-0000-00000000000a', 'FAQ', 'faq', 'Frequently asked questions.', 'https://example.com/thumbs/art10.jpg', 1, 0);

-- 8) JOBS
INSERT INTO NBPT2.jobs (id, title, slug, description, apply_url, apply_email, posted_at, organization_id)
VALUES ('70000000-0000-0000-0000-000000000001', 'Junior Java Developer', 'junior-java-developer', 'Entry level Java position.', 'https://alpha.example.com/jobs/1', 'jobs@alpha.example.com', SYSTIMESTAMP, '40000000-0000-0000-0000-000000000001');
INSERT INTO NBPT2.jobs (id, title, slug, description, apply_url, apply_email, posted_at, organization_id)
VALUES ('70000000-0000-0000-0000-000000000002', 'Frontend Developer', 'frontend-developer', 'React/Angular frontend developer.', 'https://beta.example.com/jobs/2', 'jobs@beta.example.com', SYSTIMESTAMP, '40000000-0000-0000-0000-000000000002');
INSERT INTO NBPT2.jobs (id, title, slug, description, apply_url, apply_email, posted_at, organization_id)
VALUES ('70000000-0000-0000-0000-000000000003', 'Data Analyst', 'data-analyst', 'Data analysis and reporting.', 'https://gamma.example.com/jobs/3', 'jobs@gamma.example.com', SYSTIMESTAMP, '40000000-0000-0000-0000-000000000003');
INSERT INTO NBPT2.jobs (id, title, slug, description, apply_url, apply_email, posted_at, organization_id)
VALUES ('70000000-0000-0000-0000-000000000004', 'DevOps Engineer', 'devops-engineer', 'Infrastructure and CI/CD.', 'https://delta.example.com/jobs/4', 'jobs@delta.example.com', SYSTIMESTAMP, '40000000-0000-0000-0000-000000000004');
INSERT INTO NBPT2.jobs (id, title, slug, description, apply_url, apply_email, posted_at, organization_id)
VALUES ('70000000-0000-0000-0000-000000000005', 'Research Engineer', 'research-engineer', 'Research in new technologies.', 'https://epsilon.example.com/jobs/5', 'jobs@epsilon.example.com', SYSTIMESTAMP, '40000000-0000-0000-0000-000000000005');
INSERT INTO NBPT2.jobs (id, title, slug, description, apply_url, apply_email, posted_at, organization_id)
VALUES ('70000000-0000-0000-0000-000000000006', 'Teaching Assistant', 'teaching-assistant', 'Support teaching activities.', 'https://uni.example.com/foe/jobs/6', 'jobs@foe.example.com', SYSTIMESTAMP, '40000000-0000-0000-0000-000000000006');
INSERT INTO NBPT2.jobs (id, title, slug, description, apply_url, apply_email, posted_at, organization_id)
VALUES ('70000000-0000-0000-0000-000000000007', 'Economics Intern', 'economics-intern', 'Internship in economics.', 'https://uni.example.com/econ/jobs/7', 'jobs@econ.example.com', SYSTIMESTAMP, '40000000-0000-0000-0000-000000000007');
INSERT INTO NBPT2.jobs (id, title, slug, description, apply_url, apply_email, posted_at, organization_id)
VALUES ('70000000-0000-0000-0000-000000000008', 'IT Support Specialist', 'it-support-specialist', 'IT support role.', 'https://zagreb.example.com/jobs/8', 'jobs@zagreb.example.com', SYSTIMESTAMP, '40000000-0000-0000-0000-000000000008');
INSERT INTO NBPT2.jobs (id, title, slug, description, apply_url, apply_email, posted_at, organization_id)
VALUES ('70000000-0000-0000-0000-000000000009', 'Business Consultant', 'business-consultant', 'Consulting projects.', 'https://chamber.example.com/jobs/9', 'jobs@chamber.example.com', SYSTIMESTAMP, '40000000-0000-0000-0000-000000000009');
INSERT INTO NBPT2.jobs (id, title, slug, description, apply_url, apply_email, posted_at, organization_id)
VALUES ('70000000-0000-0000-0000-00000000000a', 'Community Manager', 'community-manager', 'Manage tech community.', 'https://community.example.com/jobs/10', 'jobs@community.example.com', SYSTIMESTAMP, '40000000-0000-0000-0000-00000000000a');

-- 9) MEDIA_OUTLETS
INSERT INTO NBPT2.media_outlets (id, name, slug, website, kind, logo_id)
VALUES ('80000000-0000-0000-0000-000000000001', 'Tech News', 'tech-news', 'https://technews.example.com', 'ONLINE', '00000000-0000-0000-0000-000000000006');
INSERT INTO NBPT2.media_outlets (id, name, slug, website, kind, logo_id)
VALUES ('80000000-0000-0000-0000-000000000002', 'Daily Press', 'daily-press', 'https://dailypress.example.com', 'PRINT', '00000000-0000-0000-0000-000000000007');
INSERT INTO NBPT2.media_outlets (id, name, slug, website, kind, logo_id)
VALUES ('80000000-0000-0000-0000-000000000003', 'Campus Radio', 'campus-radio', 'https://campusradio.example.com', 'RADIO', '00000000-0000-0000-0000-000000000008');
INSERT INTO NBPT2.media_outlets (id, name, slug, website, kind, logo_id)
VALUES ('80000000-0000-0000-0000-000000000004', 'City TV', 'city-tv', 'https://citytv.example.com', 'TELEVISION', '00000000-0000-0000-0000-000000000009');
INSERT INTO NBPT2.media_outlets (id, name, slug, website, kind, logo_id)
VALUES ('80000000-0000-0000-0000-000000000005', 'Student Magazine', 'student-magazine', 'https://studentmag.example.com', 'PRINT', '00000000-0000-0000-0000-00000000000a');
INSERT INTO NBPT2.media_outlets (id, name, slug, website, kind, logo_id)
VALUES ('80000000-0000-0000-0000-000000000006', 'Job Portal', 'job-portal', 'https://jobportal.example.com', 'ONLINE', '00000000-0000-0000-0000-000000000001');
INSERT INTO NBPT2.media_outlets (id, name, slug, website, kind, logo_id)
VALUES ('80000000-0000-0000-0000-000000000007', 'Career Blog', 'career-blog', 'https://careerblog.example.com', 'ONLINE', '00000000-0000-0000-0000-000000000002');
INSERT INTO NBPT2.media_outlets (id, name, slug, website, kind, logo_id)
VALUES ('80000000-0000-0000-0000-000000000008', 'Tech Podcast', 'tech-podcast', 'https://techpodcast.example.com', 'RADIO', '00000000-0000-0000-0000-000000000003');
INSERT INTO NBPT2.media_outlets (id, name, slug, website, kind, logo_id)
VALUES ('80000000-0000-0000-0000-000000000009', 'Business Journal', 'business-journal', 'https://businessjournal.example.com', 'PRINT', '00000000-0000-0000-0000-000000000004');
INSERT INTO NBPT2.media_outlets (id, name, slug, website, kind, logo_id)
VALUES ('80000000-0000-0000-0000-00000000000a', 'Online Gazette', 'online-gazette', 'https://gazette.example.com', 'ONLINE', '00000000-0000-0000-0000-000000000005');

-- 10) STATS (10 rows, linked to first two boards with unique sort per board)
INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
VALUES ('90000000-0000-0000-0000-000000000001', '10000000-0000-0000-0000-000000000001', 'Visitors', 1000, NULL, 1, 1, 'people');
INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
VALUES ('90000000-0000-0000-0000-000000000002', '10000000-0000-0000-0000-000000000001', 'Companies', 30, NULL, 1, 2, 'building');
INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
VALUES ('90000000-0000-0000-0000-000000000003', '10000000-0000-0000-0000-000000000001', 'Workshops', 10, NULL, 0, 3, 'presentation');
INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
VALUES ('90000000-0000-0000-0000-000000000004', '10000000-0000-0000-0000-000000000001', 'Interviews', 200, NULL, 1, 4, 'chat');
INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
VALUES ('90000000-0000-0000-0000-000000000005', '10000000-0000-0000-0000-000000000001', 'CV Reviews', 150, NULL, 0, 5, 'file');
INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
VALUES ('90000000-0000-0000-0000-000000000006', '10000000-0000-0000-0000-000000000002', 'Visitors', 1200, NULL, 1, 1, 'people');
INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
VALUES ('90000000-0000-0000-0000-000000000007', '10000000-0000-0000-0000-000000000002', 'Companies', 35, NULL, 1, 2, 'building');
INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
VALUES ('90000000-0000-0000-0000-000000000008', '10000000-0000-0000-0000-000000000002', 'Workshops', 12, NULL, 0, 3, 'presentation');
INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
VALUES ('90000000-0000-0000-0000-000000000009', '10000000-0000-0000-0000-000000000002', 'Interviews', 220, NULL, 1, 4, 'chat');
INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
VALUES ('90000000-0000-0000-0000-00000000000a', '10000000-0000-0000-0000-000000000002', 'CV Reviews', 180, NULL, 0, 5, 'file');

-- 11) COMMITTEE_MEMBERS (10 rows)
INSERT INTO NBPT2.committee_members (id, committee_id, person_id, role)
VALUES ('a0000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001', 'President');
INSERT INTO NBPT2.committee_members (id, committee_id, person_id, role)
VALUES ('a0000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000002', 'Vice President');
INSERT INTO NBPT2.committee_members (id, committee_id, person_id, role)
VALUES ('a0000000-0000-0000-0000-000000000003', '30000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000003', 'Member');
INSERT INTO NBPT2.committee_members (id, committee_id, person_id, role)
VALUES ('a0000000-0000-0000-0000-000000000004', '30000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000004', 'Member');
INSERT INTO NBPT2.committee_members (id, committee_id, person_id, role)
VALUES ('a0000000-0000-0000-0000-000000000005', '30000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000005', 'Member');
INSERT INTO NBPT2.committee_members (id, committee_id, person_id, role)
VALUES ('a0000000-0000-0000-0000-000000000006', '30000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000006', 'President');
INSERT INTO NBPT2.committee_members (id, committee_id, person_id, role)
VALUES ('a0000000-0000-0000-0000-000000000007', '30000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000007', 'Member');
INSERT INTO NBPT2.committee_members (id, committee_id, person_id, role)
VALUES ('a0000000-0000-0000-0000-000000000008', '30000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000008', 'Member');
INSERT INTO NBPT2.committee_members (id, committee_id, person_id, role)
VALUES ('a0000000-0000-0000-0000-000000000009', '30000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000009', 'Member');
INSERT INTO NBPT2.committee_members (id, committee_id, person_id, role)
VALUES ('a0000000-0000-0000-0000-00000000000a', '30000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-00000000000a', 'Member');

-- 12) PARTICIPATIONS (10 rows, unique organization/year combinations)
INSERT INTO NBPT2.participations (id, year, organization_id, package_tier_id)
VALUES ('b0000000-0000-0000-0000-000000000001', 2022, '40000000-0000-0000-0000-000000000001', '50000000-0000-0000-0000-000000000001');
INSERT INTO NBPT2.participations (id, year, organization_id, package_tier_id)
VALUES ('b0000000-0000-0000-0000-000000000002', 2023, '40000000-0000-0000-0000-000000000001', '50000000-0000-0000-0000-000000000002');
INSERT INTO NBPT2.participations (id, year, organization_id, package_tier_id)
VALUES ('b0000000-0000-0000-0000-000000000003', 2022, '40000000-0000-0000-0000-000000000002', '50000000-0000-0000-0000-000000000002');
INSERT INTO NBPT2.participations (id, year, organization_id, package_tier_id)
VALUES ('b0000000-0000-0000-0000-000000000004', 2023, '40000000-0000-0000-0000-000000000002', '50000000-0000-0000-0000-000000000003');
INSERT INTO NBPT2.participations (id, year, organization_id, package_tier_id)
VALUES ('b0000000-0000-0000-0000-000000000005', 2022, '40000000-0000-0000-0000-000000000003', '50000000-0000-0000-0000-000000000003');
INSERT INTO NBPT2.participations (id, year, organization_id, package_tier_id)
VALUES ('b0000000-0000-0000-0000-000000000006', 2023, '40000000-0000-0000-0000-000000000003', '50000000-0000-0000-0000-000000000004');
INSERT INTO NBPT2.participations (id, year, organization_id, package_tier_id)
VALUES ('b0000000-0000-0000-0000-000000000007', 2022, '40000000-0000-0000-0000-000000000004', '50000000-0000-0000-0000-000000000004');
INSERT INTO NBPT2.participations (id, year, organization_id, package_tier_id)
VALUES ('b0000000-0000-0000-0000-000000000008', 2023, '40000000-0000-0000-0000-000000000004', '50000000-0000-0000-0000-000000000005');
INSERT INTO NBPT2.participations (id, year, organization_id, package_tier_id)
VALUES ('b0000000-0000-0000-0000-000000000009', 2022, '40000000-0000-0000-0000-000000000005', '50000000-0000-0000-0000-000000000005');
INSERT INTO NBPT2.participations (id, year, organization_id, package_tier_id)
VALUES ('b0000000-0000-0000-0000-00000000000a', 2023, '40000000-0000-0000-0000-000000000005', '50000000-0000-0000-0000-000000000001');

-- 13) GALLERY_IMAGES (10 rows)
INSERT INTO NBPT2.gallery_images (id, organization_id, media_id, caption, display_order)
VALUES ('c0000000-0000-0000-0000-000000000001', '40000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'Booth photo 1', 1);
INSERT INTO NBPT2.gallery_images (id, organization_id, media_id, caption, display_order)
VALUES ('c0000000-0000-0000-0000-000000000002', '40000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000002', 'Booth photo 2', 2);
INSERT INTO NBPT2.gallery_images (id, organization_id, media_id, caption, display_order)
VALUES ('c0000000-0000-0000-0000-000000000003', '40000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000003', 'Presentation', 1);
INSERT INTO NBPT2.gallery_images (id, organization_id, media_id, caption, display_order)
VALUES ('c0000000-0000-0000-0000-000000000004', '40000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000004', 'Team photo', 2);
INSERT INTO NBPT2.gallery_images (id, organization_id, media_id, caption, display_order)
VALUES ('c0000000-0000-0000-0000-000000000005', '40000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000005', 'Workshop', 1);
INSERT INTO NBPT2.gallery_images (id, organization_id, media_id, caption, display_order)
VALUES ('c0000000-0000-0000-0000-000000000006', '40000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000006', 'Workshop 2', 2);
INSERT INTO NBPT2.gallery_images (id, organization_id, media_id, caption, display_order)
VALUES ('c0000000-0000-0000-0000-000000000007', '40000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000007', 'Networking', 1);
INSERT INTO NBPT2.gallery_images (id, organization_id, media_id, caption, display_order)
VALUES ('c0000000-0000-0000-0000-000000000008', '40000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000008', 'Networking 2', 2);
INSERT INTO NBPT2.gallery_images (id, organization_id, media_id, caption, display_order)
VALUES ('c0000000-0000-0000-0000-000000000009', '40000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000009', 'Keynote', 1);
INSERT INTO NBPT2.gallery_images (id, organization_id, media_id, caption, display_order)
VALUES ('c0000000-0000-0000-0000-00000000000a', '40000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-00000000000a', 'Keynote 2', 2);

-- 14) ARTICLE_IMAGES (10 rows)
INSERT INTO NBPT2.article_images (id, article_id, media_id, caption, display_order)
VALUES ('d0000000-0000-0000-0000-000000000001', '60000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'Article image 1', 1);
INSERT INTO NBPT2.article_images (id, article_id, media_id, caption, display_order)
VALUES ('d0000000-0000-0000-0000-000000000002', '60000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000002', 'Article image 2', 2);
INSERT INTO NBPT2.article_images (id, article_id, media_id, caption, display_order)
VALUES ('d0000000-0000-0000-0000-000000000003', '60000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000003', 'Article image 3', 1);
INSERT INTO NBPT2.article_images (id, article_id, media_id, caption, display_order)
VALUES ('d0000000-0000-0000-0000-000000000004', '60000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000004', 'Article image 4', 1);
INSERT INTO NBPT2.article_images (id, article_id, media_id, caption, display_order)
VALUES ('d0000000-0000-0000-0000-000000000005', '60000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000005', 'Article image 5', 1);
INSERT INTO NBPT2.article_images (id, article_id, media_id, caption, display_order)
VALUES ('d0000000-0000-0000-0000-000000000006', '60000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000006', 'Article image 6', 1);
INSERT INTO NBPT2.article_images (id, article_id, media_id, caption, display_order)
VALUES ('d0000000-0000-0000-0000-000000000007', '60000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000007', 'Article image 7', 1);
INSERT INTO NBPT2.article_images (id, article_id, media_id, caption, display_order)
VALUES ('d0000000-0000-0000-0000-000000000008', '60000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000008', 'Article image 8', 1);
INSERT INTO NBPT2.article_images (id, article_id, media_id, caption, display_order)
VALUES ('d0000000-0000-0000-0000-000000000009', '60000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000009', 'Article image 9', 1);
INSERT INTO NBPT2.article_images (id, article_id, media_id, caption, display_order)
VALUES ('d0000000-0000-0000-0000-00000000000a', '60000000-0000-0000-0000-000000000009', '00000000-0000-0000-0000-00000000000a', 'Article image 10', 1);

-- 15) JOB_TAGS (10 rows, 2 tags for first 5 jobs)
INSERT INTO NBPT2.job_tags (job_id, tag_value)
VALUES ('70000000-0000-0000-0000-000000000001', 'java');
INSERT INTO NBPT2.job_tags (job_id, tag_value)
VALUES ('70000000-0000-0000-0000-000000000001', 'backend');
INSERT INTO NBPT2.job_tags (job_id, tag_value)
VALUES ('70000000-0000-0000-0000-000000000002', 'frontend');
INSERT INTO NBPT2.job_tags (job_id, tag_value)
VALUES ('70000000-0000-0000-0000-000000000002', 'javascript');
INSERT INTO NBPT2.job_tags (job_id, tag_value)
VALUES ('70000000-0000-0000-0000-000000000003', 'data');
INSERT INTO NBPT2.job_tags (job_id, tag_value)
VALUES ('70000000-0000-0000-0000-000000000003', 'analytics');
INSERT INTO NBPT2.job_tags (job_id, tag_value)
VALUES ('70000000-0000-0000-0000-000000000004', 'devops');
INSERT INTO NBPT2.job_tags (job_id, tag_value)
VALUES ('70000000-0000-0000-0000-000000000004', 'cloud');
INSERT INTO NBPT2.job_tags (job_id, tag_value)
VALUES ('70000000-0000-0000-0000-000000000005', 'research');
INSERT INTO NBPT2.job_tags (job_id, tag_value)
VALUES ('70000000-0000-0000-0000-000000000005', 'engineering');

-- 16) MEDIA_PARTICIPATIONS (10 rows, unique outlet/year combinations)
INSERT INTO NBPT2.media_participations (id, outlet_id, year, tier)
VALUES ('e0000000-0000-0000-0000-000000000001', '80000000-0000-0000-0000-000000000001', 2022, 'GOLD');
INSERT INTO NBPT2.media_participations (id, outlet_id, year, tier)
VALUES ('e0000000-0000-0000-0000-000000000002', '80000000-0000-0000-0000-000000000001', 2023, 'GOLD');
INSERT INTO NBPT2.media_participations (id, outlet_id, year, tier)
VALUES ('e0000000-0000-0000-0000-000000000003', '80000000-0000-0000-0000-000000000002', 2022, 'SILVER');
INSERT INTO NBPT2.media_participations (id, outlet_id, year, tier)
VALUES ('e0000000-0000-0000-0000-000000000004', '80000000-0000-0000-0000-000000000002', 2023, 'SILVER');
INSERT INTO NBPT2.media_participations (id, outlet_id, year, tier)
VALUES ('e0000000-0000-0000-0000-000000000005', '80000000-0000-0000-0000-000000000003', 2022, 'BRONZE');
INSERT INTO NBPT2.media_participations (id, outlet_id, year, tier)
VALUES ('e0000000-0000-0000-0000-000000000006', '80000000-0000-0000-0000-000000000003', 2023, 'BRONZE');
INSERT INTO NBPT2.media_participations (id, outlet_id, year, tier)
VALUES ('e0000000-0000-0000-0000-000000000007', '80000000-0000-0000-0000-000000000004', 2022, 'PARTNER');
INSERT INTO NBPT2.media_participations (id, outlet_id, year, tier)
VALUES ('e0000000-0000-0000-0000-000000000008', '80000000-0000-0000-0000-000000000004', 2023, 'PARTNER');
INSERT INTO NBPT2.media_participations (id, outlet_id, year, tier)
VALUES ('e0000000-0000-0000-0000-000000000009', '80000000-0000-0000-0000-000000000005', 2022, 'PARTNER');
INSERT INTO NBPT2.media_participations (id, outlet_id, year, tier)
VALUES ('e0000000-0000-0000-0000-00000000000a', '80000000-0000-0000-0000-000000000005', 2023, 'PARTNER');
