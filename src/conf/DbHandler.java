package conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * To use JDBC you need to install driver into local maven repository - https://wiki.jasig.org/pages/viewpage.action?pageId=57578731
 */

public class DbHandler {

    private static String dbName = "db.name";
    private static String connectionString =
            "jdbc:sqlserver://ip;"
                    + "database="+dbName+";"
                    + "user=;"
                    + "password=;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "hostNameInCertificate=*.database.windows.net;"
                    + "loginTimeout=30;";

    public static String getData(String string) {
        Connection connection;
        long number = 0;
        try {
            connection = DriverManager.getConnection(connectionString);
            ResultSet result = connection.createStatement().executeQuery("select distinct ne2.NMLSID as IndividualNMLSID\n" +
                    "from NMLS.NMLSEntity ne1\n" +
                    "  inner join NMLS.IndividualSponsorship s on ne1.NMLSEntityID = s.NMLSEntityID and s.IsRowActive = 1\n" +
                    "  inner join NMLS.Individual i on i.IndividualID = s.IndividualID\n" +
                    "  inner join NMLS.NMLSEntity ne2 on ne2.NMLSEntityID = i.NMLSEntityID\n" +
                    "where ne1.NMLSID = '"+string+"'");
            if(result.next()) {
                number = result.getLong("number");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("DB error", e);
        }
        return ""+number;
    }
}
