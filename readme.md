create db:


CREATE TABLE `DONE_IP` (
  `IP` varchar(255) NOT NULL,
  PRIMARY KEY (`IP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


Add index to LoginLog_copy1  , ip, city, province;

other db SQL:

SELECT *FROM gamedata.LoginLog_copy1 where city = null;
-- SELECT distinct(city) FROM gamedata.LoginLog_copy1;
-- SELECT distinct(province) FROM gamedata.LoginLog_copy1;
-- SELECT count(1) from gamedata.DONE_IP;
select count(province) from gamedata.LoginLog_copy1;
