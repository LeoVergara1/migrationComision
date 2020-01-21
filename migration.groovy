// https://mvnrepository.com/artifact/com.oracle/ojdbc14
// https://mvnrepository.com/artifact/oracle/ojdbc14_g
// https://mvnrepository.com/artifact/org.apache.poi/poi
// https://mvnrepository.com/artifact/org.codehaus.groovy.modules.scriptom/scriptom
@Grapes([
    @Grab(group='org.codehaus.groovy.modules.scriptom', module='scriptom', version='1.6.0')
])
//import com.zaxxer.hikari.HikariDataSource
import groovy.sql.Sql
import java.sql.Driver

//def sql = Sql.newInstance("jdbc:oracle:thin:@10.31.135.26:1521:DEVL8", "COMISIONLI", "COMISIONLI")
def sql = Sql.newInstance("jdbc:oracle:thin:@10.31.135.26:1521:DEVL8", "baninst1", "u_pick_it")
def results = sql.rows("select * from COMISIONLI.ADMIN_DE_COMISIONES ")
sql.eachRow("select * from spriden where SPRIDEN_ID = ?",["M00222384"]) { row ->
     println "${row.SPRIDEN_PIDM}"
 }

def file = new File("comisiones.csv")
def lines = file.readLines()
List<Map> comisionesMap = []
lines.removeAt(0)
lines.each { String line ->
   line = line << "1"
   row = line.split(",")
   comisionesMap << [
       ID: row[0],
       CAMPUS: row[1],
       ID_PROMOTOR: row[2],
       NOMBRE_PROMOTOR: row[3],
       PUESTO: row[4],
       ID_ALUMNO: row[5],
       NOMBRE_ALUMNO: row[6],
       PAGO_INICIAL: row[7],
       TOTAL_DESCUENTOS: row[8],
       COMISION: row[9],
       PERIODO: row[10],
       FECHA_DE_PAGO: row[11],
       AUTORIZADO_DIRECTOR: row[12],
       DATE_CREATED: row[13],
       LAST_UPDATED: row[14],
       ID_COORDINADOR: row[15],
       NOMBRE_COORDINADOR: row[16],
       COMISION_COORDINADOR: row[17],
       FECHA_AUTORIZAD: row[18],
       USUARIO: row[19],
       TIPO_PAGO: row[20],
       VALOR_CONTRATO_REAL: row[21],
       PIDM: getPidm(row[2], sql),
       COMMENTS: row[23],
       AD_PROMOTOR: row[24],
       AD_COORDINADOR: row[25],
       DISCOUNT_PERCENT: row[26]
   ]
}

def getPidm(enrollement, sql){
    def pidm = ""
    sql.eachRow("select * from spriden where SPRIDEN_ID = ?",[enrollement]) { row ->
        println enrollement
    }
    pidm
}
println comisionesMap[0].PIDM
println comisionesMap[1].PIDM
println comisionesMap[2].PIDM
println comisionesMap[3].PIDM
println comisionesMap[4].PIDM

File fileNew = new File("out_inserts.txt")

comisionesMap.each{ comission ->
    fileNew.write "INSERT INTO "COMISIONLI"."AUTORIZACION_COMISIONES" (ID, CAMPUS, FECHA_INICIAL, FECHA_FINAL, ID_PROMOTOR, NOMBRE_PROMOTOR, PUESTO, ID_ALUMNO, NOMBRE_ALUMNO, PAGO_INICIAL, TOTAL_DESCUENTOS, COMISION, PERIODO, FECHA_DE_PAGO, AUTORIZADO_DIRECTOR, DATE_CREATED, LAST_UPDATED, TIPO_PAGO, VALOR_CONTRATO_REAL, PIDM, COMMENTS, AD_PROMOTOR, AD_COORDINADOR, DISCOUNT_PERCENT, ID_COORDINADOR, USUARIO, NOMBRE_COORDINADOR, FECHA_AUTORIZADO, COMISION_COORDINADOR, USERNAME_MARKETING, STATUS_MARKETING, USERNAME_RECTOR, STATUS_RECTOR) VALUES ('298', 'cmx', TO_DATE('2020-01-21 13:55:58', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2020-01-21 13:56:01', 'YYYY-MM-DD HH24:MI:SS'), '33445', 'test', 'puesto', '3287328', 'alimno', '287', '3434', '344', '434', TO_DATE('2020-01-21 13:56:26', 'YYYY-MM-DD HH24:MI:SS'), 'y', TO_DATE('2020-01-21 13:56:34', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2020-01-21 13:56:36', 'YYYY-MM-DD HH24:MI:SS'), '3', '2', '3223', '323', '3232', '3232', '3232', '323', 'dsd', 'nombre', TO_DATE('2020-01-21 13:57:00', 'YYYY-MM-DD HH24:MI:SS'), '1', '1', '1', '1', '1') \n"
}
//def excelObj = new ActiveXObject('Excel.Application')

//def workBook = excelObj.Workbooks.Open("autorizacion_comisiones.xlsx")
