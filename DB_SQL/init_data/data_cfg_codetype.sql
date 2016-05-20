-- 代碼種類
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('CaseCat', 'CodeCat', '案件種類','system','2016-05-07 16:30:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('CaseStatusCat', 'CodeCat', '案件狀態','system','2016-05-07 16:30:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('DeptCat', 'CodeCat', '部門代碼','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('UnitCat', 'CodeCat', '單位代碼','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('HrmRoleCat', 'CodeCat', '人力角色代碼','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('HrmTypeCat', 'CodeCat', '人員類別代碼','system','2016-05-14 21:00:00.000');

-- 案件種類
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('ApplyCase', 'CaseCat', '員額申請','system','2016-05-07 16:30:00.000');

-- 案件狀態
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('01', 'CaseStatusCat', '已收件','system','2016-05-07 16:30:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('02', 'CaseStatusCat', '已申請','system','2016-05-07 16:30:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('03', 'CaseStatusCat', '未通過','system','2016-05-07 16:30:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('04', 'CaseStatusCat', '已回覆','system','2016-05-07 16:30:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('05', 'CaseStatusCat', '處理中','system','2016-05-07 16:30:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('10', 'CaseStatusCat', '案件結案','system','2016-05-07 16:30:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('97', 'CaseStatusCat', '案件異常','system','2016-05-07 16:30:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('98', 'CaseStatusCat', '放棄申請','system','2016-05-07 16:30:00.000');

-- 部門代碼
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('FIN', 'DeptCat', '財務部','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('RDT', 'DeptCat', '研發部','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('BUT', 'DeptCat', '業務部','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('SUP', 'DeptCat', '後勤單位','system','2016-05-13 16:00:00.000');

-- 單位代碼
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('FIN10', 'UnitCat', '財務一處','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('FIN20', 'UnitCat', '財務二處','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('FIN30', 'UnitCat', '財務三處','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('FIN40', 'UnitCat', '財務四處','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('RDT10', 'UnitCat', '手機應用處','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('RDT20', 'UnitCat', 'JAVA技術處','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('RDT30', 'UnitCat', '.NET技術處','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('RDT40', 'UnitCat', '前端應用處','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('BUT10', 'UnitCat', '醫療事業處','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('BUT20', 'UnitCat', '金融事業處','system','2016-05-13 16:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('BUT30', 'UnitCat', '物聯事業處','system','2016-05-13 16:00:00.000');

-- 人力角色代碼
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('PG', 'HrmRoleCat', 'PG(程式設計師)','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('SA', 'HrmRoleCat', 'SA(系統分析師)','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('SD', 'HrmRoleCat', 'SD(系統設計師)','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('ITA', 'HrmRoleCat', 'ITA(軟體架構師)','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('Tester', 'HrmRoleCat', 'Tester(測試工程師)','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('DBA ', 'HrmRoleCat', 'DBA(資料庫管理師)','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('PM', 'HrmRoleCat', 'PM(專案經理)','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('Sales', 'HrmRoleCat', 'Sales(業務人員)','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('VP', 'HrmRoleCat', 'VP(副總)','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('AVP', 'HrmRoleCat', 'AVP(協理)','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('Mgr', 'HrmRoleCat', 'Mgr(經理)','system','2016-05-14 21:00:00.000');

-- 人員類別代碼
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('1', 'HrmTypeCat', '不定期人員','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('2', 'HrmTypeCat', '定期人員(駐點)','system','2016-05-14 21:00:00.000');
INSERT INTO CFG_CODETYPE (codeId, codeCat, codeValue, updateUser, updateTime) VALUES ('3', 'HrmTypeCat', '派遣人員','system','2016-05-14 21:00:00.000');