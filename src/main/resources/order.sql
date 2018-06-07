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

 Date: 07/06/2018 13:13:59
*/


-- ----------------------------
-- Table structure for t_basket
-- ----------------------------
DROP TABLE IF EXISTS "order"."t_basket";
CREATE TABLE "order"."t_basket" (
  "sessionId" text COLLATE "pg_catalog"."default",
  "product" text COLLATE "pg_catalog"."default",
  "count" int4
)
;

-- ----------------------------
-- Records of t_basket
-- ----------------------------
INSERT INTO "order"."t_basket" VALUES ('2V9CTSPIY9WCK88NMV5K1TMU9Q6OFKHIWRE3M7UISPFD8F3XN5W9QKUBH8VHAZ0T', 'А1522', 10);
INSERT INTO "order"."t_basket" VALUES ('2V9CTSPIY9WCK88NMV5K1TMU9Q6OFKHIWRE3M7UISPFD8F3XN5W9QKUBH8VHAZ0T', 'custom2', 8);
INSERT INTO "order"."t_basket" VALUES ('2V9CTSPIY9WCK88NMV5K1TMU9Q6OFKHIWRE3M7UISPFD8F3XN5W9QKUBH8VHAZ0T', 'custom3', 2);

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
-- Function structure for clear_basket
-- ----------------------------
DROP FUNCTION IF EXISTS "order"."clear_basket"("p_sessionId" text);
CREATE OR REPLACE FUNCTION "order"."clear_basket"("p_sessionId" text)
  RETURNS "pg_catalog"."void" AS $BODY$ DECLARE
BEGIN
	 delete from "order".t_basket where "sessionId" = "p_sessionId";
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for get_basket
-- ----------------------------
DROP FUNCTION IF EXISTS "order"."get_basket"("p_sessionId" text);
CREATE OR REPLACE FUNCTION "order"."get_basket"("p_sessionId" text)
  RETURNS "pg_catalog"."refcursor" AS $BODY$ DECLARE
	res refcursor;
BEGIN
	OPEN res FOR SELECT * from "order"."t_basket" where "sessionId" = "p_sessionId";
RETURN res;

END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for save_basket
-- ----------------------------
DROP FUNCTION IF EXISTS "order"."save_basket"("p_sessionId" text, "p_product" _text, "p_count" _int4);
CREATE OR REPLACE FUNCTION "order"."save_basket"("p_sessionId" text, "p_product" _text, "p_count" _int4)
  RETURNS "pg_catalog"."void" AS $BODY$ DECLARE
BEGIN
	 delete from "order".t_basket where "sessionId" = "p_sessionId";
	 FOR i IN array_lower(p_product, 1) .. array_upper(p_product, 1)
   LOOP
			insert into "order".t_basket ("sessionId", "product", "count")
			values ("p_sessionId", p_product[i], p_count[i]);
   END LOOP;
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Foreign Keys structure for table t_basket
-- ----------------------------
ALTER TABLE "order"."t_basket" ADD CONSTRAINT "fk_prod" FOREIGN KEY ("product") REFERENCES "public"."t_products" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "order"."t_basket" ADD CONSTRAINT "fk_session" FOREIGN KEY ("sessionId") REFERENCES "client"."t_client_sessions" ("sessionId") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_orders
-- ----------------------------
ALTER TABLE "order"."t_orders" ADD CONSTRAINT "fk_client" FOREIGN KEY ("client_email") REFERENCES "client"."t_clients" ("email") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "order"."t_orders" ADD CONSTRAINT "fk_product" FOREIGN KEY ("product_id") REFERENCES "public"."t_products" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
