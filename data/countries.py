import csv

sqlFile = open('countries.sql', 'wb')
with open('countries.csv', 'rb') as csvfile:
  reader = csv.reader(csvfile)
  for row in reader:
    sqlFile.write('insert into countries (name, code) values ("' +  row[0] + '", "' + row[1] + '");\n')

sqlFile.close()