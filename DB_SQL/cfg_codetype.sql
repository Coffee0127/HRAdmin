DROP TABLE IF EXISTS CFG_CODETYPE;

CREATE TABLE CFG_CODETYPE (
    codeId varchar(32) NOT NULL COMMENT '代碼ID',
    codeType varchar(32) NOT NULL COMMENT '代碼種類',
    codeValue varchar(300) NULL COMMENT '代碼對應之內容描述',
    updateUser varchar(10) NULL COMMENT '最後修改人',
    updateTime datetime NULL COMMENT '最後修改時間',
    CONSTRAINT CFG_CODETYPE_PK PRIMARY KEY (codeId, codeType)
);
