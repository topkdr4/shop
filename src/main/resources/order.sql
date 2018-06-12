/*
Navicat PGSQL Data Transfer

Source Server         : store
Source Server Version : 90604
Source Host           : localhost:5432
Source Database       : shop
Source Schema         : order

Target Server Type    : PGSQL
Target Server Version : 90604
File Encoding         : 65001

Date: 2018-06-12 17:24:04
*/


-- ----------------------------
-- Sequence structure for seq_order
-- ----------------------------
DROP SEQUENCE IF EXISTS "order"."seq_order";
CREATE SEQUENCE "order"."seq_order"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 5
 CACHE 1;
SELECT setval('"order"."seq_order"', 5, true);

-- ----------------------------
-- Table structure for t_basket
-- ----------------------------
DROP TABLE IF EXISTS "order"."t_basket";
CREATE TABLE "order"."t_basket" (
"sessionId" text COLLATE "default",
"product" text COLLATE "default",
"count" int4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of t_basket
-- ----------------------------

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS "order"."t_order";
CREATE TABLE "order"."t_order" (
"order_id" int4 NOT NULL,
"time" timestamp(6),
"user_email" text COLLATE "default",
"price" float8,
"status" text COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO "order"."t_order" VALUES ('5', '2018-06-09 12:35:27', 'skuilffunny@gmail.com', '157', 'WAITING');

-- ----------------------------
-- Table structure for t_order_info
-- ----------------------------
DROP TABLE IF EXISTS "order"."t_order_info";
CREATE TABLE "order"."t_order_info" (
"order_num" int4,
"product_id" text COLLATE "default",
"count" int4,
"price" float4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of t_order_info
-- ----------------------------
INSERT INTO "order"."t_order_info" VALUES ('5', 'A1523', '7', '1');
INSERT INTO "order"."t_order_info" VALUES ('5', 'А1522', '15', '10');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table t_order
-- ----------------------------
ALTER TABLE "order"."t_order" ADD PRIMARY KEY ("order_id");

-- ----------------------------
-- Foreign Key structure for table "order"."t_basket"
-- ----------------------------
ALTER TABLE "order"."t_basket" ADD FOREIGN KEY ("product") REFERENCES "public"."t_products" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "order"."t_basket" ADD FOREIGN KEY ("sessionId") REFERENCES "client"."t_client_sessions" ("sessionId") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "order"."t_order_info"
-- ----------------------------
ALTER TABLE "order"."t_order_info" ADD FOREIGN KEY ("product_id") REFERENCES "public"."t_products" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
