import csv

sqlFile = open('area.sql', 'wb')
years = [i for i in range(1961, 2017)]

def getCountryIdSelectSql (country_code):
  return '(select id from countries where code = "' + country_code + '")'

def getAreaValue(area):
  return 'null' if (area == '..' or area == '') else str(area)

with open('area1961_2016.csv', 'rb') as csvfile:
  reader = csv.reader(csvfile)
  for row in reader:
    for year in years:

      sqlFile.write('insert into area (country_id, year, area) values (' +  getCountryIdSelectSql(row[0]) + ', "' + str(year) + '", ' + getAreaValue(row[year - 1960]) + ');\n')

sqlFile.close()