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

 Date: 29/05/2018 09:54:56
*/


-- ----------------------------
-- Table structure for t_best_product
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_best_product";
CREATE TABLE "settings"."t_best_product" (
  "fk_product_id" int4 NOT NULL
)
;

-- ----------------------------
-- Table structure for t_carousel
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_carousel";
CREATE TABLE "settings"."t_carousel" (
  "id" int4 NOT NULL,
  "image_path" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for t_mail
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_mail";
CREATE TABLE "settings"."t_mail" (
  "propety" varchar(255) COLLATE "pg_catalog"."default",
  "value" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for t_setting
-- ----------------------------
DROP TABLE IF EXISTS "settings"."t_setting";
CREATE TABLE "settings"."t_setting" (
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "enable" bool
)
;

-- ----------------------------
-- Primary Key structure for table t_best_product
-- ----------------------------
ALTER TABLE "settings"."t_best_product" ADD CONSTRAINT "t_best_product_pkey" PRIMARY KEY ("fk_product_id");

-- ----------------------------
-- Primary Key structure for table t_carousel
-- ----------------------------
ALTER TABLE "settings"."t_carousel" ADD CONSTRAINT "t_carousel_pkey" PRIMARY KEY ("id");
