/*
 Navicat Premium Data Transfer

 Source Server         : store
 Source Server Type    : PostgreSQL
 Source Server Version : 90604
 Source Host           : localhost:5432
 Source Catalog        : shop
 Source Schema         : order

 Target Server Type    : PostgreSQL
 Target Server Version : 90604
 File Encoding         : 65001

 Date: 05/06/2018 12:21:04
*/


-- ----------------------------
-- Table structure for t_orders
-- ----------------------------
DROP TABLE IF EXISTS "order"."t_orders";
CREATE TABLE "order"."t_orders" (
  "client_email" text COLLATE "pg_catalog"."default",
  "order_num" int4,
  "product_id" text COLLATE "pg_catalog"."default",
  "price" float4,
  "date" timestamp(6)
)
;

-- ----------------------------
-- Foreign Keys structure for table t_orders
-- ----------------------------
ALTER TABLE "order"."t_orders" ADD CONSTRAINT "fk_client" FOREIGN KEY ("client_email") REFERENCES "client"."t_clients" ("email") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "order"."t_orders" ADD CONSTRAINT "fk_product" FOREIGN KEY ("product_id") REFERENCES "public"."t_products" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
