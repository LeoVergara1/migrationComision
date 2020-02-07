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
def file = new File("Registros-Eliminar-Campus-ComisionesLI.csv")
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
File fileNew = new File("out_deletes.txt")

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
    println row.dump()
    fileNew.append """DELETE FROM COMISIONLI.AUTORIZACION_COMISIONES WHERE id=${row.ID};\n"""
}
//def excelObj = new ActiveXObject('Excel.Application')

//def workBook = excelObj.Workbooks.Open("autorizacion_comisiones.xlsx")
