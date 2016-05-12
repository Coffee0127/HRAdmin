DROP TABLE IF EXISTS CFG_SYSPARAM;

CREATE TABLE CFG_SYSPARAM (
    paramId varchar(30) NOT NULL COMMENT '參數代碼',
    paramValue varchar(300) NOT NULL COMMENT '參數值',
    paramDesc varchar(300) NULL COMMENT '參數說明',
    updateUser varchar(10) NULL COMMENT '最後修改人',
    updateTime datetime NULL COMMENT '最後修改時間',
    CONSTRAINT CFG_SYSPARAM_PK PRIMARY KEY (paramId)
);
