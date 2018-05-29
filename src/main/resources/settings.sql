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

Date: 2018-05-29 17:19:23
*/


-- ----------------------------
-- Table structure for t_settings
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_settings";
CREATE TABLE "settings"."t_settings" (
"title" text COLLATE "default",
"host" text COLLATE "default",
"port" int4,
"user" text COLLATE "default",
"password" text COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of t_settings
-- ----------------------------
INSERT INTO "settings"."t_settings" VALUES ('Велосипедики малютки', 'mail.ru', '0', 'admin', 'acef13da09');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------
