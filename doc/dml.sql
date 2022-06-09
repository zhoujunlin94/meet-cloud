USE `meet_base`;
INSERT INTO `cache_cfg` (`key`, `value`, `desc`)
VALUES ('xssConfig', '{\r\n	\"excludes\": \"/oauth/token\",\r\n	\"isIncludeRichText\": \"false\"\r\n}', 'xss配置');
