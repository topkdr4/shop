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

 Date: 13/06/2018 21:43:14
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
-- Records of t_best_product
-- ----------------------------
INSERT INTO "settings"."t_best_product" VALUES ('custom1');
INSERT INTO "settings"."t_best_product" VALUES ('A1523');

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
INSERT INTO "settings"."t_settings" VALUES ('smtp.host', 'smtp.mail.ru');
INSERT INTO "settings"."t_settings" VALUES ('smtp.port', '465');
INSERT INTO "settings"."t_settings" VALUES ('smtp.user', 'topkdr4@mail.ru');
INSERT INTO "settings"."t_settings" VALUES ('smtp.pwd', 'Crjhjcnmcdtnf229,');

-- ----------------------------
-- Table structure for t_templates
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_templates";
CREATE TABLE "settings"."t_templates" (
  "key" text COLLATE "pg_catalog"."default" NOT NULL,
  "value" text COLLATE "pg_catalog"."default",
  "desc" text COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of t_templates
-- ----------------------------
INSERT INTO "settings"."t_templates" VALUES ('after', '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head></head>
  <body>
    <b>ТЕКСТ</b> и текст и ололо
  </body>
</html>', 'Шаблон письма отправляемый пользователю после регистрации');
INSERT INTO "settings"."t_templates" VALUES ('Пример шаблона 1', '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head></head>
  <body>
  </body>
</html>', '');

-- ----------------------------
-- Function structure for get_best_product
-- ----------------------------
DROP FUNCTION IF EXISTS "settings"."get_best_product"();
CREATE OR REPLACE FUNCTION "settings"."get_best_product"()
  RETURNS "pg_catalog"."refcursor" AS $BODY$
DECLARE
res refcursor;
BEGIN
open res for
SELECT
* FROM settings.t_best_product;
RETURN res;

END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

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
-- Function structure for get_template
-- ----------------------------
DROP FUNCTION IF EXISTS "settings"."get_template"("p_key" text);
CREATE OR REPLACE FUNCTION "settings"."get_template"("p_key" text)
  RETURNS "pg_catalog"."refcursor" AS $BODY$
DECLARE
res refcursor;
BEGIN
open res for
	SELECT
	* FROM settings.t_templates t where t.key = p_key;
	
	RETURN res;

END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for get_template
-- ----------------------------
DROP FUNCTION IF EXISTS "settings"."get_template"();
CREATE OR REPLACE FUNCTION "settings"."get_template"()
  RETURNS "pg_catalog"."refcursor" AS $BODY$
DECLARE
res refcursor;
BEGIN
open res for
	SELECT
	* FROM settings.t_templates;
	
	RETURN res;

END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for remove_template
-- ----------------------------
DROP FUNCTION IF EXISTS "settings"."remove_template"("p_key" text);
CREATE OR REPLACE FUNCTION "settings"."remove_template"("p_key" text)
  RETURNS "pg_catalog"."void" AS $BODY$DECLARE
BEGIN
	delete from "settings".t_templates t where t.key = p_key;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for save_best_product
-- ----------------------------
DROP FUNCTION IF EXISTS "settings"."save_best_product"("p_codes" _text);
CREATE OR REPLACE FUNCTION "settings"."save_best_product"("p_codes" _text)
  RETURNS "pg_catalog"."void" AS $BODY$DECLARE
	item text;
BEGIN
	delete from settings.t_best_product;
	foreach item in array p_codes loop
		insert into settings.t_best_product(product_id)
		values(item);
	end loop;
END
$BODY$
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
-- Function structure for save_template
-- ----------------------------
DROP FUNCTION IF EXISTS "settings"."save_template"("p_key" text, "p_text" text, "p_desc" text);
CREATE OR REPLACE FUNCTION "settings"."save_template"("p_key" text, "p_text" text, "p_desc" text)
  RETURNS "pg_catalog"."void" AS $BODY$
	DECLARE
begin
  insert into "settings".t_templates ("key", "value", "desc")
	values (p_key, p_text, p_desc) on conflict ("key") do
		update set
				  "value" = p_text,
					"desc"  = p_desc;
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Primary Key structure for table t_best_product
-- ----------------------------
ALTER TABLE "settings"."t_best_product" ADD CONSTRAINT "t_best_product_pkey" PRIMARY KEY ("product_id");

-- ----------------------------
-- Primary Key structure for table t_templates
-- ----------------------------
ALTER TABLE "settings"."t_templates" ADD CONSTRAINT "t_templates_pkey" PRIMARY KEY ("key");

-- ----------------------------
-- Foreign Keys structure for table t_best_product
-- ----------------------------
ALTER TABLE "settings"."t_best_product" ADD CONSTRAINT "t_best_product_product_id_fkey" FOREIGN KEY ("product_id") REFERENCES "public"."t_products" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
