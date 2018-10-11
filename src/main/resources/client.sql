/*
 Navicat Premium Data Transfer

 Source Server         : store
 Source Server Type    : PostgreSQL
 Source Server Version : 90604
 Source Host           : localhost:5432
 Source Catalog        : shop
 Source Schema         : client

 Target Server Type    : PostgreSQL
 Target Server Version : 90604
 File Encoding         : 65001

 Date: 11/10/2018 12:14:45
*/


-- ----------------------------
-- Table structure for t_client_sessions
-- ----------------------------
DROP TABLE IF EXISTS "client"."t_client_sessions";
CREATE TABLE "client"."t_client_sessions" (
  "client_email" text COLLATE "pg_catalog"."default" NOT NULL,
  "sessionId" text COLLATE "pg_catalog"."default" NOT NULL,
  "expire" timestamp(6)
)
;

-- ----------------------------
-- Records of t_client_sessions
-- ----------------------------
INSERT INTO "client"."t_client_sessions" VALUES ('vadimkamkb@mail.ru', '2RYQ59OO87EPATDE6KVTYBODJNLZ9GDKNU76QB4VD9CW4IYB4C47E345SK42LFFY', '2018-10-25 09:22:50.894');

-- ----------------------------
-- Table structure for t_clients
-- ----------------------------
DROP TABLE IF EXISTS "client"."t_clients";
CREATE TABLE "client"."t_clients" (
  "email" text COLLATE "pg_catalog"."default" NOT NULL,
  "password" text COLLATE "pg_catalog"."default",
  "name" text COLLATE "pg_catalog"."default",
  "dispatch" bool
)
;

-- ----------------------------
-- Records of t_clients
-- ----------------------------
INSERT INTO "client"."t_clients" VALUES ('skuilffunny@gmail.com', 'e0113ca7a1d204db34ad4434267870c5256146ca915510d791b84a0cfa09857eaf251a2d7e160094d230f6a7b6b6560fb52ead742d13e5526a5f3650653402b2', '123', 't');
INSERT INTO "client"."t_clients" VALUES ('mmm@mail.ru', '0dd3e512642c97ca3f747f9a76e374fbda73f9292823c0313be9d78add7cdd8f72235af0c553dd26797e78e1854edee0ae002f8aba074b066dfce1af114e32f8', 'f', 'f');
INSERT INTO "client"."t_clients" VALUES ('vadimkamkb@mail.ru', '0dd3e512642c97ca3f747f9a76e374fbda73f9292823c0313be9d78add7cdd8f72235af0c553dd26797e78e1854edee0ae002f8aba074b066dfce1af114e32f8', 'Вадим ', 'f');

-- ----------------------------
-- Function structure for get_all_client
-- ----------------------------
DROP FUNCTION IF EXISTS "client"."get_all_client"();
CREATE OR REPLACE FUNCTION "client"."get_all_client"()
  RETURNS "pg_catalog"."refcursor" AS $BODY$ DECLARE
	res refcursor;
BEGIN
	OPEN res FOR 
		SELECT t.email FROM "client".t_clients t where t."dispatch" = true;
		
RETURN res;

END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for get_client
-- ----------------------------
DROP FUNCTION IF EXISTS "client"."get_client"("p_session" text);
CREATE OR REPLACE FUNCTION "client"."get_client"("p_session" text)
  RETURNS "pg_catalog"."refcursor" AS $BODY$ DECLARE
	res refcursor;
BEGIN
	OPEN res FOR 
		SELECT t.* FROM "client".t_clients t, "client"."t_client_sessions" WHERE "client"."t_client_sessions"."sessionId" = p_session;
		
RETURN res;

END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for get_client
-- ----------------------------
DROP FUNCTION IF EXISTS "client"."get_client"("p_email" text, "p_pwd" text);
CREATE OR REPLACE FUNCTION "client"."get_client"("p_email" text, "p_pwd" text)
  RETURNS "pg_catalog"."refcursor" AS $BODY$ DECLARE
	res refcursor;
BEGIN
	OPEN res FOR 
		SELECT * FROM "client".t_clients t
			where t.email = "p_email" and t.password = "p_pwd";
		
RETURN res;

END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for save_client
-- ----------------------------
DROP FUNCTION IF EXISTS "client"."save_client"("p_email" text, "p_pwd" text, "p_name" text, "p_dispatch" bool);
CREATE OR REPLACE FUNCTION "client"."save_client"("p_email" text, "p_pwd" text, "p_name" text, "p_dispatch" bool)
  RETURNS "pg_catalog"."void" AS $BODY$
	DECLARE
begin
  insert into client.t_clients ("email", "password", "name", "dispatch")
	values (p_email, p_pwd, p_name, p_dispatch);				
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for save_session
-- ----------------------------
DROP FUNCTION IF EXISTS "client"."save_session"("p_email" text, "p_expire" timestamp);
CREATE OR REPLACE FUNCTION "client"."save_session"("p_email" text, "p_expire" timestamp)
  RETURNS "pg_catalog"."text" AS $BODY$
declare
  l_sessionId text  := '';
	l_isConflict bool := true;
begin
  
	while l_isConflict loop
		begin
			l_isConflict := false;
			l_sessionId  := public.random_string(64);
		
			insert into "client".t_client_sessions("client_email", "sessionId", "expire") values (p_email, l_sessionId, p_expire);
		
			EXCEPTION
			when check_violation  then
				l_isConflict := true;		
			end;			
	end loop;
	
  return l_sessionId;
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Primary Key structure for table t_client_sessions
-- ----------------------------
ALTER TABLE "client"."t_client_sessions" ADD CONSTRAINT "t_client_sessions_pkey" PRIMARY KEY ("sessionId");

-- ----------------------------
-- Primary Key structure for table t_clients
-- ----------------------------
ALTER TABLE "client"."t_clients" ADD CONSTRAINT "t_clients_pkey" PRIMARY KEY ("email");

-- ----------------------------
-- Foreign Keys structure for table t_client_sessions
-- ----------------------------
ALTER TABLE "client"."t_client_sessions" ADD CONSTRAINT "fk_client" FOREIGN KEY ("client_email") REFERENCES "client"."t_clients" ("email") ON DELETE NO ACTION ON UPDATE NO ACTION;
