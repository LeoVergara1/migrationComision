require 'dbi'
require 'oci8'

dbh = DBI.connect('DBI:OCI8:jdbc:oracle:thin:@10.31.135.26:1521:DEVL8', 'baninst1', 'u_pick_it')
rs = dbh.prepare('select * from COMISIONLI.ADMIN_DE_COMISIONES')
rs.execute
while rsRow = rs.fetch do
   p rsRow
   #Alternative output: puts rsRow
   #Alternative output: pp rsRow
end
rs.finish
dbh.disconnect