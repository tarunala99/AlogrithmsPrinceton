import pandas as pd
import urllib.request, json
import time 
import requests

df = pd.read_excel (r'Splunk_Data Sources and Use Cases (3).xlsx', sheet_name='petr')
headers = {'Authorization' : 'Splunk 7fb85ef8-dc6a-40c0-b829-17c73493d957'}
from urllib import request, parse
for index, row in df.iterrows():
	url = "http://localhost:8088/services/collector/raw?sourcetype="+row['Splunk source type (index)']+"&host=10.0.0.17&source=syslog"
	print(row['Splunk source type (index)'])
	req = urllib.request.Request(url, headers=headers,data=str.encode(row['Sample']))
	url = urllib.request.urlopen(req)
	result = json.loads(url.read().decode())
	print(result)

startindex = 0
endindex = len(nums)-1
while(startindex!=endindex):
	midindex = (endindex-startindex)/2
	searchList(nums,target,startindex,midindex)
	searchList(nums,target,midindex,endindex)