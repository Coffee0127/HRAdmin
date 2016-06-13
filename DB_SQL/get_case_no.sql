DROP ALIAS IF EXISTS GET_CASE_NO;

CREATE ALIAS GET_CASE_NO AS $$
String getCaseNo(java.sql.Connection connection, String deptCat) throws Exception {
    String querySql = new StringBuilder()
        .append("SELECT CASE_DATE, CASE_SEQUENCE, CASE_SEQ_COUNT, CURRENT_DATE() TODAY_DATE")
        .append("  FROM CASE_ID")
        .append(" WHERE DEPT_CAT = '").append(deptCat).append("'")
        .toString();
    java.sql.ResultSet rs = connection.prepareStatement(querySql).executeQuery();
    if (rs.next()) {
        java.util.Date caseDate = rs.getDate("CASE_DATE");
        long caseSequence = rs.getLong("CASE_SEQUENCE");
        int caseSeqCount = rs.getInt("CASE_SEQ_COUNT");
        java.util.Date today = rs.getDate("TODAY_DATE");

        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE CASE_ID");
        updateSql.append("   SET UPDATE_TIME = NOW(), ");
        if (caseDate.equals(today)) {
            caseSequence++;
        } else {
            caseSequence = 1L;
            updateSql.append("CASE_DATE = ").append(today).append(",");
        }
        updateSql.append("CASE_SEQUENCE = caseSequence");
        updateSql.append(" WHERE DEPT_CAT = '").append(deptCat).append("'");
        connection.prepareStatement(querySql).executeUpdate();

        StringBuilder caseSeq = new StringBuilder();
        caseSeq.append(caseSequence);
        while (caseSeq.length() < caseSeqCount) {
            caseSeq.insert(0, 0);
        }
        StringBuilder caseId = new StringBuilder()
            .append(deptCat).append("-")
            .append(new java.text.SimpleDateFormat("yyyyMMdd").format(caseDate)).append("-")
            .append(caseSeq);
        return caseId.toString();
    }
    return null;
}
$$;