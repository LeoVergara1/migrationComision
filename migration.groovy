// https://mvnrepository.com/artifact/com.oracle/ojdbc14
// https://mvnrepository.com/artifact/oracle/ojdbc14_g
// https://mvnrepository.com/artifact/org.apache.poi/poi
// https://mvnrepository.com/artifact/org.codehaus.groovy.modules.scriptom/scriptom
@Grapes([
    //@Grab(group='org.codehaus.groovy.modules.scriptom', module='scriptom', version='1.6.0')
])
//import com.zaxxer.hikari.HikariDataSource
import groovy.sql.Sql
import java.sql.Driver

//def sql = Sql.newInstance("jdbc:oracle:thin:@10.31.135.26:1521:DEVL8", "COMISIONLI", "COMISIONLI")
def sql = Sql.newInstance("jdbc:oracle:thin:@10.31.135.26:1521:DEVL8", "baninst1", "u_pick_it")
def results = sql.rows("select * from COMISIONLI.ADMIN_DE_COMISIONES ")
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
        pidm = row.SPRIDEN_PIDM
    }
    pidm
}
File fileNew = new File("out_inserts.txt")

def checkNullOrText(value) {
    if(!value && value != ""){
        value
    }
    else if(value == ""){
        println "Entro"
        null
    }
    else{
        "'${value}'"
    }
}
comisionesMap.each{ row ->
    fileNew.append """INSERT INTO COMISIONLI.AUTORIZACION_COMISIONES
    (ID, CAMPUS, FECHA_INICIAL, FECHA_FINAL, ID_PROMOTOR, NOMBRE_PROMOTOR, PUESTO, ID_ALUMNO, NOMBRE_ALUMNO, PAGO_INICIAL, TOTAL_DESCUENTOS, COMISION, PERIODO, FECHA_DE_PAGO, AUTORIZADO_DIRECTOR, DATE_CREATED, LAST_UPDATED, TIPO_PAGO, VALOR_CONTRATO_REAL, PIDM, COMMENTS, AD_PROMOTOR, AD_COORDINADOR, DISCOUNT_PERCENT, ID_COORDINADOR, USUARIO, NOMBRE_COORDINADOR, FECHA_AUTORIZADO, COMISION_COORDINADOR, USERNAME_MARKETING, STATUS_MARKETING, USERNAME_RECTOR, STATUS_RECTOR) 
    VALUES 
    (${checkNullOrText(row.ID)}, ${checkNullOrText(row.CAMPUS)}, ${checkNullOrText(row.FECHA_INICIAL)}, ${checkNullOrText(row.FECHA_FINAL)}, ${checkNullOrText(row.ID_PROMOTOR)}, ${checkNullOrText(row.NOMBRE_PROMOTOR)}, ${checkNullOrText(row.PUESTO)}, ${checkNullOrText(row.ID_ALUMNO)}, ${checkNullOrText(row.NOMBRE_ALUMNO)}, ${checkNullOrText(row.PAGO_INICIAL)}, ${checkNullOrText(row.TOTAL_DESCUENTOS)}, ${checkNullOrText(row.COMISION)}, ${checkNullOrText(row.PERIODO)}, TO_DATE(${checkNullOrText(row.FECHA_DE_PAGO)}, 'DD/MM/RR  hh:mi a.m.'), ${checkNullOrText(row.AUTORIZADO_DIRECTOR)}, TO_DATE(${checkNullOrText(row.DATE_CREATED)}, 'DD/MM/RR  hh:mi a.m.'), TO_DATE(${checkNullOrText(row.LAST_UPDATED)}, 'DD/MM/RR  hh:mi a.m.'), ${checkNullOrText(row.TIPO_PAGO)}, ${checkNullOrText(row.VALOR_CONTRATO_REAL)}, ${checkNullOrText(row.PIDM)}, ${checkNullOrText(row.COMMENTS)}, ${checkNullOrText(row.AD_PROMOTOR)}, ${checkNullOrText(row.AD_COORDINADOR)}, ${checkNullOrText(row.DISCOUNT_PERCENT)}, ${checkNullOrText(row.ID_COORDINADOR)}, ${checkNullOrText(row.USUARIO)}, ${checkNullOrText(row.NOMBRE_COORDINADOR)}, ${checkNullOrText(row.FECHA_AUTORIZADO)}, ${checkNullOrText(row.COMISION_COORDINADOR)}, ${checkNullOrText(row.USERNAME_MARKETING)}, 0, ${checkNullOrText(row.USERNAME_RECTOR)}, 0); \n"""
}
//def excelObj = new ActiveXObject('Excel.Application')

//def workBook = excelObj.Workbooks.Open("autorizacion_comisiones.xlsx")
