import thread
import time
import uuid
import requests
import sys, traceback
import random 

SERVER="http://localhost:8081/custom-waited-get?waitInSeconds=%s&requestId=%s"
THREAD_COUNT = 400

# Define a function for the thread

def request_server(count,  waitTime, thread_name):
    counter = 0
    print "\n[%s]Starting thread " % (thread_name ,) 
    while counter < count:
        counter = counter + 1
        try:
           rid = str(uuid.uuid1())
           print "Starting thread: " + rid
           r = requests.get(SERVER % ( str(waitTime + random.randint(0, 10)), rid))
           print  "[%s]Completed: Response: %s " % (thread_name, rid)
           counter = counter + 1
        except:
           print "Error: Request execution \n"
           traceback.print_exc(file=sys.stdout)

try:
   for i in range (0, THREAD_COUNT):
       thread.start_new_thread( request_server, (3, 15, "thread-" + str(i) ) )
except:
   print "Error: unable to start thread \n" 
   traceback.print_exc(file=sys.stdout)

while 1:
   print "Waiting for next 5 seconds."
   time.sleep(5)
   pass

