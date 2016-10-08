DROP TABLE IF EXISTS `queueProperties`;
create table queueProperties(
	name varchar(200) primary key,
	displayName varchar(100),
	minResources varchar(100),
	maxResources varchar(100),	
	maxRunningApps varchar(100),
	maxAMShare varchar(100),
	weight varchar(100),
	minSharePreemptionTimeout varchar(100),
	fairSharePreemptionTimeout varchar(100),
	fairSharePreemptionThreshold varchar(100),
	schedulingMode varchar(100),
	aclSubmitApps varchar(100),
	aclAdministerApps varchar(100)
)engine=innodb default charset=utf8;

DROP TABLE IF EXISTS `userProperties`;
create table userProperties(
	name varchar(200) primary key,
	maxRunningJobs varchar(100)
)engine=innodb default charset=utf8;

DROP TABLE IF EXISTS `defaultProperties`;
create table defaultProperties(
	id int primary key auto_increment,
	queueMaxAppsDefault varchar(100), 
	defaultMinSharePreemptionTimeout varchar(100),
	defaultQueueSchedulingMode varchar(100),
	userMaxAppsDefault varchar(100),
	defaultFairSharePreemptionTimeout varchar(100),
	defaultFairSharePreemptionThreshold varchar(100),
	queueMaxAMShareDefault varchar(100)
)engine=innodb default charset=utf8;

DROP TABLE IF EXISTS `queuePlacementPolicy`;
create table queuePlacementPolicy(
	name varchar(200) primary key,
	`create` varchar(100),
	queue varchar(200)
)engine=innodb default charset=utf8;

alter database fair_scheduler default character set utf8;
SET character_set_client=utf8;
SET character_set_connection=utf8;
SET character_set_results=utf8;