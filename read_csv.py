import pandas as pd 
import configparser
data = pd.read_csv(filepath_or_buffer = "prod_scheduled_searches_itsi.csv")
from collections import namedtuple
data = data.rename(columns={"dispatch.earliest_time": "earliest_time"})
# creating a tuple from pandas
Search = namedtuple('Search', data.dtypes.index.tolist())
# gettting each of the values

def processtitle(temp):
	list = temp.split("*ACTION REQUIRED*")
	if(len(list)>1):
		temp = list[1]
	temp = temp.replace("[","")
	temp = temp.replace("]","")
	temp = temp.replace("(","_b")
	temp = temp.replace(")","")
	temp = temp.replace("%","")
	temp = temp.replace(":","")
	temp = temp.replace("|","_p_")
	temp = temp.replace("/","_s_")
	temp = temp.replace("<","_lt_")
	temp = temp.replace(">","_gt_")
	temp = temp.strip()
	return temp

for a in range(0,len(data)):
	search = Search(*data.iloc[a,:])
	temp = search.title
	temp = processtitle(temp)
	print(temp)
	os.mkdir(temp)
	os.chdir(temp)
	f = open("SPL Query.txt", "a")
	f.write(search.search)
	f.close()
	config = configparser.ConfigParser()
	temp1= search.description
	temp1 = str(temp1)
	temp1 = temp1.replace("%","percent")
	config['Splunk Parameters'] = {'author': search.author,
	'cron_schedule': search.cron_schedule,
	'dispatch.earliest_time': search.earliest_time,
	'description': temp1}
	with open('Additional Splunk Parameters.ini', 'w') as configfile:
		config.write(configfile)
	os.chdir("..")	