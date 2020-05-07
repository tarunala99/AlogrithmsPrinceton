f = open("TrendMicro_PCAP_Data.txt", encoding="utf8")
linesdev = f.readlines()
f.close()
b="TrendMicroDsTenantId="
c=len(b)
list=[]
for a in linesdev:
	start = a.find("<134>")
	if start==-1:
		continue
	end = a.find("TrendMicroDsTenantId=")
	temp = a[start:end+c+2]
	list.append(temp)
with open('output.txt', 'w') as filehandle:
    for listitem in list:
        filehandle.write(listitem+"\n")