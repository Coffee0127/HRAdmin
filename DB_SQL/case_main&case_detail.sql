DROP TABLE IF EXISTS CASE_DETAIL;
DROP TABLE IF EXISTS CASE_MAIN;

CREATE TABLE CASE_MAIN (
    CASE_ID VARCHAR(32) NOT NULL COMMENT '案件編號',
    CASE_STATUS VARCHAR(3) NOT NULL COMMENT '案件狀態',
    PRE_CASE_STATUS VARCHAR(3) COMMENT '前次案件狀態',
    UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新時間',
    DEPT VARCHAR(30) NOT NULL COMMENT '部門',
    UNIT VARCHAR(30) NOT NULL COMMENT '單位',
    PROJECT_NAME VARCHAR(50) NOT NULL COMMENT '需求人力專案名稱',
    PROJECT_CODE VARCHAR(20) NOT NULL COMMENT '需求人力專案代號',
    HRM_ROLE VARCHAR(30) NOT NULL COMMENT '需求人力角色',
    HRM_TYPE VARCHAR(20) NOT NULL COMMENT '需求人員類別',
    REQUIRED_COUNT INT NOT NULL COMMENT '需求人數',
    REQUIRED_SKILL VARCHAR(255) NOT NULL COMMENT 'Skill',
    REQUIRED_BEGIN_DATE DATE COMMENT '人力需求起日',
    REQUIRED_END_DATE DATE COMMENT '人力需求訖日',
    REASON VARCHAR(100) COMMENT '增補原因',
    NOTE TEXT COMMENT '備註',
    APPLIER VARCHAR(30) NOT NULL COMMENT '填寫人',
    HR_PROCESS TEXT COMMENT 'HR處理',

    CONSTRAINT CASE_MAIN_PK PRIMARY KEY (CASE_ID)
);

CREATE TABLE CASE_DETAIL (
    DETAIL_ID BIGINT AUTO_INCREMENT NOT NULL COMMENT '訊息編號',
    CASE_ID VARCHAR(32) NOT NULL COMMENT '案件編號',
    CASE_STATUS VARCHAR(3) NOT NULL COMMENT '案件狀態',
    UPDATER VARCHAR(30) NOT NULL COMMENT '處理人',
    MSG_DETAIL TEXT COMMENT '訊息內容',
    UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新時間',

    CONSTRAINT CASE_DETAIL_PK PRIMARY KEY (DETAIL_ID),
    CONSTRAINT CASE_DETAIL_FK FOREIGN KEY (CASE_ID) REFERENCES CASE_MAIN (CASE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
