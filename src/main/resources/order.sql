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

 Date: 13/06/2018 21:43:57
*/


-- ----------------------------
-- Sequence structure for seq_order
-- ----------------------------
DROP SEQUENCE IF EXISTS "order"."seq_order";
CREATE SEQUENCE "order"."seq_order" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

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
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS "order"."t_order";
CREATE TABLE "order"."t_order" (
  "order_id" int4 NOT NULL,
  "time" timestamp(6),
  "user_email" text COLLATE "pg_catalog"."default",
  "price" float8,
  "status" text COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO "order"."t_order" VALUES (5, '2018-06-09 12:35:27', 'skuilffunny@gmail.com', 157, 'PAID');

-- ----------------------------
-- Table structure for t_order_info
-- ----------------------------
DROP TABLE IF EXISTS "order"."t_order_info";
CREATE TABLE "order"."t_order_info" (
  "order_num" int4,
  "product_id" text COLLATE "pg_catalog"."default",
  "count" int4,
  "price" float4
)
;

-- ----------------------------
-- Records of t_order_info
-- ----------------------------
INSERT INTO "order"."t_order_info" VALUES (5, 'А1522', 15, 10);
INSERT INTO "order"."t_order_info" VALUES (5, 'A1523', 7, 1);

-- ----------------------------
-- Function structure for buy
-- ----------------------------
DROP FUNCTION IF EXISTS "order"."buy"("p_email" text, "p_product" _text, "p_count" _int4, "p_prices" _float4);
CREATE OR REPLACE FUNCTION "order"."buy"("p_email" text, "p_product" _text, "p_count" _int4, "p_prices" _float4)
  RETURNS "pg_catalog"."int4" AS $BODY$DECLARE
	l_order_id int4;
  l_all_price float8;
BEGIN
  l_all_price = 0;
	
	-- ид заказа
	SELECT nextval('order.seq_order') INTO l_order_id;
	
	-- в цикле запишем состав заказа и посчитаем общую стоимость
	FOR i IN array_lower(p_product, 1) .. array_upper(p_product, 1)
  LOOP
		l_all_price = l_all_price + (p_prices[i] * p_count[i]);
		
		insert into "order".t_order_info ("order_num", "product_id", "price", "count")
		values (l_order_id, p_product[i], p_prices[i], p_count[i]);
  END LOOP;
	
	insert into "order".t_order("order_id", "time", "user_email", "price", "status")
	values (l_order_id, timezone('utc-5', CURRENT_TIMESTAMP(0)), p_email, l_all_price, 'WAITING');
	
	return l_order_id;
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

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
-- Function structure for get__all_count
-- ----------------------------
DROP FUNCTION IF EXISTS "order"."get__all_count"();
CREATE OR REPLACE FUNCTION "order"."get__all_count"()
  RETURNS "pg_catalog"."int4" AS $BODY$DECLARE
	l_count int4;
begin
  select count(*) from "order".t_order t into l_count;
	return l_count;
end;
$BODY$
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
-- Function structure for get_history
-- ----------------------------
DROP FUNCTION IF EXISTS "order"."get_history"("p_email" text, "p_page" int4);
CREATE OR REPLACE FUNCTION "order"."get_history"("p_email" text, "p_page" int4)
  RETURNS "pg_catalog"."refcursor" AS $BODY$ 
	DECLARE
		res refcursor;
		l_from int4;
		l_to   int4;
BEGIN
	l_from := (p_page - 1) * 20;
	l_to   := p_page * 20;
	open res for 
	SELECT
		x.*
	FROM (SELECT t.*, ROW_NUMBER () OVER (ORDER BY time) FROM "order".t_order t ) x
		WHERE x."user_email" = p_email and ROW_NUMBER BETWEEN l_from AND l_to;
	
RETURN res;

END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for get_history_count
-- ----------------------------
DROP FUNCTION IF EXISTS "order"."get_history_count"("p_email" text);
CREATE OR REPLACE FUNCTION "order"."get_history_count"("p_email" text)
  RETURNS "pg_catalog"."int4" AS $BODY$ 
	DECLARE
	l_result int4;
BEGIN

SELECT count(*) FROM "order".t_order t where t.user_email = p_email into l_result;
	
RETURN l_result;

END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for get_page
-- ----------------------------
DROP FUNCTION IF EXISTS "order"."get_page"("p_page" int4);
CREATE OR REPLACE FUNCTION "order"."get_page"("p_page" int4)
  RETURNS "pg_catalog"."refcursor" AS $BODY$ 
	DECLARE
		res refcursor;
		l_from int4;
		l_to   int4;
BEGIN
	l_from := (p_page - 1) * 20;
	l_to   := p_page * 20;
	open res for 
	SELECT
		x.*
	FROM (SELECT t.*, ROW_NUMBER () OVER (ORDER BY time) FROM "order".t_order t ) x
		WHERE ROW_NUMBER BETWEEN l_from AND l_to;
	
RETURN res;

END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for get_with_state_count
-- ----------------------------
DROP FUNCTION IF EXISTS "order"."get_with_state_count"("p_state" text);
CREATE OR REPLACE FUNCTION "order"."get_with_state_count"("p_state" text)
  RETURNS "pg_catalog"."int4" AS $BODY$DECLARE
	l_count int4;
begin
  select count(*) from "order".t_order t where t.status = "p_state" into l_count;
	return l_count;
end;
$BODY$
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
-- Function structure for set_status
-- ----------------------------
DROP FUNCTION IF EXISTS "order"."set_status"("p_order" int4, "p_status" text);
CREATE OR REPLACE FUNCTION "order"."set_status"("p_order" int4, "p_status" text)
  RETURNS "pg_catalog"."void" AS $BODY$ 
	DECLARE
BEGIN	update "order".t_order set status = p_status where order_id = p_order;
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"order"."seq_order"', 6, true);

-- ----------------------------
-- Primary Key structure for table t_order
-- ----------------------------
ALTER TABLE "order"."t_order" ADD CONSTRAINT "t_order_pkey" PRIMARY KEY ("order_id");

-- ----------------------------
-- Foreign Keys structure for table t_basket
-- ----------------------------
ALTER TABLE "order"."t_basket" ADD CONSTRAINT "fk_prod" FOREIGN KEY ("product") REFERENCES "public"."t_products" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "order"."t_basket" ADD CONSTRAINT "fk_session" FOREIGN KEY ("sessionId") REFERENCES "client"."t_client_sessions" ("sessionId") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_order_info
-- ----------------------------
ALTER TABLE "order"."t_order_info" ADD CONSTRAINT "fk_product" FOREIGN KEY ("product_id") REFERENCES "public"."t_products" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
