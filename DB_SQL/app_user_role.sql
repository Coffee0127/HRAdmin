DROP TABLE IF EXISTS APP_USER_ROLE;

CREATE TABLE APP_USER_ROLE (
    OID            VARCHAR(32) NOT NULL,
    USER_ACCOUNT   VARCHAR(50) NOT NULL COMMENT '使用者帳號',
    ROLE_ID        BIGINT NOT NULL COMMENT '角色編號',
    CONSTRAINT APP_USER_ROLE_PK PRIMARY KEY (OID),
    CONSTRAINT APP_USER_ROLE_USER_FK FOREIGN KEY (USER_ACCOUNT) REFERENCES APP_USER (ACCOUNT),
    CONSTRAINT APP_USER_ROLE_ROLE_FK FOREIGN KEY (ROLE_ID) REFERENCES APP_ROLE (ID)
);