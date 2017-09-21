import csv

sqlFile = open('population.sql', 'wb')
years = [i for i in range(1960, 2016)]

def getCountryIdSelectSql (country_code):
  return '(select id from countries where code = "' + country_code + '")'

def getPopulationValue(population):
  return 'null' if population == '..' else str(population)

with open('population1960_2015.csv', 'rb') as csvfile:
  reader = csv.reader(csvfile)
  for row in reader:
    for year in years:

      sqlFile.write('insert into population (country_id, year, population) values (' +  getCountryIdSelectSql(row[0]) + ', "' + str(year) + '", ' + getPopulationValue(row[year - 1959]) + ');\n')

sqlFile.close()