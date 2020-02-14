import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.postgresql.ds.PGSimpleDataSource;

public class PostgrresqlDataSource {
    public DataSource getDataSource() {
        Properties props = new Properties();
        PGSimpleDataSource ds = new PGSimpleDataSource();
        try(FileInputStream fis = new FileInputStream("src/postgresql.properties")) {
            props.load(fis);
            ds.setUrl(props.getProperty("url"));
            ds.setUser(props.getProperty("user"));
            ds.setPassword(props.getProperty("password"));
        } catch (IOException e) {
            System.out.println(e.getMessage() + " " + e.getCause());
        }
        return ds;
    }
}
