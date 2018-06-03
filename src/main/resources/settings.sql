/*
 Navicat Premium Data Transfer

 Source Server         : store
 Source Server Type    : PostgreSQL
 Source Server Version : 90604
 Source Host           : localhost:5432
 Source Catalog        : shop
 Source Schema         : settings

 Target Server Type    : PostgreSQL
 Target Server Version : 90604
 File Encoding         : 65001

 Date: 03/06/2018 16:26:57
*/


-- ----------------------------
-- Table structure for t_best_product
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_best_product";
CREATE TABLE "settings"."t_best_product" (
  "product_id" text COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for t_carousel
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_carousel";
CREATE TABLE "settings"."t_carousel" (
  "url" text COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of t_carousel
-- ----------------------------
INSERT INTO "settings"."t_carousel" VALUES ('http://bpic.588ku.com/back_pic/03/95/53/3457ed091204154.jpg');
INSERT INTO "settings"."t_carousel" VALUES ('http://www.bicyclecentreballarat.com.au/wp-content/uploads/2016/06/2.jpg');
INSERT INTO "settings"."t_carousel" VALUES ('http://www.bicyclecentreballarat.com.au/wp-content/uploads/2016/06/1.jpg');

-- ----------------------------
-- Table structure for t_settings
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_settings";
CREATE TABLE "settings"."t_settings" (
  "key" text COLLATE "pg_catalog"."default",
  "value" text COLLATE "pg_catalog"."default"
)
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
-- Function structure for get_carousel
-- ----------------------------
DROP FUNCTION IF EXISTS "settings"."get_carousel"();
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
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for get_settings
-- ----------------------------
DROP FUNCTION IF EXISTS "settings"."get_settings"();
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
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for save_carousel
-- ----------------------------
DROP FUNCTION IF EXISTS "settings"."save_carousel"("p_urls" _text);
CREATE OR REPLACE FUNCTION "settings"."save_carousel"("p_urls" _text)
  RETURNS "pg_catalog"."void" AS $BODY$DECLARE
	item text;
BEGIN
	delete from settings.t_carousel;
	foreach item in array p_urls loop
		insert into settings.t_carousel(url)
		values(item);
	end loop;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for save_settings
-- ----------------------------
DROP FUNCTION IF EXISTS "settings"."save_settings"("p_title" text, "p_host" text, "p_port" text, "p_user" text, "p_password" text);
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
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Primary Key structure for table t_best_product
-- ----------------------------
ALTER TABLE "settings"."t_best_product" ADD CONSTRAINT "t_best_product_pkey" PRIMARY KEY ("product_id");

-- ----------------------------
-- Foreign Keys structure for table t_best_product
-- ----------------------------
ALTER TABLE "settings"."t_best_product" ADD CONSTRAINT "t_best_product_product_id_fkey" FOREIGN KEY ("product_id") REFERENCES "public"."t_products" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
