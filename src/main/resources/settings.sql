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

 Date: 30/05/2018 12:02:22
*/


-- ----------------------------
-- Table structure for t_settings
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_settings";
CREATE TABLE "settings"."t_settings" (
  "title" text COLLATE "pg_catalog"."default",
  "host" text COLLATE "pg_catalog"."default",
  "port" int4,
  "user" text COLLATE "pg_catalog"."default",
  "password" text COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of t_settings
-- ----------------------------
INSERT INTO "settings"."t_settings" VALUES ('Веломагазин «Speed OF Life»', 'mail.ru', 0, 'admin', 'acef13da09');

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
-- Function structure for save_settings
-- ----------------------------
DROP FUNCTION IF EXISTS "settings"."save_settings"("p_title" text, "p_host" text, "p_port" int4, "p_user" text, "p_password" text);
CREATE OR REPLACE FUNCTION "settings"."save_settings"("p_title" text, "p_host" text, "p_port" int4, "p_user" text, "p_password" text)
  RETURNS "pg_catalog"."void" AS $BODY$DECLARE 
BEGIN 
update settings.t_settings set 
"title" = p_title, 
"host" = p_host, 
"port" = p_port, 
"user" = p_user, 
"password" = p_password; 
END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
