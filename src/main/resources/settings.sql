/*
Navicat PGSQL Data Transfer

Source Server         : test
Source Server Version : 100400
Source Host           : localhost:5432
Source Database       : shop
Source Schema         : settings

Target Server Type    : PGSQL
Target Server Version : 100400
File Encoding         : 65001

Date: 2018-06-01 17:59:18
*/


-- ----------------------------
-- Table structure for t_best_product
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_best_product";
CREATE TABLE "settings"."t_best_product" (
"product_id" text COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of t_best_product
-- ----------------------------

-- ----------------------------
-- Table structure for t_carousel
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_carousel";
CREATE TABLE "settings"."t_carousel" (
"url" text COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of t_carousel
-- ----------------------------

-- ----------------------------
-- Table structure for t_settings
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_settings";
CREATE TABLE "settings"."t_settings" (
"key" text COLLATE "default",
"value" text COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of t_settings
-- ----------------------------
INSERT INTO "settings"."t_settings" VALUES ('shop.title', 'Веломагазин «Speed OF Life»');
INSERT INTO "settings"."t_settings" VALUES ('smtp.host', '');
INSERT INTO "settings"."t_settings" VALUES ('smtp.port', '0');
INSERT INTO "settings"."t_settings" VALUES ('smtp.pwd', '');
INSERT INTO "settings"."t_settings" VALUES ('smtp.user', '');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table t_best_product
-- ----------------------------
ALTER TABLE "settings"."t_best_product" ADD PRIMARY KEY ("product_id");

-- ----------------------------
-- Foreign Key structure for table "settings"."t_best_product"
-- ----------------------------
ALTER TABLE "settings"."t_best_product" ADD FOREIGN KEY ("product_id") REFERENCES "public"."t_products" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;


CREATE OR REPLACE FUNCTION "settings"."get_carousel"()
  RETURNS "pg_catalog"."refcursor" AS $BODY$
DECLARE
res refcursor;
BEGIN
open res for
SELECT
* FROM settings.t_carousel;
RETURN res;

END; $BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

ALTER FUNCTION "settings"."get_carousel"() OWNER TO "postgres";

CREATE OR REPLACE FUNCTION "settings"."get_settings"()
  RETURNS "pg_catalog"."refcursor" AS $BODY$
DECLARE
res refcursor;
BEGIN
open res for
SELECT
* FROM settings.t_settings;
RETURN res;

END; $BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

ALTER FUNCTION "settings"."get_settings"() OWNER TO "postgres";

CREATE OR REPLACE FUNCTION "settings"."save_settings"("p_title" text, "p_host" text, "p_port" text, "p_user" text, "p_password" text)
  RETURNS "pg_catalog"."void" AS $BODY$DECLARE
BEGIN
update settings.t_settings set
	"value" = p_title where "key" = 'shop.title';

update settings.t_settings set
	"value" = p_host where "key" = 'smtp.host';

update settings.t_settings set
	"value" = p_port where "key" = 'smtp.port';

update settings.t_settings set
	"value" = p_user where "key" = 'smtp.user';

update settings.t_settings set
	"value" = p_password where "key" = 'smtp.pwd';

END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

ALTER FUNCTION "settings"."save_settings"("p_title" text, "p_host" text, "p_port" text, "p_user" text, "p_password" text) OWNER TO "postgres";