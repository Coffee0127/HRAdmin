DROP TABLE IF EXISTS APP_ROLE_FUNCTION;

CREATE TABLE APP_ROLE_FUNCTION (
    OID       VARCHAR(32) NOT NULL,
    ROLE_ID   BIGINT NOT NULL COMMENT '角色編號',
    FUNC_ID   BIGINT NOT NULL COMMENT '功能編號',
    CONSTRAINT APP_ROLE_FUNCTION_PK PRIMARY KEY (OID),
    CONSTRAINT APP_ROLE_FUNC_ROLE_FK FOREIGN KEY (ROLE_ID) REFERENCES APP_ROLE (ID),
    CONSTRAINT APP_ROLE_FUNC_FUNC_FK FOREIGN KEY (FUNC_ID) REFERENCES APP_FUNCTION (ID)
);