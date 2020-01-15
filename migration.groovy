// https://mvnrepository.com/artifact/com.oracle/ojdbc14
// https://mvnrepository.com/artifact/oracle/ojdbc14_g
//import com.zaxxer.hikari.HikariDataSource
import groovy.sql.Sql
import java.sql.Driver


//def driver = Class.forName('oracle.jdbc.OracleDriver').newInstance() as Driver
//def props = new Properties()
//props.setProperty("user", "COMISIONLI")
//props.setProperty("password", "COMISIONLI")
//def conn = driver.connect("jdbc:oracle:thin:@10.31.135.26:1521:DEVL8", props)
//def sql = new Sql(conn)
def sql = Sql.newInstance("jdbc:oracle:thin:@10.31.135.26:1521:DEVL8", "COMISIONLI", "COMISIONLI")
def results = sql.rows("select * from tableexample.repdata ")